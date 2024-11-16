package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Hospital;
import com.example.jurassic.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DinosaurioService dinosaurioService;

    @Autowired
    private IslaTerrestreService islaTerrestreService;

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

    @Autowired
    private IslaVoladoraService islaVoladoraService;

    public Mono<Void> procesarDinosaurioEnHospital(Dinosaurio dinosaurio) {
        return Mono.delay(Duration.ofSeconds(20)) // Esperar 20 segundos
                .flatMap(tick -> {
                    Hospital hospital = hospitalRepository.findById(dinosaurio.getId()).orElse(null);
                    if (hospital != null) {
                        hospitalRepository.delete(hospital); // Eliminar del hospital
                        reintroducirDinosaurioEnIsla(dinosaurio); // Reintroducir en la isla correspondiente
                        System.out.println("Dinosaurio con ID " + dinosaurio.getId() + " ha regresado a su isla.");
                    }
                    return Mono.empty();
                });
    }

    private void reintroducirDinosaurioEnIsla(Dinosaurio dinosaurio) {
        switch (dinosaurio.getTipoHabitat()) {
            case "TERRESTRE":
                islaTerrestreService.agregarDinosaurio(dinosaurio);
                break;
            case "ACUATICO":
                islaAcuaticaService.agregarDinosaurio(dinosaurio);
                break;
            case "VOLADOR":
                islaVoladoraService.agregarDinosaurio(dinosaurio);
                break;
            default:
                throw new IllegalArgumentException("Tipo de hábitat desconocido: " + dinosaurio.getTipoHabitat());
        }
    }

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
