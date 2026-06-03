package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Sala;
import com.quadro_a_quadro.model.enums.StatusSala;
import com.quadro_a_quadro.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public Sala cadastrar(Sala sala) {
        if (salaRepository.findByNumSala(sala.getNumSala()).isPresent())
            throw new RuntimeException("Já existe uma sala com esse nome");
        return salaRepository.save(sala);
    }

    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    public Sala editar(Long id, Sala salaAtualizada) {
        Sala sala = buscarPorId(id);
        sala.setNumSala(salaAtualizada.getNumSala());
        sala.setCapacidade(salaAtualizada.getCapacidade());
        sala.setStatus(salaAtualizada.getStatus());
        return salaRepository.save(sala);
    }

    public void excluir(Long id) {
        buscarPorId(id);
        salaRepository.deleteById(id);
    }

    public Sala alterarStatus(Long id, StatusSala novoStatus) {
        Sala sala = buscarPorId(id);
        sala.setStatus(novoStatus);
        return salaRepository.save(sala);
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sala não encontrada com id: " + id));
    }

    public List<Sala> listarAtivas() {
        return salaRepository.findByStatus(StatusSala.ATIVO);
    }

    public List<Sala> buscarPorNome(String busca) {
        return salaRepository.findByNumSalaContainingIgnoreCase(busca);
    }
}