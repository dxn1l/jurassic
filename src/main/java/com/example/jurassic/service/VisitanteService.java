package com.example.jurassic.service;

import com.example.jurassic.entity.Visitante;
import com.example.jurassic.repository.VisitanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class VisitanteService {

    private static final Logger logger = LoggerFactory.getLogger(VisitanteService.class);

    @Autowired
    private VisitanteRepository visitanteRepository;

    /**
     * Generar un flujo de visitantes.
     *
     * @param cantidad Número de visitantes a generar.
     * @return Flujo de visitantes creados.
     */
    public Flux<Visitante> generarVisitantes(int cantidad) {
        List<Visitante> visitantes = new ArrayList<>();
        IntStream.range(0, cantidad).forEach(i ->
                visitantes.add(new Visitante("Visitante_" + i, "ENTRADA", LocalDateTime.now()))
        );

        visitanteRepository.saveAll(visitantes);

        logger.info("{} visitantes han llegado al parque.", cantidad);

        return Flux.fromIterable(visitantes); // Convertir la lista a un Flux
    }
    /**
     * Guardar visitantes actualizados en sus nuevas islas.
     *
     * @param visitantes Flujo de visitantes actualizados.
     * @return Flujo de visitantes guardados.
     */
    public Flux<Visitante> guardarVisitantes(Flux<Visitante> visitantes) {
        return visitantes
                .collectList() // Convertir el flujo a una lista
                .flatMapMany(lista -> Flux.fromIterable(visitanteRepository.saveAll(lista))); // Guardar en la base de datos
    }

    /**
     * Obtener un flujo de visitantes en la entrada.
     *
     * @return Flujo de visitantes en la entrada.
     */
    public Flux<Visitante> obtenerVisitantesEntrada() {
        return Flux.fromIterable(visitanteRepository.findByIsla("ENTRADA"));
    }
}
