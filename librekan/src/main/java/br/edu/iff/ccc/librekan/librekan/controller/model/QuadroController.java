package br.edu.iff.ccc.librekan.librekan.controller.model;

import br.edu.iff.ccc.librekan.librekan.model.Quadro;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quadros")
public class QuadroController {

    private List<String> quadros = new ArrayList<>();

    @GetMapping
    public String listarQuadros(Model model) {
        model.addAttribute("quadros", quadros);
        return "quadros"; 
    }

    @PostMapping
    public String criarQuadro(@RequestParam("nome") String nome) {
        quadros.add(nome);
        return "redirect:/quadros"; 
    }

    @PostMapping("/excluir")
    public String excluirQuadro(@RequestParam("nome") String nome) {
        quadros.remove(nome);
        return "redirect:/quadros";
    }

}