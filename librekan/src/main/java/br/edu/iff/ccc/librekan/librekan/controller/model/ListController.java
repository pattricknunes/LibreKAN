package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/listas")
public class ListController {

    @GetMapping("/quadro/{quadroId}")
    public String listarListas(@PathVariable Long quadroId, Model model) {
        return "listas/lista";
    }

    @GetMapping("/quadro/{quadroId}/nova")
    public String novaListaForm(@PathVariable Long quadroId, Model model) {
        return "listas/form";
    }

    @PostMapping("/quadro/{quadroId}")
    public String salvarLista(@PathVariable Long quadroId) {
        return "redirect:/listas/quadro/" + quadroId;
    }

    @GetMapping("/{id}/editar")
    public String editarListaForm(@PathVariable Long id, Model model) {
        return "listas/form";
    }

    @PostMapping("/{id}")
    public String atualizarLista(@PathVariable Long id/) {
        return "redirect:/quadros";
    }

    @GetMapping("/{id}/excluir")
    public String excluirLista(@PathVariable Long id) {
        return "redirect:/quadros";
    }
}