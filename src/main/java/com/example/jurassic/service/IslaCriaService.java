package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Huevo;
import com.example.jurassic.repository.DinosaurioRepository;
import com.example.jurassic.repository.HuevoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class IslaCriaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DinosaurioService dinosaurioService;

    private static final Logger logger = LoggerFactory.getLogger(IslaCriaService.class);
    private final HuevoRepository huevoRepository;
    private final DinosaurioRepository dinosaurioRepository;

    public IslaCriaService(HuevoRepository huevoRepository, DinosaurioRepository dinosaurioRepository) {
        this.huevoRepository = huevoRepository;
        this.dinosaurioRepository = dinosaurioRepository;
    }

    @PostConstruct
    public void iniciarGeneracionDeHuevos() {
        flujoGeneracionHuevos().subscribe();
    }

    // Flujo de generación de huevos cada 5 segundos
    public Flux<Huevo> flujoGeneracionHuevos() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(tick -> crearYGuardarHuevo())
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<Huevo> crearYGuardarHuevo() {
        return Mono.fromCallable(() -> {
                    // Generar dieta y tipo de hábitat aleatorios
                    String dieta = generarDietaAleatoria(); // HERBIVORO o CARNIVORO
                    String tipoHabitat = generarTipoHabitatAleatorio(); // TERRESTRE, ACUATICO o VOLADOR

                    // Crear el huevo con los valores generados
                    Huevo huevo = new Huevo(dieta, tipoHabitat);
                    huevo.setFechaCreacion(LocalDateTime.now());
                    Huevo savedHuevo = huevoRepository.save(huevo);

                    // Crear mensaje para el evento de creación de huevo
                    String mensaje = "Huevo creado con ID: " + savedHuevo.getId();

                    // Enviar mensaje a RabbitMQ
                    rabbitTemplate.convertAndSend(RabbitMQConfig.HUEVO_CREACION_QUEUE, mensaje);

                    return savedHuevo;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private String generarDietaAleatoria() {
        return new Random().nextBoolean() ? "HERBIVORO" : "CARNIVORO";
    }

    private String generarTipoHabitatAleatorio() {
        String[] tipos = {"TERRESTRE", "ACUATICO", "VOLADOR"};
        return tipos[new Random().nextInt(tipos.length)];
    }

    // Método que inicia el proceso de eclosión después de un intervalo de tiempo
    public void iniciarProcesoDeEclosion(Huevo huevo) {
        Mono.delay(Duration.ofSeconds(30)) // Simula el tiempo de eclosión
                .flatMap(tick -> eclosionarHuevo(huevo))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    public Huevo obtenerHuevoPorId(Long id) {
        return huevoRepository.findById(id).orElse(null);
    }

    // Convierte el huevo en un dinosaurio y lo guarda en la base de datos
    private Mono<Dinosaurio> eclosionarHuevo(Huevo huevo) {
        return Mono.fromCallable(() -> {
            // Crea un dinosaurio basado en los atributos del huevo
            Dinosaurio dinosaurio = new Dinosaurio(huevo.getDieta(), huevo.getTipoHabitat());
            dinosaurioRepository.save(dinosaurio);
            huevoRepository.delete(huevo);// Elimina el huevo después de la eclosión
            String mensaje = "HuevoID=" + huevo.getId() + ",DinosaurioID=" + dinosaurio.getId();
            rabbitTemplate.convertAndSend(RabbitMQConfig.HUEVO_ECLOSION_QUEUE, mensaje);
            return dinosaurio;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
