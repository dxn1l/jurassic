package com.example.jurassic.service;

import com.example.jurassic.entity.*;
import com.example.jurassic.repository.HuevoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuevoService {

    @Autowired
    private final HuevoRepository huevoRepository;

    public HuevoService(HuevoRepository huevoRepository) {
        this.huevoRepository = huevoRepository;
    }

    public List<Huevo> obtenerTodosHuevos() {
        return huevoRepository.findAll();
    }

    // Obtener huevos por tipo de hábitat (terrestre, acuático, volador)
    public List<Huevo> obtenerHuevosPorTipoHabitat(String tipoHabitat) {
        return huevoRepository.findByTipoHabitat(tipoHabitat);
    }

    // Obtener huevos por dieta (herbívoro, carnívoro)
    public List<Huevo> obtenerHuevosPorDieta(String dieta) {
        return huevoRepository.findByDieta(dieta);
    }

    public Huevo obtenerHuevoPorId(Long id) {
        return huevoRepository.findById(id).orElse(null);
    }

    public void guardarHuevo(Huevo huevo) {
        huevoRepository.save(huevo);
        System.out.println("Huevo creado por reproduccion y guardado con dieta " + huevo.getDieta() + " y hábitat " + huevo.getTipoHabitat());
    }

}
