package br.edu.iff.ccc.librekan.librekan.controller.view;

import br.edu.iff.ccc.librekan.librekan.dto.AdminDTO;
import br.edu.iff.ccc.librekan.librekan.dto.UsuarioDTO;
import br.edu.iff.ccc.librekan.librekan.model.Lista;
import br.edu.iff.ccc.librekan.librekan.model.Quadro;
import br.edu.iff.ccc.librekan.librekan.service.QuadroService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/quadros")
public class QuadroViewController {

    private final QuadroService quadroService;

    public QuadroViewController(QuadroService quadroService) {
        this.quadroService = quadroService;
    }

    @GetMapping
    public String listarQuadros(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/auth/login";
        }

        String email = "";
        if (usuarioLogado instanceof UsuarioDTO u) {
            email = u.getEmail();
        } else if (usuarioLogado instanceof AdminDTO a) {
            email = a.getEmail();
        }

        List<Quadro> quadrosDoUsuario = quadroService.listarPorDonoEmail(email);

        model.addAttribute("quadros", quadrosDoUsuario);
        model.addAttribute("quadro", new Quadro());

        return "quadros";
    }

    @PostMapping
    public String criarQuadro(@ModelAttribute("quadro") @Valid Quadro quadro,
            BindingResult result,
            HttpSession session,
            Model model) {

        Object usuarioLogado = session.getAttribute("usuarioLogado");
        if (usuarioLogado == null)
            return "redirect:/auth/login";

        if (result.hasErrors()) {
            model.addAttribute("quadros", quadroService.listarTodos());
            return "quadros";
        }

        String email = (usuarioLogado instanceof UsuarioDTO u) ? u.getEmail()
                : (usuarioLogado instanceof AdminDTO a) ? a.getEmail() : "";

        quadro.setDonoEmail(email);
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
        Quadro quadro = quadroService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Quadro não encontrado"));

        model.addAttribute("quadro", quadro);
        model.addAttribute("listas", quadro.getListas());
    
        model.addAttribute("novaLista", new Lista());

        return "listas";
    }
}