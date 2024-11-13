package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Huevo;
import com.example.jurassic.repository.DinosaurioRepository;
import com.example.jurassic.repository.HuevoRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class IslaCriaService {

    private static final Logger logger = LoggerFactory.getLogger(IslaCriaService.class);
    private final HuevoRepository huevoRepository;
    private final DinosaurioRepository dinosaurioRepository;

    public IslaCriaService(HuevoRepository huevoRepository, DinosaurioRepository dinosaurioRepository) {
        this.huevoRepository = huevoRepository;
        this.dinosaurioRepository = dinosaurioRepository;
    }
*
    @PostConstruct
    public void iniciarGeneracionDeHuevos() {
        flujoGeneracionHuevos().subscribe();
    }

    // Flujo de generación de huevos cada 5 segundos
    public Flux<Huevo> flujoGeneracionHuevos() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(tick -> crearYGuardarHuevo())
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(this::iniciarProcesoDeEclosion); // Inicia el proceso de eclosión para cada huevo
    }

    private Mono<Huevo> crearYGuardarHuevo() {
        return Mono.fromCallable(() -> {
                    Huevo huevo = new Huevo();
                    huevo.setTipo(generarTipoAleatorio());
                    huevo.setFechaCreacion(LocalDateTime.now());
                    return huevoRepository.save(huevo);
                })
                .doOnNext(savedHuevo -> logger.info("Huevo generado y guardado: {}", savedHuevo))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private String generarTipoAleatorio() {
        String[] tipos = {"CARNIVORO", "HERBIVORO", "ACUATICO"};
        return tipos[new Random().nextInt(tipos.length)];
    }

    // Método que inicia el proceso de eclosión después de un intervalo de tiempo
    private void iniciarProcesoDeEclosion(Huevo huevo) {
        Mono.delay(Duration.ofSeconds(30)) // Simula el tiempo de eclosión
                .flatMap(tick -> eclosionarHuevo(huevo))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    // Convierte el huevo en un dinosaurio y lo guarda en la base de datos
    private Mono<Dinosaurio> eclosionarHuevo(Huevo huevo) {
        return Mono.fromCallable(() -> {
            Dinosaurio dinosaurio = new Dinosaurio(huevo.getTipo());
            dinosaurioRepository.save(dinosaurio);
            logger.info("Huevo eclosionado y dinosaurio generado: {}", dinosaurio);
            huevoRepository.delete(huevo); // Opcional: elimina el huevo después de la eclosión
            return dinosaurio;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
