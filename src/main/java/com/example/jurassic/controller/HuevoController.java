package com.example.jurassic.controller;

import com.example.jurassic.entity.Huevo;
import com.example.jurassic.service.HuevoService;
import com.example.jurassic.service.IslaCriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/huevos")
public class HuevoController {

    private final IslaCriaService islaCriaService;
    private final HuevoService huevoService;


    public HuevoController(IslaCriaService islaCriaService, HuevoService huevoService) {
        this.islaCriaService = islaCriaService;
        this.huevoService = huevoService;
    }

    // Obtener todos los huevos
    @GetMapping
    public List<Huevo> obtenerTodosHuevos() {
        return huevoService.obtenerTodosHuevos();
    }

    // Obtener huevos por tipo (carnívoro, herbívoro, acuático)
    @GetMapping("/tipo/{tipo}")
    public List<Huevo> obtenerHuevosPorTipo(@PathVariable String tipo) {
        return huevoService.obtenerHuevosPorTipo(tipo);
    }

    // Obtener detalles de un huevo específico
    @GetMapping("/{id}")
    public Huevo obtenerHuevoPorId(@PathVariable Long id) {
        return huevoService.obtenerHuevoPorId(id);
    }

    // Endpoint para observar el flujo de generación de huevos en tiempo real
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Huevo> streamHuevos() {
        return islaCriaService.flujoGeneracionHuevos();
    }
}
