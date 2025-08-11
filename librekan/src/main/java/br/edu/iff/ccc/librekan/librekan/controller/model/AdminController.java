package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/novo")
    public String novoAdminForm() {
        return "admin/form";
    }

    @PostMapping
    public String salvarAdmin() {
        return "redirect:/admin/dashboard";
    }
}