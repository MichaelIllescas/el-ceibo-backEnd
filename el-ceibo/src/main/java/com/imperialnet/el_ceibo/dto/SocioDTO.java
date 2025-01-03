package com.imperialnet.el_ceibo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String fechaRegistro; // Formato ISO-8601 (yyyy-MM-dd)
    private String direccion;
    private String estado; // ACTIVO o ANULADO
}
