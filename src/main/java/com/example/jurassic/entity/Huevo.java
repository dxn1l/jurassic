package com.example.jurassic.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Huevo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // Puede ser CARNIVORO, HERBIVORO, ACUATICO
    private LocalDateTime fechaCreacion;

    public Huevo() {
    }

    public Huevo(String tipo, LocalDateTime fechaCreacion) {
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Huevo{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}