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


        if (dinosaurios.size() < 2) {
            dinosaurioService.enviarMensajeSinSuficientesDinos("VOLADOR");
            return;
        }

            Dinosaurio dino1 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
        Dinosaurio dino2;
        do {
            dino2 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
        } while (dino1.getId().equals(dino2.getId()));


        if (dino1.getDieta().equals(dino2.getDieta())) {

                dinosaurioService.enviarMensajeReproduccionExitosa(dino1, dino2);

                Huevo huevo = new Huevo(dino1.getDieta(), dino1.getTipoHabitat());
                huevoService.guardarHuevo(huevo);
            }else {

                dinosaurioService.enviarMensajeReproduccionFallida(dino1, dino2);
            }
    }
    public List<IslaAcuatica> obtenerPorDieta(String dieta) {
        return islaAcuaticaRepository.findByDieta(dieta);
    }
}

