package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Classificacao;
import com.quadro_a_quadro.repository.ClassificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    public List<Classificacao> listarTodas() {
        return classificacaoRepository.findAll();
    }

    public Classificacao buscarPorId(Long id) {
        return classificacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Classificação não encontrada com id: " + id));
    }

    public Classificacao cadastrar(Classificacao classificacao) {
        return classificacaoRepository.save(classificacao);
    }

    public void excluir(Long id) {
        buscarPorId(id);
        classificacaoRepository.deleteById(id);
    }
}