package com.imperialnet.el_ceibo.repository;


import com.imperialnet.el_ceibo.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Buscar pagos por jugador
    List<Pago> findByJugadorId(Long jugadorId);

    // Buscar pagos por socio
   // List<Pago> findBySocioId(Long socioId);

    // Buscar pagos por cuota
    List<Pago> findByCuotaId(Long cuotaId);

    // Buscar pagos entre fechas específicas
    List<Pago> findByFechaPagoBetween(LocalDate inicio, LocalDate fin);
}