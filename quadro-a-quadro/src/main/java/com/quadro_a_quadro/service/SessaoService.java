package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Sessao;
import com.quadro_a_quadro.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private SalaService salaService;

    public Sessao cadastrar(Sessao sessao) {
        filmeService.buscarPorId(sessao.getFilme().getId());
        salaService.buscarPorId(sessao.getSala().getId());

        boolean conflito = sessaoRepository.existsBySalaNumSalaAndDataAndHorario(
            sessao.getSala().getNumSala(),
            sessao.getData(),
            sessao.getHorario()
        );

        if (conflito)
            throw new RuntimeException("Já existe uma sessão nessa sala para essa data e horário");

        return sessaoRepository.save(sessao);
    }

    public Sessao editar(Long id, Sessao sessaoAtualizada) {
        Sessao sessao = buscarPorId(id);

        boolean conflito = sessaoRepository.existsBySalaNumSalaAndDataAndHorario(
            sessaoAtualizada.getSala().getNumSala(),
            sessaoAtualizada.getData(),
            sessaoAtualizada.getHorario()
        );

        if (conflito && !sessao.getId().equals(id))
            throw new RuntimeException("Já existe uma sessão nessa sala para essa data e horário");

        sessao.setFilme(sessaoAtualizada.getFilme());
        sessao.setSala(sessaoAtualizada.getSala());
        sessao.setData(sessaoAtualizada.getData());
        sessao.setHorario(sessaoAtualizada.getHorario());

        return sessaoRepository.save(sessao);
    }

    public List<Sessao> listarTodas() {
        return sessaoRepository.findAll();
    }

    public Sessao buscarPorId(Long id) {
        return sessaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada com id: " + id));
    }

    public void excluir(Long id) {
        buscarPorId(id);
        sessaoRepository.deleteById(id);
    }

    public List<Sessao> listarPorFilme(Long filmeId) {
        return sessaoRepository.findByFilmeId(filmeId);
    }

    public List<Sessao> buscarPorSala(String busca) {
        return sessaoRepository.findBySalaNumSalaContainingIgnoreCase(busca);
    }

    public List<Sessao> listarPorData(LocalDate data) {
        return sessaoRepository.findByData(data);
    }
}