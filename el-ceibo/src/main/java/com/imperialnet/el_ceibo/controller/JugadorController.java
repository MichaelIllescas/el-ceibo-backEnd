package com.imperialnet.el_ceibo.controller;


import com.imperialnet.el_ceibo.dto.EstadoJugadorDTO;
import com.imperialnet.el_ceibo.dto.JugadorDTO;
import com.imperialnet.el_ceibo.entity.Categoria;
import com.imperialnet.el_ceibo.entity.Jugador;
import com.imperialnet.el_ceibo.repository.CategoriaRepository;
import com.imperialnet.el_ceibo.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;
    private final CategoriaRepository   categoriaRepository;

    @Autowired
    public JugadorController(JugadorService jugadorService, CategoriaRepository categoriaRepository) {
        this.jugadorService = jugadorService;
        this.categoriaRepository = categoriaRepository;
    }

    // Crear un nuevo jugador
    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
        Jugador nuevoJugador = jugadorService.crearJugador(jugador);
        return new ResponseEntity<>(nuevoJugador, HttpStatus.CREATED);
    }

    // Obtener un jugador por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerJugadorPorId(@PathVariable Long id) {
        return jugadorService.obtenerJugadorPorId(id)
                .map(jugador -> ResponseEntity.ok(jugador))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping
    public ResponseEntity<List<JugadorDTO>> listarJugadores() {
        List<JugadorDTO> jugadores = jugadorService.obtenerTodosLosJugadores();
        return ResponseEntity.ok(jugadores);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<?>> obtenerJugadoresPorCategoria(
            @PathVariable Long categoriaId
    ) {
        List<JugadorDTO> jugadores = jugadorService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(jugadores);
    }


    // Actualizar un jugador
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarJugador(@PathVariable Long id, @RequestBody JugadorDTO jugadorDTO) {
        try {
            // Buscar la categoría por nombre
            Categoria categoria = (Categoria) categoriaRepository.findByNombre(jugadorDTO.getCategoria())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

            // Convertir DTO a entidad Jugador
            Jugador jugador = jugadorService.obtenerJugadorPorId(id).orElse(null); // Obtener el jugador existente
            jugador.setNombre(jugadorDTO.getNombre());
            jugador.setApellido(jugadorDTO.getApellido());
            jugador.setDni(jugadorDTO.getDni());
            jugador.setTelefono(jugadorDTO.getTelefono());
            jugador.setEmail(jugadorDTO.getEmail());
            jugador.setCategoria(categoria); // Asignar el objeto de categoría

            // Guardar los cambios
            jugadorService.crearJugador(jugador);

            return ResponseEntity.ok(jugador);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Anular un jugador (cambiar el estado a inactivo)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anularJugador(@PathVariable Long id) {
        try {
            jugadorService.anularJugador(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoJugadorDTO>> obtenerTodosLosJugadores() {
        List<EstadoJugadorDTO> jugadores = jugadorService.obtenerTodosLosJugadoresDTO();
        if (jugadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jugadores);
    }
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<String> deshabilitarJugador(@PathVariable Long id) {
        try {
            jugadorService.deshabilitarJugador(id);
            return ResponseEntity.ok("Jugador deshabilitado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<String> habilitarJugador(@PathVariable Long id) {
        try {
            jugadorService.habilitarJugador(id);
            return ResponseEntity.ok("Jugador deshabilitado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

}