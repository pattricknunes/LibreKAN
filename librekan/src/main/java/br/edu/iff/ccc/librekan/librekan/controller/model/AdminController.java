package br.edu.iff.ccc.librekan.librekan.controller.model;

import br.edu.iff.ccc.librekan.librekan.dto.AdminDTO;
import br.edu.iff.ccc.librekan.librekan.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/novo")
    public String novoAdminForm(Model model) {
        model.addAttribute("admin", new AdminDTO());
        return "admin/form";
    }

    @PostMapping
    public String salvarAdmin(@ModelAttribute("admin") @Valid AdminDTO admin, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/form";
        }
        adminService.salvarAdmin(admin);
        return "redirect:/admin/dashboard";
    }
}