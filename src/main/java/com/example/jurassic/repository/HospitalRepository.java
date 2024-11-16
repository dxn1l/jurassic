package com.example.jurassic.repository;

import com.example.jurassic.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findByDieta(String dieta);
    List<Hospital> findByTipoHabitat(String habitat);
}
