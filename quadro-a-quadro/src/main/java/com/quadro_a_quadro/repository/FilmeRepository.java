package com.quadro_a_quadro.repository;

import com.quadro_a_quadro.model.Filme;
import com.quadro_a_quadro.model.enums.StatusFilme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> 
{

    // busca por título parcial, não case-sensitive 
    List<Filme> findByTituloContainingIgnoreCase(String titulo);

    // verifica título duplicado 
    Optional<Filme> findByTituloIgnoreCase(String titulo);

    // lista filmes por status
    List<Filme> findByStatus(StatusFilme status);
}