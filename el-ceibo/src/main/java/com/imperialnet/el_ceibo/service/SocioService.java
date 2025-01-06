package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.SocioDTO;
import com.imperialnet.el_ceibo.entity.Socio;
import com.imperialnet.el_ceibo.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    @Autowired
    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    // Crear un nuevo socio
    public SocioDTO crearSocio(SocioDTO socioDTO) {
        Socio socio = fromDTO(socioDTO);
        socio.setFechaRegistro(LocalDate.now()); // Establecer fecha de registro
        socio.setEstado(Socio.EstadoSocio.ACTIVO); // Por defecto, estado ACTIVO
        Socio socioGuardado = socioRepository.save(socio);
        return toDTO(socioGuardado);
    }

    // Obtener un socio por ID
    public Optional<SocioDTO> obtenerSocioPorId(Long id) {
        return socioRepository.findById(id).map(this::toDTO);
    }

    // Listar todos los socios
    public List<SocioDTO> listarTodosLosSocios() {
        return socioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Actualizar un socio existente
    public SocioDTO actualizarSocio(Long id, SocioDTO socioDTO) {
        Socio socioExistente = socioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con el ID: " + id));

        socioExistente.setNombre(socioDTO.getNombre());
        socioExistente.setApellido(socioDTO.getApellido());
        socioExistente.setDni(socioDTO.getDni());
        socioExistente.setDireccion(socioDTO.getDireccion());
        socioExistente.setTelefono(socioDTO.getTelefono());
        socioExistente.setEmail(socioDTO.getEmail());
        socioExistente.setEstado(Socio.EstadoSocio.valueOf(socioDTO.getEstado()));

        Socio socioActualizado = socioRepository.save(socioExistente);
        return toDTO(socioActualizado);
    }

    // Anular (desactivar) un socio
    public void anularSocio(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con el ID: " + id));
        socio.anular();
        socioRepository.save(socio);
    }

    // Habilitar un socio
    public void habilitarSocio(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con el ID: " + id));
        socio.habilitar();
        socioRepository.save(socio);
    }

    // Convertir Socio a SocioDTO
    private SocioDTO toDTO(Socio socio) {
        return new SocioDTO(
                socio.getId(),
                socio.getNombre(),
                socio.getApellido(),
                socio.getDni(),
                socio.getTelefono(),
                socio.getEmail(),
                socio.getFechaRegistro() != null ? socio.getFechaRegistro().toString() : null,
                socio.getDireccion(),
                socio.getEstado().name()
        );
    }

    // Convertir SocioDTO a Socio
    private Socio fromDTO(SocioDTO socioDTO) {
        return new Socio(
                socioDTO.getId(),
                socioDTO.getNombre(),
                socioDTO.getApellido(),
                socioDTO.getDni(),
                socioDTO.getDireccion(),
                socioDTO.getTelefono(),
                socioDTO.getEmail(),
                socioDTO.getFechaRegistro() != null ? LocalDate.parse(socioDTO.getFechaRegistro()) : null,
                null, // Fecha de baja se gestiona en el método correspondiente
                Socio.EstadoSocio.valueOf(socioDTO.getEstado()),
                new ArrayList<>() // Inicializamos la lista de pagos como una lista vacía
        );
    }

}
