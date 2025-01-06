package com.imperialnet.el_ceibo.repository;

import com.imperialnet.el_ceibo.entity.EstadoJugador;
import com.imperialnet.el_ceibo.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador,Long> {

    public List<Jugador> findByEstado(EstadoJugador estado);

    public Jugador findByDni(String dni);

    List<Jugador> findByCategoriaIdAndEstado(Long categoriaId, EstadoJugador estado);

    List<Jugador> findByCategoriaId(Long categoriaId);
}
