package com.imperialnet.el_ceibo.controller;

import com.imperialnet.el_ceibo.dto.RecaudacionTrimestralDTO;
import com.imperialnet.el_ceibo.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/recaudaciones")
@CrossOrigin(origins = "http://localhost:3000")
public class RecaudacionesController {

    @Autowired
    private PagoService pagoService;




    @GetMapping("/mensuales")
    public List<BigDecimal> obtenerRecaudacionesMensuales(@RequestParam int year) {
        return pagoService.obtenerRecaudacionesMensuales(year);
    }


    @GetMapping("/trimestrales")
    public List<RecaudacionTrimestralDTO> obtenerRecaudacionesTrimestrales(@RequestParam int year) {
        return pagoService.obtenerRecaudacionesTrimestrales(year);
    }
}
