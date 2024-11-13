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

    private String dieta; // Herbívoro o Carnívoro
    private String tipoHabitat; // Terrestre, Acuático o Volador
    private LocalDateTime fechaCreacion;


    public Huevo() {
    }

    public Huevo(String dieta, String tipoHabitat) {
        this.dieta = dieta;
        this.tipoHabitat = tipoHabitat;
        this.fechaCreacion = LocalDateTime.now();
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
                ", dieta='" + dieta + '\'' +
                ", tipoHabitat='" + tipoHabitat + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}