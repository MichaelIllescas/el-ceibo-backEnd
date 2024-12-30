package com.imperialnet.el_ceibo.repository;

import com.imperialnet.el_ceibo.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
}
