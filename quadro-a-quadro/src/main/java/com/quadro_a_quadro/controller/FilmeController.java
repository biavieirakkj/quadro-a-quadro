package com.quadro_a_quadro.controller;

import com.quadro_a_quadro.model.Filme;
import com.quadro_a_quadro.model.enums.StatusFilme;
import com.quadro_a_quadro.model.enums.GeneroFilme;
import com.quadro_a_quadro.model.enums.ClassificacaoFilme;
import com.quadro_a_quadro.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) Boolean ativos,
            Model model) {

        if (ativos != null && ativos) {
            model.addAttribute("filmes", filmeService.listarPorStatus(StatusFilme.EM_CARTAZ));
        } else if (busca != null && !busca.isBlank()) {
            model.addAttribute("filmes", filmeService.buscarPorTitulo(busca));
            model.addAttribute("busca", busca);
        } else {
            model.addAttribute("filmes", filmeService.listarTodos());
        }
        model.addAttribute("classificacoes", ClassificacaoFilme.values());
        model.addAttribute("generos", GeneroFilme.values());
        model.addAttribute("statusList", StatusFilme.values());
        return "filmes/listar";
    }

    @GetMapping("/dados/{id}")
    @ResponseBody
    public Filme buscarDados(@PathVariable Long id) {
        return filmeService.buscarPorId(id);
    }

    @PostMapping("/novo")
    public String cadastrar(
        @Valid @ModelAttribute Filme filme,
        BindingResult resultado,
        @RequestParam("capa") MultipartFile capa,
        RedirectAttributes redirectAttributes,
        Model model) {

    if (resultado.hasErrors()) {
        redirectAttributes.addFlashAttribute("erro", "Preencha todos os campos corretamente.");
        return "redirect:/filmes";
    }

    try {
        filmeService.cadastrar(filme, capa);
        redirectAttributes.addFlashAttribute("sucesso", "Filme cadastrado com sucesso!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("erro", e.getMessage());
    }

    return "redirect:/filmes";
    }

    @PostMapping("/editar/{id}")
    public String editar(
        @PathVariable Long id,
        @Valid @ModelAttribute Filme filme,
        BindingResult resultado,
        @RequestParam("capa") MultipartFile capa,
        RedirectAttributes redirectAttributes,
        Model model) {

    if (resultado.hasErrors()) {
        redirectAttributes.addFlashAttribute("erro", "Preencha todos os campos corretamente.");
        return "redirect:/filmes";
    }

    try {
        filmeService.editar(id, filme, capa);
        redirectAttributes.addFlashAttribute("sucesso", "Filme editado com sucesso!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("erro", e.getMessage());
    }

    return "redirect:/filmes";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            filmeService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Filme excluído com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/filmes";
    }

    @PostMapping("/status/{id}")
    public String alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusFilme status,
            RedirectAttributes redirectAttributes) {
        try {
            filmeService.alterarStatus(id, status);
            redirectAttributes.addFlashAttribute("sucesso", "Status alterado com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/filmes";
    }
}