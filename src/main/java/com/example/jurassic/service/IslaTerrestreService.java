package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaTerrestre;
import com.example.jurassic.repository.DinosaurioRepository;
import com.example.jurassic.repository.IslaTerrestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

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

    /**
     * Incrementar la edad de una isla específica basada en el ID del dinosaurio.
     */
    public void incrementarEdadIsla(Long dinosaurioId) {
        IslaTerrestre isla = islaTerrestreRepository.findById(dinosaurioId).orElse(null);
        if (isla != null) {
            isla.setEdad(isla.getEdad() + 1);
            islaTerrestreRepository.save(isla);
        }
    }


    /**
     * Eliminar un dinosaurio de la isla correspondiente.
     */
    public void eliminarDinosaurio(Dinosaurio dinosaurio) {
        IslaTerrestre isla = islaTerrestreRepository.findById(dinosaurio.getId()).orElse(null);
        if (isla != null) {
            // Actualiza la lógica si hay una relación directa con la isla
            islaTerrestreRepository.delete(isla); // Aquí elimina la referencia en la isla
        }
    }
}


