package com.example.jurassic.service;

import com.example.jurassic.entity.Huevo;
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

    public IslaCriaService(HuevoRepository huevoRepository) {
        this.huevoRepository = huevoRepository;
    }

    // Método que inicia el flujo de generación de huevos
    public Flux<Huevo> flujoGeneracionHuevos() {
        return Flux.interval(Duration.ofSeconds(5))  // Genera un huevo cada 5 segundos
                .flatMap(tick -> crearYGuardarHuevo())
                .subscribeOn(Schedulers.boundedElastic()); // Ejecuta en un hilo compatible con tareas bloqueantes
    }

    @PostConstruct
    public void iniciarGeneracionDeHuevos() {
        flujoGeneracionHuevos().subscribe();
    }

    private Mono<Huevo> crearYGuardarHuevo() {
        return Mono.fromCallable(() -> {
                    Huevo huevo = new Huevo();
                    huevo.setTipo(generarTipoAleatorio());
                    huevo.setFechaCreacion(LocalDateTime.now());
                    return huevoRepository.save(huevo); // Guardado síncrono en la base de datos
                })
                .doOnNext(savedHuevo -> logger.info("Huevo generado y guardado: {}", savedHuevo))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private String generarTipoAleatorio() {
        String[] tipos = {"CARNIVORO", "HERBIVORO", "ACUATICO"};
        return tipos[new Random().nextInt(tipos.length)];
    }
}
