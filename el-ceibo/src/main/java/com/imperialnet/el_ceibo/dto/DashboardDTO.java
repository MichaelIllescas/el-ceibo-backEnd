package com.imperialnet.el_ceibo.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private long cantidadJugadores;
    private long cantidadSocios;
    private BigDecimal recaudacionTotal;
    private int categoriasActivas;
    private List<BigDecimal> graficoData;

    // Getters y setters
    public long getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(long cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public long getCantidadSocios() {
        return cantidadSocios;
    }

    public void setCantidadSocios(long cantidadSocios) {
        this.cantidadSocios = cantidadSocios;
    }

    public BigDecimal getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public void setRecaudacionTotal(BigDecimal recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }

    public int getCategoriasActivas() {
        return categoriasActivas;
    }

    public void setCategoriasActivas(int categoriasActivas) {
        this.categoriasActivas = categoriasActivas;
    }

    public List<BigDecimal> getGraficoData() {
        return graficoData;
    }

    public void setGraficoData(List<BigDecimal> graficoData) {
        this.graficoData = graficoData;
    }
}
