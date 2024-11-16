package com.example.jurassic.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Cementerio {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dieta;
    private String tipoHabitat;
    private int edad;
    private LocalDateTime fechaMuerte;
    public Cementerio() {}
    public Cementerio(String dieta, String tipoHabitat, int edad, LocalDateTime fechaMuerte) {
        this.dieta = dieta;
        this.tipoHabitat = tipoHabitat;
        this.edad = edad;
        this.fechaMuerte = fechaMuerte;
    }
    // Getters y setters
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
    public LocalDateTime getFechaMuerte() {
        return fechaMuerte;
    }
    public void setFechaMuerte(LocalDateTime fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }
}
