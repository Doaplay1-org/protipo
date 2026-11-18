package com.doaplay.doaplay_backend.services;

import com.doaplay.doaplay_backend.domain.Partida;
import com.doaplay.doaplay_backend.domain.enums.TipoSangre;

public interface PartidaService {
    Partida iniciarNuevaPartida();

    Partida getPartida();

    boolean verificarCompatibilidad(TipoSangre donante, TipoSangre paciente);

    boolean procesarEmparejamiento(int indiceDonante, int indicePaciente);

    String informarResultadoEmparejamiento(boolean esCompatible);
}