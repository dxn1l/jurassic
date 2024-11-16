package com.example.jurassic.repository;

import com.example.jurassic.entity.Cementerio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CementerioRepository extends JpaRepository<Cementerio, Long> {
    List<Cementerio> findByDieta(String dieta);
    List<Cementerio> findByTipoHabitat(String habitat);
}