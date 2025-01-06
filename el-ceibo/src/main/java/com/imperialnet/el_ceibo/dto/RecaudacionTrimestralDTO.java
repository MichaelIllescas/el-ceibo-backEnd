package com.imperialnet.el_ceibo.dto;


import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class RecaudacionTrimestralDTO {
    private String trimestre;
    private BigDecimal monto;

    // Constructor
    public RecaudacionTrimestralDTO(String trimestre, BigDecimal monto) {
        this.trimestre = trimestre;
        this.monto = monto;
    }
}