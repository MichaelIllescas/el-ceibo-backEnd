package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.PagoDTO;
import com.imperialnet.el_ceibo.dto.PagoFullDataDTO;
import com.imperialnet.el_ceibo.entity.Jugador;
import com.imperialnet.el_ceibo.entity.Pago;
import com.imperialnet.el_ceibo.entity.Socio;
import com.imperialnet.el_ceibo.repository.JugadorRepository;
import com.imperialnet.el_ceibo.repository.PagoRepository;
import com.imperialnet.el_ceibo.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    public PagoService(PagoRepository pagoRepository, SocioRepository socioRepository, JugadorRepository jugadorRepository) {
        this.pagoRepository = pagoRepository;
        this.jugadorRepository=jugadorRepository;
        this.socioRepository=socioRepository;

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
    public List<PagoFullDataDTO> obtenerPagosPorJugador(Long jugadorId) {
        List<PagoFullDataDTO> pagosFullDTO=new ArrayList<>();
        List<Pago> pagosEnBD=pagoRepository.findByJugadorId(jugadorId);
        for (Pago pago: pagosEnBD){
            pagosFullDTO.add(convertirAPagoFullDataDTO(pago));
        }

        return pagosFullDTO;
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

    //convertir Pago a PagoFullDaraDTO
    public PagoFullDataDTO convertirAPagoFullDataDTO(Pago pago) {
        return new PagoFullDataDTO(
                pago.getId(),
                (pago.getSocio()!=null)?pago.getSocio().getNombre():pago.getJugador().getNombre(),
                (pago.getSocio()!=null)?pago.getSocio().getApellido():pago.getJugador().getApellido(),
                (pago.getSocio()!=null)?pago.getSocio().getDni():pago.getJugador().getDni(),
                (pago.getJugador()!=null)? pago.getJugador().getCategoria().getNombre(): " ",
                pago.getFechaPago().toString(),
                pago.getCuota().getMonto(),
                pago.getDescripcion(),
                pago.getCuota().getTipo()


        );
    }


    public Object getListadoGeneralSociosYJugadores() {
        List<Jugador> listadoJugadores= jugadorRepository.findAll();
        List<Socio> listdoSocios = socioRepository.findAll();
        List<Object> listadoGeneral= new ArrayList<Object>();
        for (Jugador jugador : listadoJugadores) {
            listadoGeneral.add(jugador);
        }
        for (Socio socio : listdoSocios) {
            listadoGeneral.add(socio);
        }

        return listadoGeneral;

    }

    public List<PagoFullDataDTO> getListagoGenerlPagos() {
        List<Pago> pagos = pagoRepository.findAll(); // Consulta todos los pagos
        return pagos.stream()
                .map(this::convertirAPagoFullDataDTO) // Convierte cada Pago a un PagoDTO
                .collect(Collectors.toList());
    }
}
