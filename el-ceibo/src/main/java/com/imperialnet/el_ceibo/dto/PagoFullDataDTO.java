package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoFullDataDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String categoria;
    private String fechaPago;
    private Double monto;
    private String descripcion;
    private String tipoCuota;



    // Constructor
    public PagoFullDataDTO(Long id,  String nombre, String apellido, String dni, String categoria, String fechaPago, Double monto, String descripcion, String tipoCuota) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.categoria = categoria;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.descripcion = descripcion;
        this.tipoCuota = tipoCuota;

    }

}
