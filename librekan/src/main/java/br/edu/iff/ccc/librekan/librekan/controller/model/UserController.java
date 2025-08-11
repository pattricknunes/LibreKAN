package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    @GetMapping("/novo")
    public String novoUsuarioForm() {
        return "usuarios/form";
    }

    @PostMapping
    public String salvarUsuario(/* @ModelAttribute Usuario usuario */) {
        // usuarioService.salvarUsuario(usuario);
        return "redirect:/login";
    }
}