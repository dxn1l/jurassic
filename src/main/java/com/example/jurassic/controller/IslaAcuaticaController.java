package com.example.jurassic.controller;


import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaAcuatica;
import com.example.jurassic.service.IslaAcuaticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/islaacuatica")
public class IslaAcuaticaController {

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

    @GetMapping("/todos")
    public Flux<IslaAcuatica> obtenerTodosAcuaticos() {
        return islaAcuaticaService.obtenerTodos();
    }
}