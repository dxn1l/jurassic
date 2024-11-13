package com.example.jurassic.controller;

import com.example.jurassic.entity.IslaVoladora;
import com.example.jurassic.service.IslaVoladoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/islavoladora")
public class IslaVoladoraController {

    @Autowired
    private IslaVoladoraService islaVoladoraService;


    @GetMapping("/todos")
    public Flux<IslaVoladora> obtenerTodosVoladores() {
        return islaVoladoraService.obtenerTodos();
    }
}
