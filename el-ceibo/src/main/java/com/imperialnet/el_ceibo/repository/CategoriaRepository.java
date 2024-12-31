package com.imperialnet.el_ceibo.repository;

import com.imperialnet.el_ceibo.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Object> findByNombre(String categoria);
}