package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Genero;
import com.quadro_a_quadro.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GeneroService 
{

    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> listarTodos() 
    {
        return generoRepository.findAll();
    }

    public Genero buscarPorId(Long id) 
    {
        return generoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Gênero não encontrado com id: " + id));
    }

    public Genero cadastrar(Genero genero) 
    {
        generoRepository.findByNomeIgnoreCase(genero.getNome())
            .ifPresent(g -> { throw new RuntimeException("Gênero já cadastrado: " + genero.getNome()); });
        return generoRepository.save(genero);
    }

    public void excluir(Long id) 
    {
        buscarPorId(id);
        generoRepository.deleteById(id);
    }
}