package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Hospital;
import com.example.jurassic.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Mono<Hospital> enviarDinosaurioAlHospital(Dinosaurio dinosaurio) {
        return Mono.fromCallable(() -> {
            Hospital hospital = new Hospital(
                    dinosaurio.getId(),
                    dinosaurio.getDieta(),
                    dinosaurio.getTipoHabitat(),
                    dinosaurio.getEdad(),
                    dinosaurio.getFechaEclosion()
            );
            return hospitalRepository.save(hospital);
        });
    }
}
