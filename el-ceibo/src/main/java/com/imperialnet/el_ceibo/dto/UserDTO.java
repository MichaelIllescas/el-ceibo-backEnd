package com.imperialnet.el_ceibo.dto;


import com.imperialnet.el_ceibo.entity.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;
    private String email;
    private String estado;
    private Role rol;
}
