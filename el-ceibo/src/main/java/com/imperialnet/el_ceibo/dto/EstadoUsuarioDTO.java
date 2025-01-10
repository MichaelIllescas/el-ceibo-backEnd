package com.imperialnet.el_ceibo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoUsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String estado;
}
