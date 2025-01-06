package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JugadorPagoDTO {
    private String nombre;
    private String apellido;
    private String dni;
    private String estado;

    public JugadorPagoDTO(String nombre, String apellido, String dni, String estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni=dni;
        this.estado = estado;
    }

    // Getters y Setters
}

