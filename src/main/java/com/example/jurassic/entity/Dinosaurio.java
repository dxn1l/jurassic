package com.example.jurassic.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Dinosaurio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // Puede ser CARNIVORO, HERBIVORO, ACUATICO
    private LocalDateTime fechaEclosion;
    private int edad; // Edad inicial, que puede empezar en 0

    // Constructor con parámetros
    public Dinosaurio(String tipo) {
        this.tipo = tipo;
        this.fechaEclosion = LocalDateTime.now();
        this.edad = 0;
    }

    // Constructor vacío para JPA
    public Dinosaurio() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaEclosion() {
        return fechaEclosion;
    }

    public void setFechaEclosion(LocalDateTime fechaEclosion) {
        this.fechaEclosion = fechaEclosion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Dinosaurio{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", fechaEclosion=" + fechaEclosion +
                ", edad=" + edad +
                '}';
    }
}
