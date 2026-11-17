package com.doaplay.doaplay_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.doaplay.doaplay_backend.dto.Carta;
import com.doaplay.doaplay_backend.dto.Donante;
import com.doaplay.doaplay_backend.dto.enums.GrupoSaguineo;

@Service 
public class JuegoServiceImpl implements JuegoService{

    private Random random = new Random();

    private List<Carta> cartasDoante;
    private List<Carta> cartasPaciente;
    private List<Carta> cartasJugador;
    private List<Carta> montonCartas;
private List<Carta> barajaDonantes;
private List<Carta> barajaPacientes;
    private int puntajeJugador=0;
    
    // private List<Carta> cartasMesa; AUN NO LO HEMOS USADO
    //private List<Carta> cartasEspeciales; AUN NO LO HEMOS USADO

    @Override 
    public List<Carta> robarCarta() {
        if(cartasDoante.isEmpty() && cartasPaciente.isEmpty() /*&& cartasEspeciales.isEmpty()*/){
            return null;
        }
        else{
            while(cartasJugador.size()<5){
                int randomNum=random.nextInt(2);
                if(randomNum==0){
                    cartasJugador.add(cartasDoante.remove(0));
                }
                else if(randomNum==1){
                    cartasJugador.add(cartasPaciente.remove(0));
                }
                /*else if(randomNum==2){
                    cartasJugador.add(cartasEspeciales.remove(0));
                }*/
            }
            return cartasJugador;
        }
    }

    @Override
public Integer emparejar(Carta doante, Carta paciente) {

    Donante donante = new Donante();
    donante.setGrupoDon(doante.getGrupoSaguineo());
    donante.setGrupoPac(paciente.getGrupoSaguineo());

    if (donante.esSalvadoPor()) {

        montonCartas.add(doante);
        montonCartas.add(paciente);

        cartasDoante.remove(doante);
        cartasPaciente.remove(paciente);

        puntuar();

        if (!barajaDonantes.isEmpty()) {
            cartasDoante.add(barajaDonantes.remove(0));
        }

        if (!barajaPacientes.isEmpty()) {
            cartasPaciente.add(barajaPacientes.remove(0));
        }

    } else {
        String mensaje = "No se puede emparejar cartas de diferente grupo sanguíneo";
    }

    return puntajeJugador;
}

    @Override
    public void puntuar() {
        puntajeJugador++;
    }
  
    //Metodo para reiniciar/Iniciar una nueva partida
 @Override
public List<GrupoSaguineo> obtenerCuatroAleatorios() {

    List<GrupoSaguineo> grupos = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
        grupos.add(GrupoSaguineo.values()[random.nextInt(GrupoSaguineo.values().length)]);
    }

    return grupos;
}
    @Override
 public void nuevaPartida() {


    cartasDoante = new ArrayList<>();
    cartasPaciente = new ArrayList<>();

    barajaDonantes = new ArrayList<>();
    barajaPacientes = new ArrayList<>();

    cartasJugador = new ArrayList<>();
    montonCartas = new ArrayList<>();


    List<GrupoSaguineo> donantes = obtenerCuatroAleatorios();
    List<GrupoSaguineo> pacientes = obtenerCuatroAleatorios();

    for (int i = 0; i < 4; i++) {

        Carta donante = new Carta();
        Carta paciente = new Carta();

        donante.setGrupoSaguineo(donantes.get(i));
        paciente.setGrupoSaguineo(pacientes.get(i));

        cartasDoante.add(donante);
        cartasPaciente.add(paciente);
    
        barajaDonantes.add(donante);
        barajaPacientes.add(paciente);
    }

    barajar();
}

//Metodo que randomiza el orden de las cartas de ambas barajas

       public void barajar() {

    for (int i = 0; i < barajaDonantes.size(); i++) {

        int newPos = random.nextInt(barajaDonantes.size());

        Carta cartaMoved = barajaDonantes.get(newPos);

        barajaDonantes.set(newPos, barajaDonantes.get(i));
        barajaDonantes.set(i, cartaMoved);
    }

    for (int i = 0; i < barajaPacientes.size(); i++) {

        int newPos = random.nextInt(barajaPacientes.size());

        Carta cartaMoved = barajaPacientes.get(newPos);

        barajaPacientes.set(newPos, barajaPacientes.get(i));
        barajaPacientes.set(i, cartaMoved);
    }
}
        /*for(int i=0; i<cartasEspeciales.size(); i++){
            int newPos=random.nextInt(cartasEspeciales.size());
            while(newPos==i){
                newPos=random.nextInt(cartasEspeciales.size());
            }
            Carta cartaMoved=cartasEspeciales.get(newPos);
            cartasEspeciales.set(newPos, cartasEspeciales.get(i));
            cartasEspeciales.set(i, cartaMoved);
        }*/
 

    /*@Override 
    public void jugarCartaEspecial(Carta carta) {
        
    }*/

    @Override 
    public void finalizarTurno() {
        robarCarta();
    }
@Override
public List<Carta> obtenerDonantes() {
    return cartasDoante;
}

@Override
public List<Carta> obtenerPacientes() {
    return cartasPaciente;
}
public Integer emparejarPorGrupo(String grupoDonante, String grupoPaciente) {


    Carta donante = null;
    Carta paciente = null;

    for (Carta carta : cartasDoante) {
        if (carta.getGrupoSaguineo().name().equals(grupoDonante)) {
            donante = carta;
        }
    }

    for (Carta carta : cartasPaciente) {
        if (carta.getGrupoSaguineo().name().equals(grupoPaciente)) {
            paciente = carta;
        }
    }

    if (donante != null && paciente != null) {
        return emparejar(donante, paciente);
    }

    return puntajeJugador;
}
}
