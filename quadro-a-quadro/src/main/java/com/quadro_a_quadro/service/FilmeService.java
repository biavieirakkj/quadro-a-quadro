package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Filme;
import com.quadro_a_quadro.model.enums.StatusFilme;
import com.quadro_a_quadro.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService 
{
    @Autowired
    private FilmeRepository filmeRepository;

    //cadastrar filme
    public Filme cadastrar(Filme filme, MultipartFile capa) throws IOException 
    {
        validarTituloDuplicado(filme.getTitulo(), null);

        System.out.println("=== SALVANDO FILME ===");
        System.out.println("capa é null: " + (capa == null));
        System.out.println("capa isEmpty: " + (capa != null && capa.isEmpty()));
        System.out.println("capa nome: " + (capa != null ? capa.getOriginalFilename() : "null"));

        if (capa != null && !capa.isEmpty()) {
            String nomeArquivo = salvarImagem(capa);
            filme.setCapa(nomeArquivo);
        }

        return filmeRepository.save(filme);
    }

    private String salvarImagem(MultipartFile arquivo) throws IOException 
    {
        Path pastaUpload = Paths.get("uploads/capas");
        System.out.println("pasta absoluta: " + pastaUpload.toAbsolutePath());
        
        Files.createDirectories(pastaUpload);
        
        String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path destino = pastaUpload.resolve(nomeArquivo);
        
        System.out.println("salvando em: " + destino.toAbsolutePath());
        Files.copy(arquivo.getInputStream(), destino);
        System.out.println("arquivo salvo com sucesso!");
        
        return nomeArquivo;
    }   

    //listar filmes
    public List<Filme> listarTodos() 
    {
        return filmeRepository.findAll();
    }

    //editar filme
    public Filme editar(Long id, Filme filmeAtualizado, MultipartFile capa) throws IOException 
    {
        Filme filme = buscarPorId(id);
        validarTituloDuplicado(filmeAtualizado.getTitulo(), id);

        filme.setTitulo(filmeAtualizado.getTitulo());
        filme.setSinopse(filmeAtualizado.getSinopse());
        filme.setDuracao(filmeAtualizado.getDuracao());
        filme.setClassificacao(filmeAtualizado.getClassificacao());
        filme.setGeneros(filmeAtualizado.getGeneros());
        filme.setStatus(filmeAtualizado.getStatus());

        if (capa != null && !capa.isEmpty()) {
            String nomeArquivo = salvarImagem(capa);
            filme.setCapa(nomeArquivo);
        }

    return filmeRepository.save(filme);
}


    // excluir filme
    public void excluir(Long id) 
    {
        buscarPorId(id); // garante que existe antes de excluir
        filmeRepository.deleteById(id);
    }

    //ativar/desativar filme
    public Filme alterarStatus(Long id, StatusFilme novoStatus) 
    {
        Filme filme = buscarPorId(id);
        filme.setStatus(novoStatus);
        return filmeRepository.save(filme);
    }

    //buscar filme por título
    public List<Filme> buscarPorTitulo(String titulo) 
    {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo);
    }

    //validar título duplicado
    private void validarTituloDuplicado(String titulo, Long idAtual) 
    {
        Optional<Filme> existente = filmeRepository.findByTituloIgnoreCase(titulo);
        if (existente.isPresent() && !existente.get().getId().equals(idAtual)) {
            throw new RuntimeException("Já existe um filme com o título: " + titulo);
        }
    }

    //buscar por ID (uso interno)
    public Filme buscarPorId(Long id) 
    {
        return filmeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filme não encontrado com id: " + id));
    }

    // listar filmes por status
    public List<Filme> listarPorStatus(StatusFilme status) 
    {
        return filmeRepository.findByStatus(status);
    }
}