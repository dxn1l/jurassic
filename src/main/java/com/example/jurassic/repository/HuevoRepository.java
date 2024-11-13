package com.example.jurassic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.jurassic.entity.Huevo;
import java.util.List;

@Repository
public interface HuevoRepository extends JpaRepository<Huevo, Long> {

    // Filtra huevos por tipo de hábitat (terrestre, acuático, volador)
    List<Huevo> findByTipoHabitat(String tipoHabitat);

    // Filtra huevos por dieta (herbívoro, carnívoro)
    List<Huevo> findByDieta(String dieta);
}
