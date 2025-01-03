package com.imperialnet.el_ceibo.controller;

import com.imperialnet.el_ceibo.dto.SocioDTO;
import com.imperialnet.el_ceibo.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    @Autowired
    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    // Crear un nuevo socio
    @PostMapping
    public ResponseEntity<SocioDTO> crearSocio(@RequestBody SocioDTO socioDTO) {
        SocioDTO nuevoSocio = socioService.crearSocio(socioDTO);
        return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
    }

    // Obtener un socio por ID
    @GetMapping("/{id}")
    public ResponseEntity<SocioDTO> obtenerSocioPorId(@PathVariable Long id) {
        return socioService.obtenerSocioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Listar todos los socios
    @GetMapping
    public ResponseEntity<List<SocioDTO>> listarSocios() {
        List<SocioDTO> socios = socioService.listarTodosLosSocios();
        return ResponseEntity.ok(socios);
    }

    // Actualizar un socio
    @PutMapping("/{id}")
    public ResponseEntity<SocioDTO> actualizarSocio(@PathVariable Long id, @RequestBody SocioDTO socioDTO) {
        try {
            SocioDTO socioActualizado = socioService.actualizarSocio(id, socioDTO);
            return ResponseEntity.ok(socioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Anular un socio
    @PutMapping("/{id}/anular")
    public ResponseEntity<Void> anularSocio(@PathVariable Long id) {
        try {
            socioService.anularSocio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Habilitar un socio
    @PutMapping("/{id}/habilitar")
    public ResponseEntity<Void> habilitarSocio(@PathVariable Long id) {
        try {
            socioService.habilitarSocio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
