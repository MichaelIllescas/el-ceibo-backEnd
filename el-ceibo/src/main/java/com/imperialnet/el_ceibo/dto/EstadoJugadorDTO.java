package com.imperialnet.el_ceibo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoJugadorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String estado;

    // Constructor
    public EstadoJugadorDTO(Long id, String nombre, String apellido, String dni, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.estado = estado;
    }
}
