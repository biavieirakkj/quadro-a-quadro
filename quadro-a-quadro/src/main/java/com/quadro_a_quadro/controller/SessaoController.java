package com.quadro_a_quadro.controller;

import com.quadro_a_quadro.model.Sessao;
import com.quadro_a_quadro.model.enums.StatusFilme;
import com.quadro_a_quadro.service.FilmeService;
import com.quadro_a_quadro.service.SalaService;
import com.quadro_a_quadro.service.SessaoService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private SalaService salaService;

    private void popularModel(Model model) {
        model.addAttribute("sessoes", sessaoService.listarTodas());
        model.addAttribute("filmes", filmeService.listarPorStatus(StatusFilme.EM_CARTAZ));
        model.addAttribute("salas", salaService.listarAtivas());
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) Boolean hoje,
            Model model) {

        if (hoje != null && hoje) {
            model.addAttribute("sessoes",
                sessaoService.listarPorData(LocalDate.now()));
        } else if (busca != null && !busca.isBlank()) {
            model.addAttribute("sessoes",
                sessaoService.buscarPorSala(busca));
        } else {
            model.addAttribute("sessoes", sessaoService.listarTodas());
        }

        model.addAttribute("filmes", filmeService.listarPorStatus(StatusFilme.EM_CARTAZ));
        model.addAttribute("salas", salaService.listarAtivas());
        return "sessoes/listar";
    }

    @PostMapping("/nova")
    public String cadastrar(
        @Valid @ModelAttribute Sessao sessao,
        BindingResult resultado,
        RedirectAttributes redirectAttributes,
        Model model) {

    if (resultado.hasErrors()) {
        redirectAttributes.addFlashAttribute("erro", "Preencha todos os campos corretamente.");
        return "redirect:/sessoes";
    }

    try {
        sessaoService.cadastrar(sessao);
        redirectAttributes.addFlashAttribute("sucesso", "Sessão cadastrada com sucesso!");
    } catch (RuntimeException e) {
        redirectAttributes.addFlashAttribute("erro", e.getMessage());
    }

    return "redirect:/sessoes";
    }

    @PostMapping("/editar/{id}")
    public String editar(
        @PathVariable Long id,
        @Valid @ModelAttribute Sessao sessao,
        BindingResult resultado,
        RedirectAttributes redirectAttributes,
        Model model) {

    if (resultado.hasErrors()) {
        redirectAttributes.addFlashAttribute("erro", "Preencha todos os campos corretamente.");
        return "redirect:/sessoes";
    }

    try {
        sessaoService.editar(id, sessao);
        redirectAttributes.addFlashAttribute("sucesso", "Sessão editada com sucesso!");
    } catch (RuntimeException e) {
        redirectAttributes.addFlashAttribute("erro", e.getMessage());
    }

    return "redirect:/sessoes";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sessaoService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Sessão excluída com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/sessoes";
    }
}