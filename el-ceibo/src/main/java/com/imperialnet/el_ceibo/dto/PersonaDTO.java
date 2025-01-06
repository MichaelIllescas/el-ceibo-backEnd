package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String tipo; // "jugador" o "socio"

    // Constructor completo
    public PersonaDTO(Long id, String nombre, String apellido, String dni, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.tipo = tipo;
    }

    // Constructor vacío
    public PersonaDTO() {}
}
