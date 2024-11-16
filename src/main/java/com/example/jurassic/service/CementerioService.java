package com.example.jurassic.service;

import com.example.jurassic.entity.Cementerio;
import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.repository.CementerioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class CementerioService {
    @Autowired
    private CementerioRepository cementerioRepository;

    public void enviarDinosaurioAlCementerio(Dinosaurio dinosaurio) {
        Cementerio cementerio = new Cementerio(
                dinosaurio.getDieta(),
                dinosaurio.getTipoHabitat(),
                dinosaurio.getEdad(),
                LocalDateTime.now() // Fecha de muerte actual
        );
        cementerioRepository.save(cementerio);
        System.out.println("Dinosaurio con ID " + dinosaurio.getId() + " enviado al cementerio.");
    }

}
