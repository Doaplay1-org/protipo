package com.doaplay.doaplay_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.doaplay.doaplay_backend.dto.Carta;
import com.doaplay.doaplay_backend.dto.FormInfo;
import com.doaplay.doaplay_backend.dto.enums.GrupoSaguineo;
import com.doaplay.doaplay_backend.services.JuegoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller 
public class MainController {
    @Autowired 
    public JuegoServiceImpl ronda;
    @GetMapping({"/", "/doaplay"})
    public String showIndexView(Model model) {
        ronda.nuevaPartida();
        model.addAttribute("donante",ronda.obtenerDonantes());
        model.addAttribute("paciente",ronda.obtenerPacientes());

        model.addAttribute("formInfo", new FormInfo());
        return "vista";
    }
    @PostMapping("/submit")
    public String postMethodName(FormInfo formInfo, Model model) {
         model.addAttribute("jugada", ronda.emparejarPorGrupo(formInfo.getSeleccionDonante(), formInfo.getSeleccionPaciente()));
     model.addAttribute("donante", ronda.obtenerDonantes());
    model.addAttribute("paciente", ronda.obtenerPacientes());
    model.addAttribute("formInfo", new FormInfo());
        
         return "vista";
    }
    
    
}
