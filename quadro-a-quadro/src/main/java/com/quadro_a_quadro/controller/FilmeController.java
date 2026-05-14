package com.quadro_a_quadro.controller;

import com.quadro_a_quadro.model.Filme;
import com.quadro_a_quadro.model.enums.StatusFilme;
import com.quadro_a_quadro.service.FilmeService;
import com.quadro_a_quadro.service.ClassificacaoService;
import com.quadro_a_quadro.service.GeneroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filmes")
public class FilmeController 
{

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private ClassificacaoService classificacaoService;

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) 
    {
        if (busca != null && !busca.isBlank()) 
        {
            model.addAttribute("filmes", filmeService.buscarPorTitulo(busca));
            model.addAttribute("busca", busca);
        } else 
        {
            model.addAttribute("filmes", filmeService.listarTodos());
        }
        return "filmes/listar";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) 
    {
        model.addAttribute("filme", new Filme());
        model.addAttribute("classificacoes", classificacaoService.listarTodas());
        model.addAttribute("generos", generoService.listarTodos());
        model.addAttribute("statusList", StatusFilme.values());
        return "filmes/formulario";
    }

    @PostMapping("/novo")
    public String cadastrar(
            @Valid @ModelAttribute Filme filme,
            BindingResult resultado,
            RedirectAttributes redirectAttributes,
            Model model) 
    {

        if (resultado.hasErrors()) 
        {
            model.addAttribute("classificacoes", classificacaoService.listarTodas());
            model.addAttribute("generos", generoService.listarTodos());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        try 
        {
            filmeService.cadastrar(filme);
            redirectAttributes.addFlashAttribute("sucesso", "Filme cadastrado com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("classificacoes", classificacaoService.listarTodas());
            model.addAttribute("generos", generoService.listarTodos());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        return "redirect:/filmes";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) 
    {
        model.addAttribute("filme", filmeService.buscarPorId(id));
        model.addAttribute("classificacoes", classificacaoService.listarTodas());
        model.addAttribute("generos", generoService.listarTodos());
        model.addAttribute("statusList", StatusFilme.values());
        return "filmes/formulario";
    }

    @PostMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            @Valid @ModelAttribute Filme filme,
            BindingResult resultado,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (resultado.hasErrors()) 
        {
            model.addAttribute("classificacoes", classificacaoService.listarTodas());
            model.addAttribute("generos", generoService.listarTodos());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        try 
        {
            filmeService.editar(id, filme);
            redirectAttributes.addFlashAttribute("sucesso", "Filme editado com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("classificacoes", classificacaoService.listarTodas());
            model.addAttribute("generos", generoService.listarTodos());
            model.addAttribute("statusList", StatusFilme.values());
            return "filmes/formulario";
        }

        return "redirect:/filmes";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) 
    {
        try 
        {
            filmeService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Filme excluído com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/filmes";
    }

    @PostMapping("/status/{id}")
    public String alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusFilme status,
            RedirectAttributes redirectAttributes) 
    {
        try 
        {
            filmeService.alterarStatus(id, status);
            redirectAttributes.addFlashAttribute("sucesso", "Status alterado com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/filmes";
    }
}