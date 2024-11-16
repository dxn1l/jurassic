package com.example.jurassic.scheduler;

import com.example.jurassic.service.VisitanteDistribucionService;
import com.example.jurassic.service.VisitanteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VisitanteScheduler {

    private static final Logger logger = LoggerFactory.getLogger(VisitanteScheduler.class);

    @Autowired
    private VisitanteService visitanteService;

    @Autowired
    private VisitanteDistribucionService visitanteDistribucionService;

    /**
     * Generar visitantes cada 15 segundos.
     */
    @Scheduled(fixedRate = 15000) // Cada 15 segundos
    public void generarVisitantes() {
        visitanteService.generarVisitantes(5)
                .doOnComplete(() -> logger.info("Evento: Llegada de visitantes completado."))
                .subscribe();
    }

    /**
     * Distribuir visitantes cada 20 segundos.
     */
    @Scheduled(fixedRate = 20000) // Cada 20 segundos
    public void distribuirVisitantes() {
        visitanteDistribucionService.distribuirVisitantes()
                .doOnComplete(() -> logger.info("Evento: Distribución de visitantes completado."))
                .subscribe();
    }
}
