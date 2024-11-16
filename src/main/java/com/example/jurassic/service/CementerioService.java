package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Cementerio;
import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.repository.CementerioRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class CementerioService {
    @Autowired
    private CementerioRepository cementerioRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarDinosaurioAlCementerio(Dinosaurio dinosaurio) {
        Cementerio cementerio = new Cementerio(
                dinosaurio.getDieta(),
                dinosaurio.getTipoHabitat(),
                dinosaurio.getEdad(),
                LocalDateTime.now() // Fecha de muerte actual
        );
        cementerio.setId(dinosaurio.getId()); // Asignar manualmente el ID
        cementerioRepository.save(cementerio);
        enviarMensajeEnvioAlCementerio(dinosaurio);
    }

    public void enviarMensajeEnvioAlCementerio(Dinosaurio dinosaurio) {
        String mensaje = "Dinosaurio con ID " + dinosaurio.getId() + " enviado al cementerio.";
        rabbitTemplate.convertAndSend(RabbitMQConfig.ENVIO_CEMENTERIO_QUEUE, mensaje);

    }



}
