package br.edu.iff.ccc.librekan.librekan.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path= "principal2")

public class MainViewController {
    @GetMapping("/")
    public String getHomePage(){
        return "index.html";
    }
    
}
