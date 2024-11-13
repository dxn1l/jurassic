package com.example.jurassic.repository;

import com.example.jurassic.entity.Dinosaurio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinosaurioRepository extends JpaRepository<Dinosaurio, Long> {
    List<Dinosaurio> findByTipo(String tipo);

}

