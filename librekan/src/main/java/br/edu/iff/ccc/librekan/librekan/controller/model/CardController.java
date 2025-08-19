package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cartoes")
public class CardController {

    @GetMapping("/lista/{listaId}")
    public String listarCartoes(@PathVariable Long listaId, Model model) {
        return "cartoes/lista";
    }

    @GetMapping("/lista/{listaId}/novo")
    public String novoCartaoForm(@PathVariable Long listaId, Model model) {
        return "cartoes/form";
    }

    @PostMapping("/lista/{listaId}")
    public String salvarCartao(@PathVariable Long listaId) {
        return "redirect:/cartoes/lista/" + listaId;
    }

    @GetMapping("/{id}/editar")
    public String editarCartaoForm(@PathVariable Long id, Model model) {
        return "cartoes/form";
    }

    @PostMapping("/{id}")
    public String atualizarCartao(@PathVariable Long id/) {
        return "redirect:/quadros";
    }

    @GetMapping("/{id}/excluir")
    public String excluirCartao(@PathVariable Long id) {
        return "redirect:/quadros";
    }
}