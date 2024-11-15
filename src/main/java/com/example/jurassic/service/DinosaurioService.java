package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.repository.DinosaurioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DinosaurioService {

    private final DinosaurioRepository dinosaurioRepository;

    @Autowired
    private IslaVoladoraService islaVoladoraService;

    @Autowired
    private IslaTerrestreService islaTerrestreService;

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

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
        logger.info("Dinosaurio con ID: {} actualizado en la base de datos", dinosaurio.getId());
    }

    /**
     * Elimina un dinosaurio de la base de datos.
     * @param dinosaurio Dinosaurio a eliminar.
     */
    public void eliminarDinosaurio(Dinosaurio dinosaurio) {
        dinosaurioRepository.delete(dinosaurio);
        logger.info("Dinosaurio con ID: {} eliminado de la base de datos", dinosaurio.getId());
    }

    /**
     * Envía un mensaje a RabbitMQ indicando que un dinosaurio ha muerto.
     * @param dinosaurio Dinosaurio que ha muerto.
     */
    public void enviarMensajeMuerte(Dinosaurio dinosaurio) {
        String mensaje = "Dinosaurio con ID: " + dinosaurio.getId() + " ha muerto a la edad de: " + dinosaurio.getEdad();
        rabbitTemplate.convertAndSend(RabbitMQConfig.DINO_MUERTE_QUEUE, mensaje);
        logger.info("Mensaje de muerte enviado a RabbitMQ para el dinosaurio con ID: {}", dinosaurio.getId());
    }

}
