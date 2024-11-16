package com.example.jurassic.controller;


import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaAcuatica;
import com.example.jurassic.service.IslaAcuaticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/islaacuatica")
public class IslaAcuaticaController {

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

    @GetMapping("/todos")
    public Flux<IslaAcuatica> obtenerTodosAcuaticos() {
        return islaAcuaticaService.obtenerTodos();
    }

    @GetMapping("/dieta/{tipoDieta}")
    public List<IslaAcuatica> obtenerDinosauriosPorDieta(@PathVariable String tipoDieta) {
        return islaAcuaticaService.obtenerPorDieta(tipoDieta);
    }
}