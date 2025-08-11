package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quadros")
public class BoardController {

    @GetMapping
    public String listarQuadros(Model model) {
        return "quadros/lista";
    }

    @GetMapping("/novo")
    public String novoQuadroForm(Model model) {
        return "quadros/form";
    }

    @PostMapping
    public String salvarQuadro() {
        return "redirect:/quadros";
    }

    @GetMapping("/{id}/editar")
    public String editarQuadroForm(@PathVariable Long id, Model model) {
        return "quadros/form";
    }

    @PostMapping("/{id}")
    public String atualizarQuadro(@PathVariable Long id/) {
        return "redirect:/quadros";
    }

    @GetMapping("/{id}/excluir")
    public String excluirQuadro(@PathVariable Long id) {
        return "redirect:/quadros";
    }
}