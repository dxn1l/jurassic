package com.example.jurassic.repository;

import com.example.jurassic.entity.IslaTerrestre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IslaTerrestreRepository extends JpaRepository<IslaTerrestre, Long> {
    List<IslaTerrestre> findByDieta(String dieta);
}

