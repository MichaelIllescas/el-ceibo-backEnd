package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagoFullDataDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String categoria; // Puede ser null si el pago no está relacionado con un jugador
    private String fechaPago;
    private BigDecimal monto;
    private String descripcion;
    private String tipo; // "jugador" o "socio"
    private String usuario_registro;

    // Constructor completo
    public PagoFullDataDTO(Long id, String nombre, String apellido, String dni, String categoria, String fechaPago, BigDecimal monto, String descripcion, String tipo, String usuario_registro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.categoria = categoria;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.usuario_registro= usuario_registro;
    }

    // Constructor vacío
    public PagoFullDataDTO() {}
}
