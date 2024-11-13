package com.example.jurassic.repository;

import com.example.jurassic.entity.Huevo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuevoRepository extends JpaRepository<Huevo, Long> {
}