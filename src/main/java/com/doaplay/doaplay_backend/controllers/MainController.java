package com.doaplay.doaplay_backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class MainController {
    
    @GetMapping({"/", "/doaplay"})
    public String showIndexView(Model model) {
        
        return "vista";
    }
    
}
