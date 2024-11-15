package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaVoladora;
import com.example.jurassic.repository.IslaVoladoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

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

    /**
     * Incrementar la edad de una isla voladora específica basada en el ID del dinosaurio.
     */
    public void incrementarEdadIsla(Long dinosaurioId) {
        IslaVoladora isla = islaVoladoraRepository.findById(dinosaurioId).orElse(null);
        if (isla != null) {
            isla.setEdad(isla.getEdad() + 1);
            islaVoladoraRepository.save(isla);
        }
    }


    /**
     * Eliminar un dinosaurio de la isla voladora correspondiente.
     */
    public void eliminarDinosaurio(Dinosaurio dinosaurio) {
        IslaVoladora isla = islaVoladoraRepository.findById(dinosaurio.getId()).orElse(null);
        if (isla != null) {
            islaVoladoraRepository.delete(isla); // Aquí elimina la referencia en la isla
        }
    }

}