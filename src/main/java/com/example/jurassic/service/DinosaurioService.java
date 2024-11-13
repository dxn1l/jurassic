package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.repository.DinosaurioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DinosaurioService {

    private final DinosaurioRepository dinosaurioRepository;

    public DinosaurioService(DinosaurioRepository dinosaurioRepository) {
        this.dinosaurioRepository = dinosaurioRepository;
    }

    public List<Dinosaurio> obtenerTodosDinosaurios() {
        return dinosaurioRepository.findAll();
    }

    public List<Dinosaurio> obtenerDinosauriosPorTipo(String tipo) {
        return dinosaurioRepository.findByTipo(tipo);
    }

    public Dinosaurio obtenerDinosaurioPorId(Long id) {
        return dinosaurioRepository.findById(id).orElse(null);
    }
}

