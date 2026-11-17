package com.doaplay.doaplay_backend.services;

import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.doaplay.doaplay_backend.dto.Carta;

@Service 
@Scope ("session")
public class JuegoServiceImpl implements JuegoService{

    private Random random = new Random();

    private List<Carta> cartasDoante;
    private List<Carta> cartasPaciente;
    private List<Carta> cartasJugador;
    private List<Carta> montonCartas;
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
    public void emparejar(Carta doante, Carta paciente) {
        if(doante.getTipo().equals(paciente.getTipo())){
            String mensaje="No se puede emparejar cartas del mismo tipo";
        }
        else{
        if(doante.getGrupoSaguineo().equals(paciente.getGrupoSaguineo())){
            montonCartas.add(doante);
            montonCartas.add(paciente);
            cartasJugador.remove(doante);
            cartasJugador.remove(paciente);
            puntuar();
        }
        else{
            String mensaje="No se puede emparejar cartas de diferente grupo sanguineo";
        }
    }
        
    }

    @Override
    public void puntuar() {
        puntajeJugador++;
    }

    //Metodo para reiniciar/Iniciar una nueva partida
    @Override
    public void nuevaPartida() {
        //se limpian todas las listas para evitar errores de duplicidad y se crean nuevas cartas para el juego
        cartasDoante.clear();
        cartasPaciente.clear();
        puntajeJugador=0;
        //cartasEspeciales.clear();
        //editar este metodo para que las cartas no esten vacias
        for(int i=0; i<=15;i++){
            cartasDoante.add(new Carta());
            cartasPaciente.add(new Carta());
            //cartasEspeciales.add(new Carta());
        }
    }
//Metodo que randomiza el orden de las cartas de ambas barajas
    @Override
    public void barajar() {
        for(int i=0; i<cartasDoante.size(); i++){
            int newPos=random.nextInt(cartasDoante.size());
            //se evita que la carta no se mueva de posicion
            while(newPos==i){
                newPos=random.nextInt(cartasDoante.size());
            }
            //se guarda la carta que se va a mover y se intercambia con la carta en la posicion aleatoria
            Carta cartaMoved=cartasDoante.get(newPos);
            cartasDoante.set(newPos, cartasDoante.get(i));
            cartasDoante.set(i, cartaMoved);
        }
        for(int i=0; i<cartasPaciente.size(); i++){
            int newPos=random.nextInt(cartasPaciente.size());
            while(newPos==i){
                newPos=random.nextInt(cartasPaciente.size());
            }
            Carta cartaMoved=cartasPaciente.get(newPos);
            cartasPaciente.set(newPos, cartasPaciente.get(i));
            cartasPaciente.set(i, cartaMoved);
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
    }

    /*@Override 
    public void jugarCartaEspecial(Carta carta) {
        
    }*/
    
    @Override 
    public void finalizarTurno() {
        robarCarta();
    }
}
