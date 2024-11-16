package com.example.jurassic.controller;

import com.example.jurassic.entity.Cementerio;
import com.example.jurassic.service.CementerioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/cementerio")
public class CementerioController {

    private final CementerioService cementerioService;

    public CementerioController(CementerioService cementerioService) {
        this.cementerioService = cementerioService;
    }

    // Obtener todos los dinosaurios muertos
    @GetMapping("/todos")
    public List<Cementerio> obtenerTodosDinosauriosMuertos() {
        return cementerioService.obtenerTodosLosDinosauriosMuertos();
    }

    // Obtener dinosaurios muertos por dieta (Carnívoros o Herbívoros)
    @GetMapping("/dieta/{tipoDieta}")
    public List<Cementerio> obtenerDinosauriosMuertosPorDieta(@PathVariable String tipoDieta) {
        return cementerioService.obtenerDinosauriosMuertosPorDieta(tipoDieta);
    }

    @GetMapping("/habitat/{tipoHabitat}")
    public List<Cementerio> obtenerDinosauriosMuertosPorHabitat(@PathVariable String tipoHabitat) {
        return cementerioService.obtenerDinosauriosMuertosPorHabitat(tipoHabitat);
    }
}