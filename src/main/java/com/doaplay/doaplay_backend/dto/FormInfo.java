package com.doaplay.doaplay_backend.dto;

import lombok.Data;

@Data
public class FormInfo {
    private Integer donante; // índice del donante elegido (null si no elige)
    private Integer paciente; // índice del paciente elegido
}