package com.imperialnet.el_ceibo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RecaudacionMensualDTO {
    private int mes;
    private BigDecimal monto;

    // Constructor
    public RecaudacionMensualDTO(int mes, BigDecimal monto) {
        this.mes = mes;
        this.monto = monto;
    }

}
