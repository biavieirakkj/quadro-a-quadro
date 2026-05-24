package com.quadro_a_quadro.controller;

import com.quadro_a_quadro.model.Usuario;
import com.quadro_a_quadro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // página de login
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // página de registro
    @GetMapping("/registro")
    public String exibirRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    // salvar registro
    @PostMapping("/registro")
    public String registrar(
            @Valid @ModelAttribute Usuario usuario,
            BindingResult resultado,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (resultado.hasErrors()) return "auth/registro";

        try {
            usuarioService.registrar(usuario);
            redirectAttributes.addFlashAttribute("sucesso",
                "Cadastro realizado! Aguarde a aprovação do administrador.");
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "auth/registro";
        }

        return "redirect:/login";
    }

    // admin lista todos os usuários
    @GetMapping("/usuarios")
    public String listarUsuarios(
            @RequestParam(required = false) String busca,
            Model model) {

        if (busca != null && !busca.isBlank()) {
            model.addAttribute("usuarios", usuarioService.buscarPorNomeOuEmail(busca));
        } else {
            model.addAttribute("usuarios", usuarioService.listarTodos());
        }
        return "auth/usuarios";
    }

    // admin lista solicitações pendentes
    @GetMapping("/usuarios/solicitacoes")
    public String listarSolicitacoes(
            @RequestParam(required = false) String busca,
            Model model) {

        if (busca != null && !busca.isBlank()) {
            model.addAttribute("usuarios",
                usuarioService.buscarPendentesPorNomeOuEmail(busca));
        } else {
            model.addAttribute("usuarios", usuarioService.listarPendentes());
        }
        return "auth/solicitacoes";
    }

    // admin aprova usuário
    @PostMapping("/usuarios/aprovar/{id}")
    public String aprovar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.aprovar(id);
        redirectAttributes.addFlashAttribute("sucesso", "Usuário aprovado!");
        return "redirect:/usuarios/solicitacoes";
    }

    // admin rejeita usuário
    @PostMapping("/usuarios/rejeitar/{id}")
    public String rejeitar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.rejeitar(id);
        redirectAttributes.addFlashAttribute("sucesso", "Usuário rejeitado!");
        return "redirect:/usuarios/solicitacoes";
    }

    // admin exclui usuário
    @PostMapping("/usuarios/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Usuário excluído!");
        return "redirect:/usuarios";
    }
}