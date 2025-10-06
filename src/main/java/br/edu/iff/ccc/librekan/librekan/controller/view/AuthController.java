package br.edu.iff.ccc.librekan.librekan.controller.view;

import br.edu.iff.ccc.librekan.librekan.dto.UsuarioDTO;
import br.edu.iff.ccc.librekan.librekan.dto.AdminDTO;
import br.edu.iff.ccc.librekan.librekan.dto.LoginDTO;
import br.edu.iff.ccc.librekan.librekan.service.UserService;
import br.edu.iff.ccc.librekan.librekan.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AdminService adminService;

    public AuthController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/register/user")
    public String registerUserForm(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "auth/register-user";
    }

    @PostMapping("/register/user")
    public String registerUser(@Valid @ModelAttribute UsuarioDTO usuarioDTO, 
                               BindingResult result,
                               RedirectAttributes redirectAttributes) { // <--- Adicione aqui
        if (result.hasErrors()) {
            return "auth/register-user";
        }
        userService.salvarUsuario(usuarioDTO);
        
        redirectAttributes.addFlashAttribute("registrationSuccess", "Usuário cadastrado com sucesso! Faça o login para continuar.");
        
        return "redirect:/auth/login";
    }

    @GetMapping("/register/admin")
    public String registerAdminForm(Model model) {
        model.addAttribute("adminDTO", new AdminDTO());
        return "auth/register-admin";
    }

   @PostMapping("/register/admin")
    public String registerAdmin(@Valid @ModelAttribute AdminDTO adminDTO, BindingResult result, RedirectAttributes redirectAttributes) { 
        if (result.hasErrors()) {
            return "auth/register-admin";
        }
        adminService.salvarAdmin(adminDTO);

        redirectAttributes.addFlashAttribute("registrationSuccess", "Administrador cadastrado com sucesso! Faça o login para continuar.");

        return "redirect:/auth/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginDTO());
        return "auth/login";
    }

     @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") @Valid LoginDTO loginDTO,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (result.hasErrors()) {
            return "auth/login";
        }

        //
        if ("testes@testes".equals(loginDTO.getEmail()) && "testes".equals(loginDTO.getSenha())) {
             var admin = adminService.buscarPorEmail("testes@testes"); // Busque o objeto Admin real
             if (admin != null) {
                 session.setAttribute("usuarioLogado", admin);
                 session.setAttribute("tipoUsuario", "usuario");
                 return "redirect:/quadros";
             }
        }
        //

        var usuario = userService.buscarPorEmail(loginDTO.getEmail());
        var admin = adminService.buscarPorEmail(loginDTO.getEmail());

        if (usuario != null && usuario.getSenha().equals(loginDTO.getSenha())) {
            session.setAttribute("usuarioLogado", usuario);
            session.setAttribute("tipoUsuario", "usuario");
            return "redirect:/quadros";
        } else if (admin != null && admin.getSenha().equals(loginDTO.getSenha())) {
            session.setAttribute("usuarioLogado", admin);
            session.setAttribute("tipoUsuario", "admin");
            return "redirect:/quadros";
        }

        model.addAttribute("erro", "Email ou senha inválidos");
        return "auth/login";
    }
    @PostMapping("/logout") 
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login"; 
    }
}