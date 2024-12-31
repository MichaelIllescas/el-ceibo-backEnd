package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.JugadorDTO;
import com.imperialnet.el_ceibo.entity.Jugador;
import com.imperialnet.el_ceibo.exception.JugadorNotFoundException;
import com.imperialnet.el_ceibo.repository.JugadorRepository;
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
                        jugador.getCategoria().getNombre() // Solo obtenemos el nombre de la categoría
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
}
