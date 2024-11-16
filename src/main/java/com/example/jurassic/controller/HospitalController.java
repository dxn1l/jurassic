package com.example.jurassic.controller;

import com.example.jurassic.entity.Hospital;
import com.example.jurassic.service.HospitalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    // Obtener todos los dinosaurios heridos
    @GetMapping("/todos")
    public List<Hospital> obtenerTodosDinosauriosHeridos() {
        return hospitalService.obtenerTodosLosDinosauriosHeridos();
    }

    // Obtener dinosaurios heridos por dieta (Carnívoros o Herbívoros)
    @GetMapping("/dieta/{tipoDieta}")
    public List<Hospital> obtenerDinosauriosHeridosPorDieta(@PathVariable String tipoDieta) {
        return hospitalService.obtenerDinosauriosHeridosPorDieta(tipoDieta);
    }

    @GetMapping("/habitat/{tipoHabitat}")
    public List<Hospital> obtenerDinosauriosHeridosPorHabitat(@PathVariable String tipoHabitat) {
        return hospitalService.obtenerDinosauriosHeridosPorHabitat(tipoHabitat);
    }

}