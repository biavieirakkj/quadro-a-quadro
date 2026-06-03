package com.quadro_a_quadro.repository;

import com.quadro_a_quadro.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByData(LocalDate data);

    boolean existsBySalaNumSalaAndDataAndHorario(
        String numSala,
        LocalDate data,
        LocalTime horario
    );

    List<Sessao> findByFilmeId(Long filmeId);
    List<Sessao> findBySalaNumSalaContainingIgnoreCase(String numSala);
}