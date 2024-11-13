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

    // Obtener dinosaurios por tipo de hábitat (terrestre, acuático, volador)
    public List<Dinosaurio> obtenerDinosauriosPorTipoHabitat(String tipoHabitat) {
        return dinosaurioRepository.findByTipoHabitat(tipoHabitat);
    }

    // Obtener dinosaurios por dieta (herbívoro, carnívoro)
    public List<Dinosaurio> obtenerDinosauriosPorDieta(String dieta) {
        return dinosaurioRepository.findByDieta(dieta);
    }

    public Dinosaurio obtenerDinosaurioPorId(Long id) {
        return dinosaurioRepository.findById(id).orElse(null);
    }
}
