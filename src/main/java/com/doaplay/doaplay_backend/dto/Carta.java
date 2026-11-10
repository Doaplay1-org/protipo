package com.doaplay.doaplay_backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data 
@EqualsAndHashCode (of="grupoSaguineo")
public class Carta {
    
    private GrupoSaguineo grupoSaguineo;
    private TipoCarta tipo;   
}
