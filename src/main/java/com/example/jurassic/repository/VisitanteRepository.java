package com.example.jurassic.repository;

import com.example.jurassic.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitanteRepository extends JpaRepository<Visitante, Long> {

    /**
     * Encuentra todos los visitantes en una isla específica.
     *
     * @param isla Nombre de la isla (e.g., "ENTRADA", "TERRESTRE").
     * @return Lista de visitantes en la isla.
     */
    List<Visitante> findByIsla(String isla);

    /**
     * Cuenta los visitantes en una isla específica.
     *
     * @param isla Nombre de la isla.
     * @return Número de visitantes en la isla.
     */
    @Query("SELECT COUNT(v) FROM Visitante v WHERE v.isla = :isla")
    long countByIsla(String isla);

    /**
     * Elimina todos los visitantes en una isla específica.
     *
     * @param isla Nombre de la isla.
     */
    @Query("DELETE FROM Visitante v WHERE v.isla = :isla")
    void deleteByIsla(String isla);
}

