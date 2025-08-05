package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping(path = "principal")

public class MainViewController {
    @GetMapping("/")
    public String getHomePage(){
        return "index.html";
    }

    @GetMapping("/quadros")
    public String getQuadrosPage() {
        return "quadros.html";
    }

    @PostMapping("/quadros/add")
    public String postQuadrosPage(@PathVariable("add") String add, Model model) {
        model.addAttribute("add");
        return "quadros.html";
    }

   
    
    
}
