package br.edu.iff.ccc.librekan.librekan.controller.api;

import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quadros")
public class QuadroRestController {
    private final QuadroService quadroService;

    public QuadroRestController(QuadroService quadroService) {
        this.quadroService = quadroService;
    }

    @GetMapping
    public List<Quadro> listar() {
        return quadroService.listarTodos();
    }

    @PostMapping
    public Quadro criar(@RequestBody Quadro quadro) {
        return quadroService.salvar(quadro);
    }
}
