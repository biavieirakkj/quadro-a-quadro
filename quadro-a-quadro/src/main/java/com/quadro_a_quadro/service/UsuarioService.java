package com.quadro_a_quadro.service;

import com.quadro_a_quadro.model.Usuario;
import com.quadro_a_quadro.model.enums.PerfilUsuario;
import com.quadro_a_quadro.model.enums.StatusUsuario;
import com.quadro_a_quadro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registro — sempre vira GERENTE e fica PENDENTE
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new RuntimeException("Email já cadastrado");

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setPerfil(PerfilUsuario.GERENTE);
        usuario.setStatus(StatusUsuario.PENDENTE);

        return usuarioRepository.save(usuario);
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Listar pendentes
    public List<Usuario> listarPendentes() {
        return usuarioRepository.findByStatus(StatusUsuario.PENDENTE);
    }

    // Buscar por nome ou email
    public List<Usuario> buscarPorNomeOuEmail(String termo) {
        return usuarioRepository
            .findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(termo, termo);
    }

    // Buscar pendentes por nome ou email
    public List<Usuario> buscarPendentesPorNomeOuEmail(String termo) {
        return usuarioRepository
            .findByStatusAndNomeContainingIgnoreCaseOrStatusAndEmailContainingIgnoreCase(
                StatusUsuario.PENDENTE, termo,
                StatusUsuario.PENDENTE, termo);
    }

    // Admin aprova usuário
    public void aprovar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setStatus(StatusUsuario.APROVADO);
        usuarioRepository.save(usuario);
    }

    // Admin rejeita usuário
    public void rejeitar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setStatus(StatusUsuario.REJEITADO);
        usuarioRepository.save(usuario);
    }

    // Excluir usuário
    public void excluir(Long id) {
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}