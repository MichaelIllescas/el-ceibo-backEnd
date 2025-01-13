package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.dto.DashboardDTO;
import com.imperialnet.el_ceibo.dto.RecaudacionMensualDTO;
import com.imperialnet.el_ceibo.repository.CategoriaRepository;
import com.imperialnet.el_ceibo.repository.JugadorRepository;
import com.imperialnet.el_ceibo.service.PagoService;
import com.imperialnet.el_ceibo.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final JugadorRepository jugadorRepository;
    private final SocioRepository socioRepository;
    private final PagoService pagoService;
    private final CategoriaRepository categoriaRepository;

    public DashboardService(JugadorRepository jugadorRepository,
                            SocioRepository socioRepository,
                            PagoService pagoService,
                            CategoriaRepository categoriaRepository) {
        this.jugadorRepository = jugadorRepository;
        this.socioRepository = socioRepository;
      this.pagoService = pagoService;
        this.categoriaRepository = categoriaRepository;
    }

    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        // Cantidad de jugadores
        dto.setCantidadJugadores(jugadorRepository.count());

        // Cantidad de socios
        dto.setCantidadSocios(socioRepository.count());

        // Recaudación total
        List<BigDecimal> recaudaciones= pagoService.obtenerRecaudacionesMensuales(LocalDate.now().getYear());
        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal recaudacion : recaudaciones) {
            total = total.add(recaudacion); // Usar el método add
        }

        dto.setRecaudacionTotal(total);

        // Categorías activas
        dto.setCategoriasActivas((int) categoriaRepository.count());

        // Datos para el gráfico (puedes personalizar esto)
        List<BigDecimal> graficoData = pagoService.obtenerRecaudacionesMensuales(LocalDate.now().getYear());
        dto.setGraficoData(graficoData);

        return dto;
    }
}
