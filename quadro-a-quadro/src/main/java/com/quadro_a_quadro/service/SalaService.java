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

    //cadastrar sala
    public Sala cadastrar(Sala sala) 
    {
        return salaRepository.save(sala);
    }

    //listar salas
    public List<Sala> listarTodas() 
    {
        return salaRepository.findAll();
    }

    //editar sala
    public Sala editar(Long id, Sala salaAtualizada) 
    {
        Sala sala = buscarPorId(id);
        sala.setCapacidade(salaAtualizada.getCapacidade());
        sala.setStatus(salaAtualizada.getStatus());
        return salaRepository.save(sala);
    }

    //excluir sala
    public void excluir(Long id) 
    {
        buscarPorId(id);
        salaRepository.deleteById(id);
    }

    //ativar/desativar sala
    public Sala alterarStatus(Long id, StatusSala novoStatus) 
    {
        Sala sala = buscarPorId(id);
        sala.setStatus(novoStatus);
        return salaRepository.save(sala);
    }

    //buscar por ID (uso interno)
    public Sala buscarPorId(Long id) 
    {
        return salaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sala não encontrada de id: " + id));
    }

    //listar salas ativas
    public List<Sala> listarAtivas() 
    {
        return salaRepository.findByStatus(StatusSala.ATIVO);
    }
}