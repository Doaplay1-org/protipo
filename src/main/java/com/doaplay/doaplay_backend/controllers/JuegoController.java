package com.doaplay.doaplay_backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.doaplay.doaplay_backend.dto.FormInfo;
import com.doaplay.doaplay_backend.services.PartidaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JuegoController {
    private final PartidaService partidaService;

    @GetMapping("/")
    public String ver(Model model) {
        return cargarVista(model);
    }

    @PostMapping("/nueva")
    public String nueva(Model model) {
        partidaService.iniciarNuevaPartida();
        return cargarVista(model);
    }

    @PostMapping("/emparejar")
    public String emparejar(@ModelAttribute("formInfo") FormInfo formInfo, Model model) {

        if (formInfo.getDonante() == null || formInfo.getPaciente() == null) {
            model.addAttribute("mensaje", "Selecciona un paciente y un donante.");
            model.addAttribute("tipoMensaje", "error");
            return cargarVista(model);
        }

        boolean correcto = partidaService.procesarEmparejamiento(
                formInfo.getDonante(), formInfo.getPaciente());

        model.addAttribute("mensaje", partidaService.informarResultadoEmparejamiento(correcto));
        model.addAttribute("tipoMensaje", correcto ? "ok" : "error");
        return cargarVista(model);
    }

    private String cargarVista(Model model) {
        model.addAttribute("partida", partidaService.getPartida());
        model.addAttribute("formInfo", new FormInfo()); // formulario vacío: sin radios marcados
        return "juegoView";
    }

}

