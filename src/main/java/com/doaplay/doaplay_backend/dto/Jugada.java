package com.doaplay.doaplay_backend.dto;

import lombok.Data;

@Data 
public class Jugada {
    private Carta donante;
    private  Carta paciente;
    private Boolean  correcto;
    
}
