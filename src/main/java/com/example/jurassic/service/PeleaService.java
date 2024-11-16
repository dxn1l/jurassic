package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PeleaService {

    @Autowired
    private DinosaurioService dinosaurioService;

    @Autowired
    private CementerioService cementerioService;

    @Autowired
    private HospitalService hospitalService;

    public Mono<Void> iniciarEventoPelea() {
        // Adaptar la lista de islas a un flujo
        return Flux.fromIterable(dinosaurioService.obtenerTodasLasIslas())
                .collectList()
                .flatMap(islas -> {
                    // Seleccionar isla aleatoria
                    String islaSeleccionada = islas.get(ThreadLocalRandom.current().nextInt(islas.size()));

                    // Adaptar dinosaurios de la isla a un flujo
                    List<Dinosaurio> dinosaurios = dinosaurioService.obtenerDinosauriosPorTipoHabitat(islaSeleccionada);
                    if (dinosaurios.size() < 2) {
                        System.out.println("No hay suficientes dinosaurios en la isla " + islaSeleccionada + " para una pelea.");
                        return Mono.empty();
                    }

                    // Seleccionar dos dinosaurios aleatorios
                    Dinosaurio dino1 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
                    Dinosaurio dino2;
                    do {
                        dino2 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
                    } while (dino1.getId().equals(dino2.getId()));

                    // Determinar ganador y perdedor
                    Dinosaurio ganador;
                    Dinosaurio perdedor;
                    if (dino1.getDieta().equals("CARNIVORO") && dino2.getDieta().equals("HERBIVORO")) {
                        ganador = dino1;
                        perdedor = dino2;
                    } else if (dino2.getDieta().equals("CARNIVORO") && dino1.getDieta().equals("HERBIVORO")) {
                        ganador = dino2;
                        perdedor = dino1;
                    } else { // Carnívoro vs Carnívoro
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            ganador = dino1;
                            perdedor = dino2;
                        } else {
                            ganador = dino2;
                            perdedor = dino1;
                        }
                    }

                    System.out.println("Ganador: " + ganador.getId() + ", Perdedor: " + perdedor.getId());

                    // Decidir si el perdedor muere o queda herido
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        // Perdedor muere
                        return Mono.fromRunnable(() -> {
                            cementerioService.enviarDinosaurioAlCementerio(perdedor);
                            dinosaurioService.eliminarDinosaurio(perdedor);
                            System.out.println("El perdedor, Dinosaurio con ID " + perdedor.getId() + ", ha MUERTO.");
                        });
                    } else {
                        // Perdedor queda herido
                        return hospitalService.enviarDinosaurioAlHospital(perdedor)
                                .doOnSuccess(h -> System.out.println("El perdedor, Dinosaurio con ID " + perdedor.getId() + ", ha quedado HERIDO y enviado al hospital."))
                                .then(Mono.fromRunnable(() -> dinosaurioService.eliminarDinosaurio(perdedor)));
                    }
                }).then();
    }
}
