package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaAcuatica;
import com.example.jurassic.repository.IslaAcuaticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;



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

    /**
     * Incrementar la edad de una isla acuática específica basada en el ID del dinosaurio.
     */
    public void incrementarEdadIsla(Long dinosaurioId) {
        IslaAcuatica isla = islaAcuaticaRepository.findById(dinosaurioId).orElse(null);
        if (isla != null) {
            isla.setEdad(isla.getEdad() + 1);
            islaAcuaticaRepository.save(isla);
        }
    }


    /**
     * Eliminar un dinosaurio de la isla acuática correspondiente.
     */

    public void eliminarDinosaurio(Dinosaurio dinosaurio) {
        IslaAcuatica isla = islaAcuaticaRepository.findById(dinosaurio.getId()).orElse(null);
        if (isla != null) {
            islaAcuaticaRepository.delete(isla); // Aquí elimina la referencia en la isla
        }
    }
}

