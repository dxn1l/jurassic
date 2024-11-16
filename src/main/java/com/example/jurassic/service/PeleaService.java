package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Dinosaurio;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Mono<Void> iniciarEventoPelea() {
        return Flux.fromIterable(dinosaurioService.obtenerTodasLasIslas())
                .collectList()
                .flatMap(islas -> {
                    String islaSeleccionada = islas.get(ThreadLocalRandom.current().nextInt(islas.size()));

                    List<Dinosaurio> dinosaurios = dinosaurioService.obtenerDinosauriosPorTipoHabitat(islaSeleccionada);
                    if (dinosaurios.size() < 2) {
                        enviarMensajeDinosauriosInsuficientes(islaSeleccionada);
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

                    enviarMensajePelea(dino1, dino2, ganador);


                    if (ThreadLocalRandom.current().nextBoolean()) {
                        return Mono.fromRunnable(() -> {
                            cementerioService.enviarDinosaurioAlCementerio(perdedor);
                            eliminarDinosaurioDeIsla(perdedor); // Eliminar de la isla correspondiente
                            dinosaurioService.eliminarDinosaurio(perdedor); // Eliminar de la tabla general
                            enviarMensajeMuertePorPelea(perdedor);
                        });
                    } else {
                        return hospitalService.enviarDinosaurioAlHospital(perdedor)
                                .doOnSuccess(h -> {
                                    eliminarDinosaurioDeIsla(perdedor); // Eliminar de la isla correspondiente
                                    hospitalService.procesarDinosaurioEnHospital(perdedor).subscribe();
                                    enviarMensajeHeridoPorPelea(perdedor);
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


    private void enviarMensajePelea(Dinosaurio dino1, Dinosaurio dino2, Dinosaurio ganador) {
        String mensaje = String.format("Pelea entre Dinosaurio %d y %d en la isla %s. Ganador: %d",
                dino1.getId(), dino2.getId(), dino1.getTipoHabitat(), ganador.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.PELEA_QUEUE, mensaje);
    }

    private void enviarMensajeMuertePorPelea(Dinosaurio dinosaurio) {
        String mensaje = String.format("Dinosaurio %d murió tras una pelea en la isla %s.",
                dinosaurio.getId(), dinosaurio.getTipoHabitat());
        rabbitTemplate.convertAndSend(RabbitMQConfig.MUERTE_PELEA_QUEUE, mensaje);
    }

    private void enviarMensajeHeridoPorPelea(Dinosaurio dinosaurio) {
        String mensaje = String.format("Dinosaurio %d resultó herido tras una pelea y fue enviado al hospital.",
                dinosaurio.getId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.HERIDO_PELEA_QUEUE, mensaje);
    }

    private void enviarMensajeDinosauriosInsuficientes( String tipoHabitat) {
        String mensaje = "No se pudo iniciar el proceso de pelea en la isla " + tipoHabitat +
                " debido a la falta de dinosaurios.";
        rabbitTemplate.convertAndSend(RabbitMQConfig.SIN_SUFICIENTES_DINOS_QUEUE, mensaje);
    }

}
