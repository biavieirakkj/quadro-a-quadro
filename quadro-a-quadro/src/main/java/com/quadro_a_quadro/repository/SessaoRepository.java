package com.quadro_a_quadro.repository;

import com.quadro_a_quadro.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> 
{

    List<Sessao> findByData(LocalDate data);

    // verifica conflito de horário na sala 
    boolean existsBySalaNumSalaAndDataAndHorario
    (
        Long numSala,
        LocalDate data,
        LocalTime horario
    );

    // coloca isso — busca pelo numSala da sala
    List<Sessao> findBySalaNumSalaContaining(Long numSala);

    List<Sessao> findByFilmeId(Long filmeId);

    List<Sessao> findBySalaNumSala(Long numSala);
}