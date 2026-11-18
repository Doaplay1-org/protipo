package com.doaplay.doaplay_backend.domain;

import java.util.ArrayList;
import java.util.List;

import com.doaplay.doaplay_backend.domain.enums.EstadoPartida;
import com.doaplay.doaplay_backend.domain.enums.TipoSangre;

import lombok.Data;

@Data
public class Partida {
   private List<TipoSangre> mazoDonantes = new ArrayList<>();
    private List<TipoSangre> mazoPacientes = new ArrayList<>();

    private List<TipoSangre> donantesVisibles = new ArrayList<>();
    private List<TipoSangre> pacientesVisibles = new ArrayList<>();

    private int contador = 0;
    private EstadoPartida estado = EstadoPartida.EN_CURSO;
}