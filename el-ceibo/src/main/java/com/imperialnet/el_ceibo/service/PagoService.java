package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.JugadorPagoDTO;
import com.imperialnet.el_ceibo.dto.PagoDTO;
import com.imperialnet.el_ceibo.dto.PagoFullDataDTO;
import com.imperialnet.el_ceibo.dto.PersonaDTO;
import com.imperialnet.el_ceibo.entity.*;
import com.imperialnet.el_ceibo.repository.CuotaRepository;
import com.imperialnet.el_ceibo.repository.JugadorRepository;
import com.imperialnet.el_ceibo.repository.PagoRepository;
import com.imperialnet.el_ceibo.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final SocioRepository socioRepository;
    private final JugadorRepository jugadorRepository;
    private final CuotaRepository cuotaRepository;

    @Autowired
    public PagoService(PagoRepository pagoRepository, SocioRepository socioRepository, JugadorRepository jugadorRepository, CuotaRepository cuotaRepository) {
        this.pagoRepository = pagoRepository;
        this.socioRepository = socioRepository;
        this.jugadorRepository = jugadorRepository;
        this.cuotaRepository = cuotaRepository;
    }

    public Pago guardarPago(Pago pago) {
        // Verificar que la cuota no sea nula
        if (pago.getCuota() == null || pago.getCuota().getId() == null) {
            throw new IllegalArgumentException("La cuota no puede ser nula y debe contener un ID válido.");
        }

        // Validar la existencia de la cuota en la base de datos
        Cuota cuota = cuotaRepository.findById(pago.getCuota().getId())
                .orElseThrow(() -> new IllegalArgumentException("La cuota especificada no existe."));
        pago.setCuota(cuota);

        // Validar la existencia del jugador si está presente
        if (pago.getJugador() != null && pago.getJugador().getId() != null) {
            Jugador jugador = jugadorRepository.findById(pago.getJugador().getId())
                    .orElseThrow(() -> new IllegalArgumentException("El jugador especificado no existe."));
            pago.setJugador(jugador);
        }

        // Validar la existencia del socio si está presente
        if (pago.getSocio() != null && pago.getSocio().getId() != null) {
            Socio socio = socioRepository.findById(pago.getSocio().getId())
                    .orElseThrow(() -> new IllegalArgumentException("El socio especificado no existe."));
            pago.setSocio(socio);
        }

        pago.setFechaRegistro(LocalDate.now() );
        return pagoRepository.save(pago);
    }

    // Listar todos los pagos como DTOs
    public List<PagoDTO> obtenerTodosLosPagos() {
        return pagoRepository.findAll().stream()
                .map(this::convertirAPagoDTO)
                .collect(Collectors.toList());
    }

    // Método para convertir Pago a PagoDTO
    public PagoDTO convertirAPagoDTO(Pago pago) {
        return new PagoDTO(
                pago.getId(),
                pago.getFechaPago(),
                pago.getMonto(),
                pago.getDescripcion(),
                pago.getCuota().getId(),
                pago.getJugador() != null ? pago.getJugador().getId() : null,
                pago.getSocio() != null ? pago.getSocio().getId() : null
        );
    }

    // Convertir Pago a PagoFullDataDTO
    public PagoFullDataDTO convertirAPagoFullDataDTO(Pago pago) {
        return new PagoFullDataDTO(
                pago.getId(),
                pago.getJugador() != null ? pago.getJugador().getNombre() : pago.getSocio().getNombre(),
                pago.getJugador() != null ? pago.getJugador().getApellido() : pago.getSocio().getApellido(),
                pago.getJugador() != null ? pago.getJugador().getDni() : pago.getSocio().getDni(),
                pago.getJugador() != null ? pago.getJugador().getCategoria().getNombre() : "",
                pago.getFechaPago().toString(),
                pago.getMonto(),
                pago.getDescripcion(),
                pago.getCuota().getTipo()
        );
    }

    // Obtener pagos por jugador
    public List<PagoFullDataDTO> obtenerPagosPorJugador(Long jugadorId) {
        return pagoRepository.findByJugadorId(jugadorId)
                .stream()
                .map(this::convertirAPagoFullDataDTO)
                .collect(Collectors.toList());
    }

    // Obtener pagos por socio
    public List<PagoFullDataDTO> obtenerPagosPorSocio(Long socioId) {
        return pagoRepository.findBySocioId(socioId)
                .stream()
                .map(this::convertirAPagoFullDataDTO)
                .collect(Collectors.toList());
    }

    // Obtener pagos entre fechas
    public List<PagoDTO> obtenerPagosPorFechas(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findByFechaPagoBetweenAndSocioIsNull(inicio, fin)
                .stream()
                .map(this::convertirAPagoDTO)
                .collect(Collectors.toList());
    }

    // Eliminar un pago
    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }

    // Obtener listado general de socios y jugadores
    public List<PersonaDTO> getListadoGeneralSociosYJugadores() {
        List<PersonaDTO> listadoGeneral = new ArrayList<>();
        jugadorRepository.findAll().forEach(jugador -> listadoGeneral.add(
                new PersonaDTO(jugador.getId(), jugador.getNombre(), jugador.getApellido(), jugador.getDni(), "jugador")
        ));
        socioRepository.findAll().forEach(socio -> listadoGeneral.add(
                new PersonaDTO(socio.getId(), socio.getNombre(), socio.getApellido(), socio.getDni(), "socio")
        ));
        return listadoGeneral;
    }

    // Obtener listado general de pagos
    public List<PagoFullDataDTO> getListadoGeneralPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirAPagoFullDataDTO)
                .collect(Collectors.toList());
    }

    public List<JugadorPagoDTO> obtenerJugadoresYPagos(Long categoriaId, Integer mes, Integer año) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        // Calcular rango de fechas si mes y año están presentes
        if (mes != null && año != null) {
            fechaInicio = LocalDate.of(año, mes, 1);
            fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
        }

        // Obtener jugadores de la categoría seleccionada
        List<Jugador> jugadores = jugadorRepository.findByCategoriaIdAndEstado(categoriaId, EstadoJugador.ACTIVO);

        // Obtener pagos realizados en el rango de fechas
        List<Pago> pagos = pagoRepository.findByFechaPagoBetweenAndSocioIsNull(fechaInicio, fechaFin);

        // Crear el listado de jugadores con su estado de pago
        return jugadores.stream().map(jugador -> {
            // Verificar si el jugador tiene un pago en el rango de fechas
            Pago pago = pagos.stream()
                    .filter(p -> p.getJugador().getId().equals(jugador.getId()))
                    .findFirst()
                    .orElse(null);

            // Crear el DTO con el estado del pago
            String estado = (pago != null) ? "Pagó en fecha " + pago.getFechaPago() : "Debe";
            return new JugadorPagoDTO(jugador.getNombre(), jugador.getApellido(), jugador.getDni(), estado);
        }).collect(Collectors.toList());
    }

    public List<PagoFullDataDTO> filtrarPagosPorFecha(Integer dia, Integer mes, Integer año) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        if (año != null) {
            if (mes != null) {
                if (dia != null) {
                    fechaInicio = LocalDate.of(año, mes, dia);
                    fechaFin = fechaInicio;
                } else {
                    fechaInicio = LocalDate.of(año, mes, 1);
                    fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
                }
            } else {
                fechaInicio = LocalDate.of(año, 1, 1);
                fechaFin = LocalDate.of(año, 12, 31);
            }
        }

        if (fechaInicio != null && fechaFin != null) {
            return pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin).stream()
                    .map(this::convertirAPagoFullDataDTO)
                    .collect(Collectors.toList());
        }


        return pagoRepository.findAll().stream().map(this::convertirAPagoFullDataDTO).collect(Collectors.toList());

    }

}
