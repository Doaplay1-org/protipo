package com.doaplay.doaplay_backend.services;

import java.util.List;
import java.util.stream.Gatherer.Integrator;

import com.doaplay.doaplay_backend.dto.Carta;
import com.doaplay.doaplay_backend.dto.enums.GrupoSaguineo;

public interface JuegoService {
    public List<Carta> robarCarta();
    public Integer emparejar(Carta doante, Carta paciente);
    public void puntuar();
    public void nuevaPartida();
    public void barajar();
    public List<Carta> obtenerDonantes();
    public List<Carta> obtenerPacientes(); 
    public void finalizarTurno();
    public  List<GrupoSaguineo> obtenerCuatroAleatorios();
    public Integer emparejarPorGrupo(String grupoDonante, String grupoPaciente) ;
    //public void jugarCartaEspecial(Carta carta);

}
