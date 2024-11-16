package com.example.jurassic.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class IslaAcuatica {
    @Id
    private Long id;
    private String dieta;
    private String tipoHabitat;
    private LocalDateTime fechaEclosion;
    private int edad;

    public IslaAcuatica(Long id, String dieta, String tipoHabitat, LocalDateTime fechaEclosion, int edad) {
        this.id = id;
        this.dieta = dieta;
        this.tipoHabitat = tipoHabitat;
        this.fechaEclosion = fechaEclosion;
        this.edad = edad;
    }

    public IslaAcuatica() {

    }

    // Constructor, getters, setters

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

    public String toString() {
        return "IslaAcuatica{" +
                "id=" + id +
                ", dieta='" + dieta + '\'' +
                ", tipoHabitat='" + tipoHabitat + '\'' +
                ", fechaEclosion=" + fechaEclosion +
                ", edad=" + edad +
                '}';
    }

}
