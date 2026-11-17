package com.doaplay.doaplay_backend.services;

import java.util.List;

import com.doaplay.doaplay_backend.dto.Carta;
import com.doaplay.doaplay_backend.dto.Donante;
import com.doaplay.doaplay_backend.dto.Jugada;

public interface JuegoService {
    public List<Carta> robarCarta();
    public void emparejar(Carta doante, Carta paciente);
    public void puntuar();
    public void nuevaPartida();
    public void barajar();
    public void finalizarTurno();
    //public void jugarCartaEspecial(Carta carta);

}
