package com.imperialnet.el_ceibo.controller;

import com.imperialnet.el_ceibo.dto.JugadorPagoDTO;
import com.imperialnet.el_ceibo.dto.PagoDTO;
import com.imperialnet.el_ceibo.dto.PagoFullDataDTO;
import com.imperialnet.el_ceibo.entity.Pago;
import com.imperialnet.el_ceibo.service.PagoService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody Pago pago, HttpServletRequest request) {
        try {
            Pago nuevoPago = pagoService.guardarPago(pago, request);
            return ResponseEntity.ok(nuevoPago);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve un 400 si algo falla
        }
    }

    @GetMapping("/estado-pagos") // Endpoint específico
    public ResponseEntity<List<JugadorPagoDTO>> obtenerEstadoPagos(
            @RequestParam Long categoriaId,
            @RequestParam Integer mes,
            @RequestParam Integer año
    ) {
        List<JugadorPagoDTO> jugadoresYPagos = pagoService.obtenerJugadoresYPagos(categoriaId, mes, año);
        return ResponseEntity.ok(jugadoresYPagos);
    }

    // Obtener pagos por jugador
    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<PagoFullDataDTO>> listarPagosPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorJugador(jugadorId));
    }

    // Obtener pagos por socio
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<PagoFullDataDTO>> listarPagosPorSocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorSocio(socioId));
    }

    // Obtener pagos entre fechas
    @GetMapping("/fechas")
    public ResponseEntity<List<PagoDTO>> listarPagosPorFechas(
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

    // Obtener listado total de socios y jugadores
    @GetMapping("/listadoGeneral")
    public ResponseEntity<List<?>> listadoGeneral() {
        return ResponseEntity.ok(pagoService.getListadoGeneralSociosYJugadores());
    }

    // Obtener listado general de pagos
    @GetMapping("/listadoGeneralPagos")
    public ResponseEntity<List<PagoFullDataDTO>> listadoGeneralPagos() {
        return ResponseEntity.ok(pagoService.getListadoGeneralPagos());
    }
    @GetMapping("/por-periodo")
    public ResponseEntity<List<PagoFullDataDTO>> obtenerPagosPorPeriodo(
            @RequestParam(required = false) Integer dia,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer año
    ) {
        List<PagoFullDataDTO> pagos = pagoService.filtrarPagosPorFecha(dia, mes, año);
        return ResponseEntity.ok(pagos);
    }

}
