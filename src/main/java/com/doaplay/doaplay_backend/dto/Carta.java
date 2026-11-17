package com.doaplay.doaplay_backend.dto;

import com.doaplay.doaplay_backend.dto.enums.GrupoSaguineo;
import com.doaplay.doaplay_backend.dto.enums.TipoCarta;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data 
@EqualsAndHashCode (of="grupoSaguineo")
public class Carta {
    
    private GrupoSaguineo grupoSaguineo;
    private TipoCarta tipo;   
}
