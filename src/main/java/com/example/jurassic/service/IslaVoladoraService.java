package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaVoladora;
import com.example.jurassic.repository.IslaVoladoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class IslaVoladoraService {

    @Autowired
    private IslaVoladoraRepository islaVoladoraRepository;

    public Flux<IslaVoladora> obtenerTodos() {
        return Flux.fromIterable(islaVoladoraRepository.findAll());
    }

    public void agregarDinosaurio(Dinosaurio dinosaurio) {
        IslaVoladora volador = new IslaVoladora(dinosaurio.getId(), dinosaurio.getDieta(), dinosaurio.getTipoHabitat(), dinosaurio.getFechaEclosion(), dinosaurio.getEdad());
        islaVoladoraRepository.save(volador);
    }
}