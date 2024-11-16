package com.example.jurassic.repository;

import com.example.jurassic.entity.IslaAcuatica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IslaAcuaticaRepository extends JpaRepository<IslaAcuatica, Long> {
    List<IslaAcuatica> findByDieta(String dieta);
}
