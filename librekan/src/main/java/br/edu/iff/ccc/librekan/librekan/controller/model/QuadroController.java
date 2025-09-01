package br.edu.iff.ccc.librekan.librekan.controller.model;

import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/quadros")
public class QuadroController {

    private final QuadroService quadroService;

    public QuadroController(QuadroService quadroService) {
        this.quadroService = quadroService;
    }

    @GetMapping
    public String listarQuadros(Model model) {
        List<Quadro> quadros = quadroService.listarTodos();
        model.addAttribute("quadros", quadros);
        model.addAttribute("quadro", new Quadro());
        return "quadros";
    }

    @PostMapping
    public String criarQuadro(@ModelAttribute("quadro") @Valid Quadro quadro, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("quadros", quadroService.listarTodos());
            return "quadros";
        }
        quadroService.salvar(quadro);
        return "redirect:/quadros";
    }

    @PostMapping("/excluir")
    public String excluirQuadro(@RequestParam("id") Long id) {
        quadroService.excluir(id);
        return "redirect:/quadros";
    }

    @GetMapping("/{id}")
    public String visualizarQuadro(@PathVariable Long id, Model model) {
    Quadro quadro = quadroService.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Quadro não encontrado"));

    model.addAttribute("quadro", quadro);
    model.addAttribute("listas", quadro.getListas());
    return "listas";
}
}