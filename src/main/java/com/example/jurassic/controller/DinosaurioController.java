package com.example.jurassic.controller;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.service.DinosaurioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dinosaurios")
public class DinosaurioController {

    private final DinosaurioService dinosaurioService;

    public DinosaurioController(DinosaurioService dinosaurioService) {
        this.dinosaurioService = dinosaurioService;
    }

    // Obtener todos los dinosaurios
    @GetMapping
    public List<Dinosaurio> obtenerTodosDinosaurios() {
        return dinosaurioService.obtenerTodosDinosaurios();
    }

    // Obtener dinosaurios por tipo (carnívoros, herbívoros, acuáticos)
    @GetMapping("/tipo/{tipo}")
    public List<Dinosaurio> obtenerDinosauriosPorTipo(@PathVariable String tipo) {
        return dinosaurioService.obtenerDinosauriosPorTipo(tipo);
    }

    // Obtener detalles de un dinosaurio específico
    @GetMapping("/{id}")
    public Dinosaurio obtenerDinosaurioPorId(@PathVariable Long id) {
        return dinosaurioService.obtenerDinosaurioPorId(id);
    }
}
