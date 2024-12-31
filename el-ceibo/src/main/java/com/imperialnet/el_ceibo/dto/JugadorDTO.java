package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JugadorDTO {
        private Long id;
        private String nombre;
        private String apellido;
        private String dni;
        private String telefono;
        private String email;
        private String fechaRegistro;
        private String categoria;

        // Constructor
        public JugadorDTO(Long id, String nombre, String apellido, String dni, String telefono, String email, String fechaRegistro, String categoriaNombre) {
            this.id = id;
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
            this.telefono = telefono;
            this.email = email;
            this.fechaRegistro = fechaRegistro;
            this.categoria = categoriaNombre;
        }
}
