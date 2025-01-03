package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.PagoDTO;
import com.imperialnet.el_ceibo.entity.Pago;
import com.imperialnet.el_ceibo.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    @Autowired
    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    // Crear o actualizar un pago
    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    // Buscar un pago por ID
    public Optional<Pago> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    // Buscar pagos por jugador
    public List<Pago> obtenerPagosPorJugador(Long jugadorId) {
        return pagoRepository.findByJugadorId(jugadorId);
    }

    // Buscar pagos entre fechas
    public List<Pago> obtenerPagosPorFechas(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findByFechaPagoBetween(inicio, fin);
    }

    // Eliminar un pago por ID
    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }

    // Listar todos los pagos como DTOs
    public List<PagoDTO> obtenerTodosLosPagos() {
        List<Pago> pagos = pagoRepository.findAll(); // Consulta todos los pagos
        return pagos.stream()
                .map(this::convertirAPagoDTO) // Convierte cada Pago a un PagoDTO
                .collect(Collectors.toList());
    }

    // Método para convertir Pago a PagoDTO
    public PagoDTO convertirAPagoDTO(Pago pago) {
        return new PagoDTO(
                pago.getId(),
                pago.getFechaPago(),
                pago.getMonto(),
                pago.getDescripcion(),
                pago.getCuota().getId(),  // Solo el ID de la cuota
                pago.getJugador() != null ? pago.getJugador().getId() : null // Verificar si existe un jugador
        );
    }
}
