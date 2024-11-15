package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Huevo;
import com.example.jurassic.entity.IslaAcuatica;
import com.example.jurassic.repository.IslaAcuaticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class IslaAcuaticaService {

    @Autowired
    private IslaAcuaticaRepository islaAcuaticaRepository;

    @Autowired
    private HuevoService huevoService;

    @Autowired
    private DinosaurioService dinosaurioService;

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
    public void reproducirDinosaurios() {
        List<Dinosaurio> dinosaurios = dinosaurioService.obtenerDinosauriosPorTipoHabitat("VOLADOR");

        if (dinosaurios.size() >= 2) {
            Dinosaurio dino1 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
            Dinosaurio dino2 = dinosaurios.stream()
                    .filter(d -> !d.getId().equals(dino1.getId()) && d.getDieta().equals(dino1.getDieta()))
                    .findFirst()
                    .orElse(null);

            if (dino2 != null) {
                System.out.println("Dinosaurio con ID " + dino1.getId() + " se ha reproducido con dinosaurio con ID " + dino2.getId());
                Huevo huevo = new Huevo(dino1.getDieta(), dino1.getTipoHabitat());
                huevoService.guardarHuevo(huevo);
            }
        }
    }
}

