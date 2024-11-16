package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Visitante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class VisitanteDistribucionService {

    private static final Logger logger = LoggerFactory.getLogger(VisitanteDistribucionService.class);

    @Autowired
    private VisitanteService visitanteService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Distribuir visitantes de la entrada a las islas.
     *
     * @return Flujo de visitantes distribuidos.
     */
    public Flux<Visitante> distribuirVisitantes() {
        return visitanteService.obtenerVisitantesEntrada() // Selecciona solo los que están en la entrada
                .buffer(5) // Procesar en grupos de 10
                .flatMap(grupo -> {
                    grupo.forEach(visitante -> {
                        String isla = seleccionarIslaAleatoria();
                        visitante.setIsla(isla); // Asignar la nueva isla al visitante
                        String mensaje = String.format("Visitante con ID=:%d ha decidido visitar la Isla:=%s", visitante.getId(), isla);
                        rabbitTemplate.convertAndSend(RabbitMQConfig.VISITANTE_DISTRIBUIDO_QUEUE, mensaje);
                    });

                    // Guardar los visitantes actualizados en la base de datos
                    return visitanteService.guardarVisitantes(Flux.fromIterable(grupo));
                })
                .doOnComplete(() -> logger.info("Distribución completa de un grupo de visitantes."));
    }

    private String seleccionarIslaAleatoria() {
        String[] islas = {"TERRESTRE", "ACUATICA", "VOLADORA"};
        return islas[ThreadLocalRandom.current().nextInt(islas.length)];
    }

}
