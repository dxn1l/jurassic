package com.example.jurassic.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitantes")
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String isla; // Isla a la que pertenece (ENTRADA, TERRESTRE, ACUATICA, VOLADORA)

    @Column(nullable = false)
    private LocalDateTime fechaEntrada; // Fecha y hora de entrada al parque o la isla

    public Visitante() {
    }

    public Visitante(String nombre, String isla, LocalDateTime fechaEntrada) {
        this.nombre = nombre;
        this.isla = isla;
        this.fechaEntrada = fechaEntrada;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIsla() {
        return isla;
    }

    public void setIsla(String isla) {
        this.isla = isla;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    @Override
    public String toString() {
        return "Visitante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", isla='" + isla + '\'' +
                ", fechaEntrada=" + fechaEntrada +
                '}';
    }
}
