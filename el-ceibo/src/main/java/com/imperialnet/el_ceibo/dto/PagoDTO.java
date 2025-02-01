package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PagoDTO {

    private Long id;
    private LocalDate fechaPago;
    private BigDecimal monto;
    private String descripcion;
    private Long cuotaId;
    private Long jugadorId;
    private Long socioId;
    private String usuario_registro;


    // Constructor
    public PagoDTO(Long id, LocalDate fechaPago, BigDecimal monto, String descripcion, Long cuotaId, Long jugadorId, Long socioId, String usuario_registro) {
        this.id = id;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuotaId = cuotaId;
        this.jugadorId = jugadorId;
        this.socioId = socioId;
        this.usuario_registro = usuario_registro;
    }
}
