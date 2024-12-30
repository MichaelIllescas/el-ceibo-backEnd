package com.imperialnet.el_ceibo.service;


import com.imperialnet.el_ceibo.entity.Categoria;
import com.imperialnet.el_ceibo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }
}
