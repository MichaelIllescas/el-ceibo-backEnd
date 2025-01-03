package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.EstadoJugadorDTO;
import com.imperialnet.el_ceibo.dto.JugadorDTO;
import com.imperialnet.el_ceibo.entity.EstadoJugador;
import com.imperialnet.el_ceibo.entity.Jugador;
import com.imperialnet.el_ceibo.exception.JugadorNotFoundException;
import com.imperialnet.el_ceibo.repository.JugadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }


    public Jugador crearJugador(Jugador jugador) {
        jugador.setFechaRegistro(LocalDate.now());
        return jugadorRepository.save(jugador);
    }


    public Optional<Jugador> obtenerJugadorPorId(Long id) {
        return jugadorRepository.findById(id);
    }


    public List<JugadorDTO> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = jugadorRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato de la fecha

        return jugadores.stream()
                .map(jugador -> new JugadorDTO(
                        jugador.getId(),
                        jugador.getNombre(),
                        jugador.getApellido(),
                        jugador.getDni(),
                        jugador.getTelefono(),
                        jugador.getEmail(),
                        jugador.getFechaRegistro().format(formatter), // Formatea la fecha
                        jugador.getCategoria().getNombre(),
                        jugador.getEstado().name()// Solo obtenemos el nombre de la categoría
                ))
                .collect(Collectors.toList());
    }

    public Jugador actualizarJugador(Long id, Jugador jugadorActualizado) {
        if (!jugadorRepository.existsById(id)) {
            throw new JugadorNotFoundException("El jugador con ID " + id + " no existe.");
        }
        jugadorActualizado.setId(id);
        return jugadorRepository.save(jugadorActualizado);
    }


    public void anularJugador(Long id) {
        jugadorRepository.findById(id).ifPresentOrElse(jugador -> {
            jugador.setFechaBaja(LocalDate.now());
            jugadorRepository.save(jugador);
        }, () -> {
            throw new JugadorNotFoundException("El jugador con ID " + id + " no existe.");
        });
    }

    // Método para obtener todos los jugadores como EstadoJugadorDTO
    public List<EstadoJugadorDTO> obtenerTodosLosJugadoresDTO() {
        List<Jugador> jugadores = jugadorRepository.findAll();
        return jugadores.stream()
                .map(this::convertirJugadorAEstadoDTO)
                .collect(Collectors.toList());
    }

    // Conversión de Jugador a EstadoJugadorDTO
    private EstadoJugadorDTO convertirJugadorAEstadoDTO(Jugador jugador) {
        return new EstadoJugadorDTO(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getApellido(),
                jugador.getDni(),
                jugador.getEstado().name() // Convertir enum EstadoJugador a String
        );
    }

    public List<Jugador> obtenerJugadoresActivos() {
        return jugadorRepository.findByEstado(EstadoJugador.ACTIVO);
    }

    @Transactional
    public void deshabilitarJugador(Long id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        jugador.anular();
        jugadorRepository.save(jugador);
    }
    public void habilitarJugador(Long id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        jugador.habilitar();
        jugadorRepository.save(jugador);
    }

}
