package com.doaplay.doaplay_backend.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.doaplay.doaplay_backend.domain.Partida;
import com.doaplay.doaplay_backend.domain.enums.EstadoPartida;
import com.doaplay.doaplay_backend.domain.enums.TipoSangre;

import lombok.RequiredArgsConstructor;

@Service
@SessionScope
@RequiredArgsConstructor

public class PartidaServiceImpl implements PartidaService {

    private static final int CARTAS_VISIBLES = 4;
    private Partida partida;

    // ---------- Partida ----------

     @Override
    public Partida iniciarNuevaPartida() {
        partida = new Partida();
        partida.setMazoDonantes(crearBaraja(composicionDonantes()));
        partida.setMazoPacientes(crearBaraja(composicionPacientes()));
        reponer();
        evaluarEstado();
        return partida;
    }

    @Override
    public Partida getPartida() {
        if (partida == null) {
            iniciarNuevaPartida();
        }
        return partida;
    }

     // ---------- Barajas ----------
    // Ajusta estas cantidades para cambiar la dificultad

    private Map<TipoSangre, Integer> composicionPacientes() {
        Map<TipoSangre, Integer> m = new EnumMap<>(TipoSangre.class);
        m.put(TipoSangre.O_NEGATIVO, 2);
        m.put(TipoSangre.O_POSITIVO, 2);
        m.put(TipoSangre.A_NEGATIVO, 2);
        m.put(TipoSangre.A_POSITIVO, 2);
        m.put(TipoSangre.B_NEGATIVO, 2);
        m.put(TipoSangre.B_POSITIVO, 2);
        m.put(TipoSangre.AB_NEGATIVO, 2);
        m.put(TipoSangre.AB_POSITIVO, 1);
        return m;
    }

    private Map<TipoSangre, Integer> composicionDonantes() {
        Map<TipoSangre, Integer> m = new EnumMap<>(TipoSangre.class);
        m.put(TipoSangre.O_NEGATIVO, 3);
        m.put(TipoSangre.O_POSITIVO, 2);
        m.put(TipoSangre.A_NEGATIVO, 2);
        m.put(TipoSangre.A_POSITIVO, 2);
        m.put(TipoSangre.B_NEGATIVO, 2);
        m.put(TipoSangre.B_POSITIVO, 1);
        m.put(TipoSangre.AB_NEGATIVO, 2);
        m.put(TipoSangre.AB_POSITIVO, 1);
        return m;
    }

     private List<TipoSangre> crearBaraja(Map<TipoSangre, Integer> composicion) {
        List<TipoSangre> baraja = new ArrayList<>();
        
        composicion.forEach((tipo, cantidad) -> {
            for (int i = 0; i < cantidad; i++) {
                baraja.add(tipo);
            }
        });
        Collections.shuffle(baraja);
        return baraja;
    }

    // ---------- Compatibilidad ----------

    @Override
    public boolean verificarCompatibilidad(TipoSangre donante, TipoSangre paciente) {
        if (donante == paciente || donante == TipoSangre.O_NEGATIVO) {
            return true;
        }

        switch (paciente) {
            case O_POSITIVO:
                return donante == TipoSangre.O_POSITIVO;

            case A_POSITIVO:
                return donante == TipoSangre.A_POSITIVO
                    || donante == TipoSangre.A_NEGATIVO
                    || donante == TipoSangre.O_POSITIVO;

            case B_POSITIVO:
                return donante == TipoSangre.B_POSITIVO
                    || donante == TipoSangre.B_NEGATIVO
                    || donante == TipoSangre.O_POSITIVO;

            case AB_POSITIVO:
                return true; // receptor universal

            case A_NEGATIVO:
                return donante == TipoSangre.A_NEGATIVO;

            case B_NEGATIVO:
                return donante == TipoSangre.B_NEGATIVO;

            case AB_NEGATIVO:
                return donante == TipoSangre.AB_NEGATIVO
                    || donante == TipoSangre.A_NEGATIVO
                    || donante == TipoSangre.B_NEGATIVO;

            default:
                return false;
        }
    }

    // ---------- Emparejamiento ----------

    @Override
    public boolean procesarEmparejamiento(int indiceDonante, int indicePaciente) {
        Partida p = getPartida();

        if (p.getEstado() != EstadoPartida.EN_CURSO) {
            return false;
        }
        if (indiceDonante < 0 || indiceDonante >= p.getDonantesVisibles().size()
                || indicePaciente < 0 || indicePaciente >= p.getPacientesVisibles().size()) {
            return false;
        }

        TipoSangre donante = p.getDonantesVisibles().get(indiceDonante);
        TipoSangre paciente = p.getPacientesVisibles().get(indicePaciente);

        if (!verificarCompatibilidad(donante, paciente)) {
            return false; // no se modifica nada
        }

        // Compatibles: se retiran ambas cartas y se suma el punto
        p.getDonantesVisibles().remove(indiceDonante);
        p.getPacientesVisibles().remove(indicePaciente);
        p.setContador(p.getContador() + 1);

        reponer();
        evaluarEstado();
        return true;
    }

    @Override
    public String informarResultadoEmparejamiento(boolean esCompatible) {
        if (esCompatible) {
            return "¡Emparejamiento exitoso! Las cartas son compatibles.";
        }
        return "Emparejamiento fallido. Las cartas no son compatibles.";
    }

    // ---------- Lógica interna ----------

    // Vuelve a tener 4 + 4 cartas visibles mientras queden en los mazos
    private void reponer() {
        while (partida.getDonantesVisibles().size() < CARTAS_VISIBLES
                && !partida.getMazoDonantes().isEmpty()) {
            partida.getDonantesVisibles().add(partida.getMazoDonantes().remove(0));
        }
        while (partida.getPacientesVisibles().size() < CARTAS_VISIBLES
                && !partida.getMazoPacientes().isEmpty()) {
            partida.getPacientesVisibles().add(partida.getMazoPacientes().remove(0));
        }
    }

    // Victoria: no queda ninguna carta. Pérdida: bloqueo, ninguna pareja posible.
    private void evaluarEstado() {
        boolean sinCartas = partida.getDonantesVisibles().isEmpty()
                && partida.getPacientesVisibles().isEmpty()
                && partida.getMazoDonantes().isEmpty()
                && partida.getMazoPacientes().isEmpty();

        if (sinCartas) {
            partida.setEstado(EstadoPartida.VICTORIA);
            return;
        }

        for (TipoSangre d : partida.getDonantesVisibles()) {
            for (TipoSangre pa : partida.getPacientesVisibles()) {
                if (verificarCompatibilidad(d, pa)) {
                    partida.setEstado(EstadoPartida.EN_CURSO);
                    return;
                }
            }
        }
        partida.setEstado(EstadoPartida.PERDIDA);
    }

    
}

