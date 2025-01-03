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
    private Long cuotaId;  // Solo el ID de la cuota
    private Long jugadorId; // Solo el ID del jugador

    // Constructor
    public PagoDTO(Long id, LocalDate fechaPago, BigDecimal monto, String descripcion, Long cuotaId, Long jugadorId) {
        this.id = id;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuotaId = cuotaId;
        this.jugadorId = jugadorId;
    }


}

