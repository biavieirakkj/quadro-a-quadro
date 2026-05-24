package com.quadro_a_quadro.controller;

import com.quadro_a_quadro.model.Filme;
import com.quadro_a_quadro.model.enums.StatusFilme;
import com.quadro_a_quadro.model.enums.ClassificacaoFilme;
import com.quadro_a_quadro.model.enums.GeneroFilme;
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

    // RF002 - Listar filmes
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
        return "filmes/listar";
    }

    // RF001 - Exibir formulário de cadastro
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("filme", new Filme());
        model.addAttribute("classificacoes", ClassificacaoFilme.values());
        model.addAttribute("generos", GeneroFilme.values());
        model.addAttribute("statusList", StatusFilme.values());
        return "filmes/formulario";
    }

    // RF001 - Cadastrar filme
    @PostMapping("/novo")
    public String cadastrar(
            @Valid @ModelAttribute Filme filme,
            BindingResult resultado,
            @RequestParam("capa") MultipartFile capa,
            RedirectAttributes redirectAttributes,
            Model model) 
    {

        if (resultado.hasErrors()) 
        {
            model.addAttribute("classificacoes", ClassificacaoFilme.values());
            model.addAttribute("generos", GeneroFilme.values());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        try 
        {
            filmeService.cadastrar(filme, capa);
            redirectAttributes.addFlashAttribute("sucesso", "Filme cadastrado com sucesso!");
        } catch (Exception e) 
        {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("classificacoes", ClassificacaoFilme.values());
            model.addAttribute("generos", GeneroFilme.values());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        return "redirect:/filmes";
    }

    // RF003 - Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) 
    {
        model.addAttribute("filme", filmeService.buscarPorId(id));
        model.addAttribute("classificacoes", ClassificacaoFilme.values());
        model.addAttribute("generos", GeneroFilme.values());
        model.addAttribute("statusList", StatusFilme.values());
        return "filmes/formulario";
    }

    // RF003 - Editar filme
    @PostMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            @Valid @ModelAttribute Filme filme,
            BindingResult resultado,
            @RequestParam("capa") MultipartFile capa,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (resultado.hasErrors()) 
        {
            model.addAttribute("classificacoes", ClassificacaoFilme.values());
            model.addAttribute("generos", GeneroFilme.values());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        try 
        {
            filmeService.editar(id, filme, capa);
            redirectAttributes.addFlashAttribute("sucesso", "Filme editado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("classificacoes", ClassificacaoFilme.values());
            model.addAttribute("generos", GeneroFilme.values());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        return "redirect:/filmes";
    }

    // RF004 - Excluir filme
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

    // RF005 - Alterar status
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