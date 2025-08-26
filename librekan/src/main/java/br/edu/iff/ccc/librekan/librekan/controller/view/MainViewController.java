package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {
    @GetMapping("/")
    public String getHomePage(){
        return "redirect:/quadros";
    }
    
}
