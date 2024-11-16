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

    @Autowired
    private IslaTerrestreService islaTerrestreService;

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

    @Autowired
    private IslaVoladoraService islaVoladoraService;

    public Mono<Void> iniciarEventoPelea() {
        return Flux.fromIterable(dinosaurioService.obtenerTodasLasIslas())
                .collectList()
                .flatMap(islas -> {
                    String islaSeleccionada = islas.get(ThreadLocalRandom.current().nextInt(islas.size()));

                    List<Dinosaurio> dinosaurios = dinosaurioService.obtenerDinosauriosPorTipoHabitat(islaSeleccionada);
                    if (dinosaurios.size() < 2) {
                        System.out.println("No hay suficientes dinosaurios en la isla " + islaSeleccionada + " para una pelea.");
                        return Mono.empty();
                    }

                    Dinosaurio dino1 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
                    Dinosaurio dino2;
                    do {
                        dino2 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
                    } while (dino1.getId().equals(dino2.getId()));

                    Dinosaurio ganador;
                    Dinosaurio perdedor;
                    if (dino1.getDieta().equals("CARNIVORO") && dino2.getDieta().equals("HERBIVORO")) {
                        ganador = dino1;
                        perdedor = dino2;
                    } else if (dino2.getDieta().equals("CARNIVORO") && dino1.getDieta().equals("HERBIVORO")) {
                        ganador = dino2;
                        perdedor = dino1;
                    } else {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            ganador = dino1;
                            perdedor = dino2;
                        } else {
                            ganador = dino2;
                            perdedor = dino1;
                        }
                    }

                    System.out.println("Ganador: " + ganador.getId() + ", Perdedor: " + perdedor.getId());

                    if (ThreadLocalRandom.current().nextBoolean()) {
                        return Mono.fromRunnable(() -> {
                            cementerioService.enviarDinosaurioAlCementerio(perdedor);
                            eliminarDinosaurioDeIsla(perdedor); // Eliminar de la isla correspondiente
                            dinosaurioService.eliminarDinosaurio(perdedor); // Eliminar de la tabla general
                            System.out.println("El perdedor, Dinosaurio con ID " + perdedor.getId() + ", ha MUERTO.");
                        });
                    } else {
                        return hospitalService.enviarDinosaurioAlHospital(perdedor)
                                .doOnSuccess(h -> {
                                    eliminarDinosaurioDeIsla(perdedor); // Eliminar de la isla correspondiente
                                    hospitalService.procesarDinosaurioEnHospital(perdedor).subscribe();
                                    System.out.println("El perdedor, Dinosaurio con ID " + perdedor.getId() + ", ha quedado HERIDO y enviado al hospital.");
                                })
                                .then();
                    }
                }).then();
    }

    private void eliminarDinosaurioDeIsla(Dinosaurio dinosaurio) {
        switch (dinosaurio.getTipoHabitat()) {
            case "TERRESTRE":
                islaTerrestreService.eliminarDinosaurio(dinosaurio);
                break;
            case "ACUATICO":
                islaAcuaticaService.eliminarDinosaurio(dinosaurio);
                break;
            case "VOLADOR":
                islaVoladoraService.eliminarDinosaurio(dinosaurio);
                break;
            default:
                throw new IllegalArgumentException("Tipo de hábitat desconocido: " + dinosaurio.getTipoHabitat());
        }
    }
}
