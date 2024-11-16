package com.example.jurassic.service;

import com.example.jurassic.Config.RabbitMQConfig;
import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Hospital;
import com.example.jurassic.repository.HospitalRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Mono<Void> procesarDinosaurioEnHospital(Dinosaurio dinosaurio) {
        return Mono.delay(Duration.ofSeconds(20)) // Esperar 20 segundos
                .flatMap(tick -> {
                    Hospital hospital = hospitalRepository.findById(dinosaurio.getId()).orElse(null);
                    if (hospital != null) {
                        hospitalRepository.delete(hospital); // Eliminar del hospital
                        reintroducirDinosaurioEnIsla(dinosaurio); // Reintroducir en la isla correspondiente
                        enviarMensajeRegresoDelHospital(dinosaurio); // Enviar mensaje de regreso
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

    private void enviarMensajeRegresoDelHospital(Dinosaurio dinosaurio) {
        String mensaje = String.format("Dinosaurio %d regresó del hospital a la isla %s.",
                dinosaurio.getId(), dinosaurio.getTipoHabitat());
        rabbitTemplate.convertAndSend(RabbitMQConfig.REGRESO_HOSPITAL_QUEUE, mensaje);
    }

    // Obtener todos los dinosaurios heridos
    public List<Hospital> obtenerTodosLosDinosauriosHeridos() {
        return hospitalRepository.findAll();
    }

    // Obtener dinosaurios heridos por dieta
    public List<Hospital> obtenerDinosauriosHeridosPorDieta(String dieta) {
        return hospitalRepository.findByDieta(dieta);
    }

    public List<Hospital> obtenerDinosauriosHeridosPorHabitat(String habitat) {
        return hospitalRepository.findByTipoHabitat(habitat);
    }
}
