package com.doaplay.doaplay_backend.dto;

import lombok.Data;

@Data 
public class Donante {
    private GrupoSaguineo grupoDon;
    private GrupoSaguineo grupoPac;

    public boolean esSalvadoPor() {

        if (grupoPac == GrupoSaguineo.O_NEGATIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO;
        }

        else if (grupoPac == GrupoSaguineo.O_POSITIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO ||
                   grupoDon == GrupoSaguineo.O_POSITIVO;
        }

        else if (grupoPac == GrupoSaguineo.A_NEGATIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO ||
                   grupoDon == GrupoSaguineo.A_NEGATIVO;
        }

        else if (grupoPac == GrupoSaguineo.A_POSITIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO ||
                   grupoDon == GrupoSaguineo.O_POSITIVO ||
                   grupoDon == GrupoSaguineo.A_NEGATIVO ||
                   grupoDon == GrupoSaguineo.A_POSITIVO;
        }

        else if (grupoPac == GrupoSaguineo.B_NEGATIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO ||
                   grupoDon == GrupoSaguineo.B_NEGATIVO;
        }

        else if (grupoPac == GrupoSaguineo.B_POSITIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO ||
                   grupoDon == GrupoSaguineo.O_POSITIVO ||
                   grupoDon == GrupoSaguineo.B_NEGATIVO ||
                   grupoDon == GrupoSaguineo.B_POSITIVO;
        }

        else if (grupoPac == GrupoSaguineo.AB_NEGATIVO) {
            return grupoDon == GrupoSaguineo.O_NEGATIVO ||
                   grupoDon == GrupoSaguineo.A_NEGATIVO ||
                   grupoDon == GrupoSaguineo.B_NEGATIVO ||
                   grupoDon == GrupoSaguineo.AB_NEGATIVO;
        }

        else if (grupoPac == GrupoSaguineo.AB_POSITIVO) {
            return true;
        }

        return false;
    }
}


