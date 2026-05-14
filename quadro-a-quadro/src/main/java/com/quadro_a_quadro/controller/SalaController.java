package com.quadro_a_quadro.controller;

import com.quadro_a_quadro.model.Sala;
import com.quadro_a_quadro.model.enums.StatusSala;
import com.quadro_a_quadro.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/salas")
public class SalaController 
{

    @Autowired
    private SalaService salaService;

    @GetMapping
    public String listar(Model model) 
    {
        model.addAttribute("salas", salaService.listarTodas());
        return "salas/listar";
    }

    @GetMapping("/nova")
    public String exibirFormularioCadastro(Model model) 
    {
        model.addAttribute("sala", new Sala());
        model.addAttribute("statusList", StatusSala.values());
        return "salas/formulario";
    }

    // RF009 - Cadastrar sala
    @PostMapping("/nova")
    public String cadastrar(
            @Valid @ModelAttribute Sala sala,
            BindingResult resultado,
            RedirectAttributes redirectAttributes,
            Model model) 
    {

        if (resultado.hasErrors()) 
        {
            model.addAttribute("statusList", StatusSala.values());
            return "salas/formulario";
        }

        try 
        {
            salaService.cadastrar(sala);
            redirectAttributes.addFlashAttribute("sucesso", "Sala cadastrada com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("statusList", StatusSala.values());
            return "salas/formulario";
        }

        return "redirect:/salas";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) 
    {
        model.addAttribute("sala", salaService.buscarPorId(id));
        model.addAttribute("statusList", StatusSala.values());
        return "salas/formulario";
    }

    @PostMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            @Valid @ModelAttribute Sala sala,
            BindingResult resultado,
            RedirectAttributes redirectAttributes,
            Model model) 
    {

        if (resultado.hasErrors()) 
        {
            model.addAttribute("statusList", StatusSala.values());
            return "salas/formulario";
        }

        try 
        {
            salaService.editar(id, sala);
            redirectAttributes.addFlashAttribute("sucesso", "Sala editada com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("statusList", StatusSala.values());
            return "salas/formulario";
        }

        return "redirect:/salas";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) 
    {
        try 
        {
            salaService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Sala excluída com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/salas";
    }


    @PostMapping("/status/{id}")
    public String alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusSala status,
            RedirectAttributes redirectAttributes) 
    {
        try 
        {
            salaService.alterarStatus(id, status);
            redirectAttributes.addFlashAttribute("sucesso", "Status alterado com sucesso!");
        } 
        catch (RuntimeException e) 
        {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/salas";
    }
}