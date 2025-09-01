package br.edu.iff.ccc.librekan.librekan.controller.model;

import br.edu.iff.ccc.librekan.librekan.dto.UsuarioDTO;
import br.edu.iff.ccc.librekan.librekan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "usuarios/form";
    }

    @PostMapping
    public String salvarUsuario(@ModelAttribute("usuario") @Valid UsuarioDTO usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "usuarios/form";
        }
        userService.salvarUsuario(usuario);
        return "redirect:/login";
    }
}