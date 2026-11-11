package com.doaplay.doaplay_backend.services;

import java.util.List;

import com.doaplay.doaplay_backend.dto.Carta;

public interface JuegoService {
    public List<Carta> robarPaciente(int cantidad_robar);
    public List<Carta> robarDoante(int cantidad_robar);
    public void emparejar(Carta doante,Carta paciente);
    public void puntuar();
    public void nuevaPartida();
    public void barajar();

}
