package com.example.jurassic.entity;

import com.example.jurassic.service.DinosaurioService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
public class Dinosaurio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dieta; // Herbívoro o Carnívoro
    private String tipoHabitat; // Terrestre, Acuático o Volador
    private LocalDateTime fechaEclosion;
    private int edad; // Edad inicial, que puede empezar en 0

    // Constructor con parámetros
    public Dinosaurio(String dieta, String tipoHabitat) {
        this.dieta = dieta;
        this.tipoHabitat = tipoHabitat;
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
    

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String tipo) {
        this.dieta = tipo;
    }
    public String getTipoHabitat() {
        return tipoHabitat;
    }

    public void setTipoHabitat(String tipoHabitat) {
        this.dieta = tipoHabitat;
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
                ", dieta='" + dieta + '\'' +
                ", tipoHabitad='" + tipoHabitat + '\'' +
                ", fechaEclosion=" + fechaEclosion +
                ", edad=" + edad +
                '}';
    }
}
