package com.imperialnet.el_ceibo.controller;


import com.imperialnet.el_ceibo.dto.PagoDTO;
import com.imperialnet.el_ceibo.entity.Pago;
import com.imperialnet.el_ceibo.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Registrar o actualizar un pago
     @PostMapping
    public ResponseEntity<PagoDTO> registrarPago(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.guardarPago(pago);
        PagoDTO pagoDTO = pagoService.convertirAPagoDTO(nuevoPago);
        return ResponseEntity.ok(pagoDTO);
    }

    // Obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        List<PagoDTO> pagosDTO = pagoService.obtenerTodosLosPagos(); // Llama al método correcto
        return ResponseEntity.ok(pagosDTO);
    }


    // Obtener pagos por jugador
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Pago>> listarPagosPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorJugador(jugadorId));
    }

    // Obtener pagos por socio
//    @GetMapping("/socio/{socioId}")
//    public ResponseEntity<List<Pago>> listarPagosPorSocio(@PathVariable Long socioId) {
//        return ResponseEntity.ok(pagoService.obtenerPagosPorSocio(socioId));
//    }

    // Obtener pagos entre fechas
    @GetMapping("/fechas")
    public ResponseEntity<List<Pago>> listarPagosPorFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorFechas(inicio, fin));
    }

    // Eliminar un pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}