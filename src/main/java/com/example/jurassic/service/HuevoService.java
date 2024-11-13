package com.example.jurassic.service;

import com.example.jurassic.entity.Huevo;
import com.example.jurassic.repository.HuevoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuevoService {

    private final HuevoRepository huevoRepository;

    public HuevoService(HuevoRepository huevoRepository) {
        this.huevoRepository = huevoRepository;
    }

    public List<Huevo> obtenerTodosHuevos() {
        return huevoRepository.findAll();
    }

    public List<Huevo> obtenerHuevosPorTipo(String tipo) {
        return huevoRepository.findByTipo(tipo);
    }

    public Huevo obtenerHuevoPorId(Long id) {
        return huevoRepository.findById(id).orElse(null);
    }
}

