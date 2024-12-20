package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.repository.DinosaurioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DinosaurioService {

    private final DinosaurioRepository dinosaurioRepository;

    @Autowired
    @Lazy

    private IslaVoladoraService islaVoladoraService;

    @Autowired
    @Lazy

    private IslaTerrestreService islaTerrestreService;

    @Autowired
    @Lazy

    private IslaAcuaticaService islaAcuaticaService;

    @Autowired
    private CementerioService cementerioService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(IslaCriaService.class);

    public DinosaurioService(DinosaurioRepository dinosaurioRepository) {
        this.dinosaurioRepository = dinosaurioRepository;
    }

    public List<Dinosaurio> obtenerTodosDinosaurios() {
        return dinosaurioRepository.findAll();
    }

    // Obtener dinosaurios por tipo de hábitat (terrestre, acuático, volador)
    public List<Dinosaurio> obtenerDinosauriosPorTipoHabitat(String tipoHabitat) {
        return dinosaurioRepository.findByTipoHabitat(tipoHabitat);
    }

    @Transactional
    public void procesarMuerteDinosaurio(Dinosaurio dinosaurio) {
        // Enviar el dinosaurio al cementerio
        cementerioService.enviarDinosaurioAlCementerio(dinosaurio);
        // Eliminar el dinosaurio de la base de datos
        dinosaurioRepository.delete(dinosaurio);
    }

    // Obtener dinosaurios por dieta (herbívoro, carnívoro)
    public List<Dinosaurio> obtenerDinosauriosPorDieta(String dieta) {
        return dinosaurioRepository.findByDieta(dieta);
    }

    public Dinosaurio obtenerDinosaurioPorId(Long id) {
        return dinosaurioRepository.findById(id).orElse(null);
    }

    @Transactional
    public void distribuirDinosaurio(Dinosaurio dinosaurio) {
        String mensaje;
        switch (dinosaurio.getTipoHabitat()) {
            case "VOLADOR":
                islaVoladoraService.agregarDinosaurio(dinosaurio);
                break;
            case "TERRESTRE":
                islaTerrestreService.agregarDinosaurio(dinosaurio);
                break;
            case "ACUATICO":
                islaAcuaticaService.agregarDinosaurio(dinosaurio);
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitat desconocido: " + dinosaurio.getTipoHabitat());
        }
    }


    /**
     * Guarda un dinosaurio en la base de datos.
     * @param dinosaurio Dinosaurio a guardar.
     */
    public void guardarDinosaurio(Dinosaurio dinosaurio) {
        dinosaurioRepository.save(dinosaurio);
    }


    /**
     * Envía un mensaje a RabbitMQ indicando que un dinosaurio ha muerto.
     * @param dinosaurio Dinosaurio que ha muerto.
     */
    public void enviarMensajeMuerte(Dinosaurio dinosaurio) {
        String mensaje = "Dinosaurio con ID: " + dinosaurio.getId() + " ha muerto de vejez a la edad de: " + dinosaurio.getEdad();
        rabbitTemplate.convertAndSend(RabbitMQConfig.DINO_MUERTE_QUEUE, mensaje);
    }

    public void enviarMensajeReproduccionExitosa(Dinosaurio dino1, Dinosaurio dino2) {
        String mensaje = "Dinosaurio con ID:" +dino1.getId() + " y DIETA:" + dino1.getDieta() +
                " y Dinosaurio con ID:" + dino2.getId() + " y DIETA:" + dino2.getDieta() +
                " se han reproducido en la isla " + dino1.getTipoHabitat();
        rabbitTemplate.convertAndSend(RabbitMQConfig.REPRODUCCION_EXITOSA_QUEUE, mensaje);
    }

    public void enviarMensajeReproduccionFallida(Dinosaurio dino1, Dinosaurio dino2) {
        String mensaje = "Dinosaurio con ID:" + dino1.getId() + " y DIETA:" + dino1.getDieta() +
                " y Dinosaurio con ID:" + dino2.getId() + " y DIETA:" + dino2.getDieta() +
                " no pudieron reproducirse debido a dietas diferentes en la isla " + dino1.getTipoHabitat();
        rabbitTemplate.convertAndSend(RabbitMQConfig.REPRODUCCION_FALLIDA_QUEUE, mensaje);

    }

    public void enviarMensajeSinSuficientesDinos(String tipoHabitat) {
        String mensaje = "No se pudo iniciar el proceso de reproducción en la isla " + tipoHabitat +
                " debido a la falta de dinosaurios.";
        rabbitTemplate.convertAndSend(RabbitMQConfig.SIN_SUFICIENTES_DINOS_QUEUE, mensaje);

    }

    public void eliminarDinosaurio(Dinosaurio dinosaurio) {
        dinosaurioRepository.delete(dinosaurio);
    }
    public List<String> obtenerTodasLasIslas() {
        return Arrays.asList("TERRESTRE", "ACUATICO", "VOLADOR");
    }


}
