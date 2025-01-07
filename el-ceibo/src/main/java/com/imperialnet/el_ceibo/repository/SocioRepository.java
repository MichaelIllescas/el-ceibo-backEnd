package com.imperialnet.el_ceibo.repository;

import com.imperialnet.el_ceibo.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

    public List<Socio> findAllByEstado(Socio.EstadoSocio estado);
}
