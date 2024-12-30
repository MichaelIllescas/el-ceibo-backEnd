package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.entity.Cuota;
import com.imperialnet.el_ceibo.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;

    public Optional<Cuota> save(Cuota cuota) {
        return Optional.of(cuotaRepository.save(cuota));
    }

    public Optional<Cuota> findById(Long id) {
        return cuotaRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (cuotaRepository.existsById(id)) {
            cuotaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("La cuota con el ID " + id + " no existe.");
        }
    }

    public List<Cuota> findAll() {
        return cuotaRepository.findAll();
    }

    public Optional<Cuota> update(Long id, Cuota cuota) {
        return cuotaRepository.findById(id).map(existingCuota -> {
            existingCuota.setTipo(cuota.getTipo());
            existingCuota.setMonto(cuota.getMonto());
            existingCuota.setFechaUltimaActualizacion(cuota.getFechaUltimaActualizacion());
            return cuotaRepository.save(existingCuota);
        });
    }
}
