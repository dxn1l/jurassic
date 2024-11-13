package com.example.jurassic.controller;

import com.example.jurassic.entity.Huevo;
import com.example.jurassic.service.IslaCriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/huevos")
public class HuevoController {

    private final IslaCriaService islaCriaService;

    public HuevoController(IslaCriaService islaCriaService) {
        this.islaCriaService = islaCriaService;
    }

    // Endpoint para observar el flujo de generación de huevos en tiempo real
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Huevo> streamHuevos() {
        return islaCriaService.flujoGeneracionHuevos();
    }
}
