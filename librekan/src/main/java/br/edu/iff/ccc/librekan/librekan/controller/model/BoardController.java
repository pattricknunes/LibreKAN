package br.edu.iff.ccc.librekan.librekan.controller.view;

import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quadros")
public class BoardController {

    private final QuadroService quadroService;

    public BoardController(QuadroService quadroService) {
        this.quadroService = quadroService;
    }

    @GetMapping
    public String listarQuadros(Model model) {
        model.addAttribute("quadros", quadroService.listarTodos());
        return "quadros/lista";
    }

    @GetMapping("/novo")
    public String novoQuadroForm(Model model) {
        model.addAttribute("quadro", new Quadro());
        return "quadros/form";
    }

    @PostMapping
    public String salvarQuadro(@ModelAttribute Quadro quadro) {
        quadroService.salvar(quadro);
        return "redirect:/quadros";
    }

    @GetMapping("/{id}/editar")
    public String editarQuadroForm(@PathVariable Long id, Model model) {
        Quadro quadro = quadroService.obterPorId(id);
        model.addAttribute("quadro", quadro);
        return "quadros/form";
    }

    @PostMapping("/{id}")
    public String atualizarQuadro(@PathVariable Long id, @ModelAttribute Quadro quadro) {
        quadro.setId(id);
        quadroService.atualizar(quadro);
        return "redirect:/quadros";
    }

    @GetMapping("/{id}/excluir")
    public String excluirQuadro(@PathVariable Long id) {
        quadroService.excluir(id);
        return "redirect:/quadros";
    }
}