package com.quadro_a_quadro;

import com.quadro_a_quadro.model.Usuario;
import com.quadro_a_quadro.model.enums.PerfilUsuario;
import com.quadro_a_quadro.model.enums.StatusUsuario;
import com.quadro_a_quadro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail("admin@cinema.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@cinema.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setPerfil(PerfilUsuario.ADMINISTRADOR);
            admin.setStatus(StatusUsuario.APROVADO);
            usuarioRepository.save(admin);
            System.out.println("=== Admin criado: admin@cinema.com / admin123 ===");
        }
    }
}