package com.quadro_a_quadro.repository;

import com.quadro_a_quadro.model.Sala;
import com.quadro_a_quadro.model.enums.StatusSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findByStatus(StatusSala status);
    Optional<Sala> findByNumSala(String numSala);
    List<Sala> findByNumSalaContainingIgnoreCase(String numSala);
}