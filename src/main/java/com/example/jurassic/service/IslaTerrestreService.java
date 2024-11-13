package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaTerrestre;
import com.example.jurassic.repository.DinosaurioRepository;
import com.example.jurassic.repository.IslaTerrestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class IslaTerrestreService {

    @Autowired
    private IslaTerrestreRepository islaTerrestreRepository;

    public Flux<IslaTerrestre> obtenerTodos() {
        return Flux.fromIterable(islaTerrestreRepository.findAll());
    }

    public void agregarDinosaurio(Dinosaurio dinosaurio) {
        IslaTerrestre terrestre = new IslaTerrestre(dinosaurio.getId(), dinosaurio.getDieta(), dinosaurio.getTipoHabitat(), dinosaurio.getFechaEclosion(), dinosaurio.getEdad());
        islaTerrestreRepository.save(terrestre);
    }
}

