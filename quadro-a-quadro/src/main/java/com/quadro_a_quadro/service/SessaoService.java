package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Sessao;
import com.quadro_a_quadro.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoService 
{
    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private SalaService salaService;

    //cadastrar sessão
    public Sessao cadastrar(Sessao sessao) 
    {
        // verifica se filme existe e está ativo
        filmeService.buscarPorId(sessao.getFilme().getId());

        // verifica se sala existe e está ativa
        salaService.buscarPorId(sessao.getSala().getNumSala());

        // verifica conflito de horário (cenário alternativo)
        boolean conflito = sessaoRepository.existsBySalaNumSalaAndDataAndHorario(
            sessao.getSala().getNumSala(),
            sessao.getData(),
            sessao.getHorario()
        );

        if (conflito) 
        {
            throw new RuntimeException(
                "Já existe uma sessão nessa sala para essa data e horário"
            );
        }

        return sessaoRepository.save(sessao);
    }

    //listar todas as sessões
    public List<Sessao> listarTodas() 
    {
        return sessaoRepository.findAll();
    }

    //buscar por ID
    public Sessao buscarPorId(Long id) 
    {
        return sessaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada com id: " + id));
    }

    //excluir sessão
    public void excluir(Long id) 
    {
        buscarPorId(id);
        sessaoRepository.deleteById(id);
    }

    // buscar por data

// buscar por nome da sala
    public List<Sessao> buscarPorSala(String busca) {
        return sessaoRepository.findBySalaNomeContainingIgnoreCase(busca);
    }

    public List<Sessao> listarPorData(LocalDate data) 
    {
    return sessaoRepository.findByData(data);
    }

    //listar sessões de um filme
    public List<Sessao> listarPorFilme(Long filmeId) 
    {
        return sessaoRepository.findByFilmeId(filmeId);
    }

    // Listar sessões de uma sala
    public List<Sessao> listarPorSala(Long numSala) 
    {
        return sessaoRepository.findBySalaNumSala(numSala);
    }
}