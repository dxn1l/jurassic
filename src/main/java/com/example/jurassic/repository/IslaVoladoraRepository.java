package com.example.jurassic.repository;

import com.example.jurassic.entity.IslaVoladora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IslaVoladoraRepository extends JpaRepository<IslaVoladora, Long> {
    List<IslaVoladora> findByDieta(String dieta);

}
