package com.quadro_a_quadro.repository;

import com.quadro_a_quadro.model.Usuario;
import com.quadro_a_quadro.model.enums.StatusUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByStatus(StatusUsuario status);
    boolean existsByEmail(String email);
    List<Usuario> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String nome, String email);
    List<Usuario> findByStatusAndNomeContainingIgnoreCaseOrStatusAndEmailContainingIgnoreCase(
        StatusUsuario status1, String nome,
        StatusUsuario status2, String email);
}