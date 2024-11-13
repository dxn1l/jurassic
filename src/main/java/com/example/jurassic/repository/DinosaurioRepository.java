package com.example.jurassic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.jurassic.entity.Dinosaurio;
import java.util.List;

@Repository
public interface DinosaurioRepository extends JpaRepository<Dinosaurio, Long> {

    // Filtra dinosaurios por tipo de hábitat (terrestre, acuático, volador)
    List<Dinosaurio> findByTipoHabitat(String tipoHabitat);

    // Filtra dinosaurios por dieta (herbívoro, carnívoro)
    List<Dinosaurio> findByDieta(String dieta);
}


