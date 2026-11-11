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

    private List<Carta> cartasDoanteRobadas;
    private List<Carta> cartasPacienteRobadas;

    @Override
    public List<Carta> robarPaciente(int cantidad_robar) {
        for(int i=0; i<cantidad_robar; i++){
            Carta carta = cartasPaciente.remove(0);
            cartasPacienteRobadas.add(carta);
        }
        return cartasPacienteRobadas;
    }

    @Override
    public List<Carta> robarDoante(int cantidad_robar) {
        for(int i=0; i<cantidad_robar; i++){
            Carta carta = cartasDoante.remove(0);
            cartasDoanteRobadas.add(carta);
        }
        return cartasDoanteRobadas;
    }

    @Override
    public void emparejar(Carta doante, Carta paciente) {
        
    }

    @Override
    public void puntuar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'puntuar'");
    }

    //Metodo para reiniciar/Iniciar una nueva partida
    @Override
    public void nuevaPartida() {
        //se limpian todas las listas para evitar errores de duplicidad y se crean nuevas cartas para el juego
        cartasDoanteRobadas.clear();
        cartasPacienteRobadas.clear();
        cartasDoante.clear();
        cartasPaciente.clear();
        //editar este metodo para que las cartas no esten vacias
        for(int i=0; i<=15;i++){
            cartasDoante.add(new Carta());
            cartasPaciente.add(new Carta());
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
    }
    
}
