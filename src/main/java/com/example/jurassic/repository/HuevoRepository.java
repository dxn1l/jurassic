package com.example.jurassic.repository;

import com.example.jurassic.entity.Huevo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HuevoRepository extends JpaRepository<Huevo, Long> {
    // Permite buscar huevos por tipo (carnívoro, herbívoro, acuático)
    List<Huevo> findByTipo(String tipo);
}