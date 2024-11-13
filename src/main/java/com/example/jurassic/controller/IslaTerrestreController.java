package com.example.jurassic.controller;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.IslaTerrestre;
import com.example.jurassic.service.IslaTerrestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/islaterrestre")
public class IslaTerrestreController {

    @Autowired
    private IslaTerrestreService islaTerrestresService;


    @GetMapping("/todos")
    public Flux<IslaTerrestre> obtenerTodosTerrestres() {
        return islaTerrestresService.obtenerTodos();
    }
}