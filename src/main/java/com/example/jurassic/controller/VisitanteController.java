package com.example.jurassic.controller;

import com.example.jurassic.entity.Visitante;
import com.example.jurassic.service.VisitanteDistribucionService;
import com.example.jurassic.service.VisitanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/visitantes")
public class VisitanteController {

    @Autowired
    private VisitanteService visitanteService;

    @Autowired
    private VisitanteDistribucionService visitanteDistribucionService;

    /**
     * Generar nuevos visitantes.
     *
     * @return Flujo de visitantes generados.
     */
    @PostMapping("/generar")
    public Flux<Visitante> generarVisitantes() {
        return visitanteService.generarVisitantes(50); // Generar 50 visitantes
    }

    /**
     * Obtener visitantes en la entrada.
     *
     * @return Flujo de visitantes en la entrada.
     */
    @GetMapping("/entrada")
    public Flux<Visitante> obtenerVisitantesEntrada() {
        return visitanteService.obtenerVisitantesEntrada();
    }

    /**
     * Distribuir visitantes de la entrada a las islas.
     *
     * @return Flujo de visitantes distribuidos.
     */
    @PostMapping("/distribuir")
    public Flux<Visitante> distribuirVisitantes() {
        return visitanteDistribucionService.distribuirVisitantes();
    }
}
