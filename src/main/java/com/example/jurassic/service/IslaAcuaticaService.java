package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaAcuatica;
import com.example.jurassic.repository.IslaAcuaticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class IslaAcuaticaService {

    @Autowired
    private IslaAcuaticaRepository islaAcuaticaRepository;

    public Flux<IslaAcuatica> obtenerTodos() {
        return Flux.fromIterable(islaAcuaticaRepository.findAll());
    }

    public void agregarDinosaurio(Dinosaurio dinosaurio) {
        IslaAcuatica acuatico = new IslaAcuatica(dinosaurio.getId(), dinosaurio.getDieta(), dinosaurio.getTipoHabitat(), dinosaurio.getFechaEclosion(), dinosaurio.getEdad());
        islaAcuaticaRepository.save(acuatico);
    }
}
