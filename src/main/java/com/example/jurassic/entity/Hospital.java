package com.example.jurassic.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Hospital {

    @Id
    private Long id; // Ya no será generado automáticamente

    private String dieta;
    private String tipoHabitat;
    private int edad;
    private LocalDateTime fechaEclosion;

    public Hospital() {}

    public Hospital(Long id, String dieta, String tipoHabitat, int edad, LocalDateTime fechaEclosion) {
        this.id = id;
        this.dieta = dieta;
        this.tipoHabitat = tipoHabitat;
        this.edad = edad;
        this.fechaEclosion = fechaEclosion;
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

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public String getTipoHabitat() {
        return tipoHabitat;
    }

    public void setTipoHabitat(String tipoHabitat) {
        this.tipoHabitat = tipoHabitat;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDateTime getFechaEclosion() {
        return fechaEclosion;
    }

    public void setFechaEclosion(LocalDateTime fechaEclosion) {
        this.fechaEclosion = fechaEclosion;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", dieta='" + dieta + '\'' +
                ", tipoHabitat='" + tipoHabitat + '\'' +
                ", edad=" + edad +
                ", fechaEclosion=" + fechaEclosion +
                '}';
    }
}
