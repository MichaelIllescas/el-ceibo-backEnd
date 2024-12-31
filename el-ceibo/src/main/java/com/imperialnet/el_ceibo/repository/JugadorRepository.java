package com.imperialnet.el_ceibo.repository;

import com.imperialnet.el_ceibo.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador,Long> {
}
