package com.imperialnet.el_ceibo.controller;

import com.imperialnet.el_ceibo.entity.Cuota;
import com.imperialnet.el_ceibo.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuotas")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @PostMapping
    public ResponseEntity<Cuota> createCuota(@RequestBody Cuota cuota) {
        return cuotaService.save(cuota)
                .map(savedCuota -> ResponseEntity.status(HttpStatus.CREATED).body(savedCuota))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuota> getCuota(@PathVariable Long id) {
        return cuotaService.findById(id)
                .map(cuota -> ResponseEntity.ok(cuota))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuota> updateCuota(@PathVariable Long id, @RequestBody Cuota cuota) {
        return cuotaService.update(id, cuota)
                .map(updatedCuota -> ResponseEntity.ok(updatedCuota))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuota(@PathVariable Long id) {
        try {
            cuotaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Cuota>> getAllCuotas() {
        List<Cuota> cuotas = cuotaService.findAll();
        return ResponseEntity.ok(cuotas);
    }
}
