package com.example.jurassic.service;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Huevo;
import com.example.jurassic.entity.IslaTerrestre;
import com.example.jurassic.repository.DinosaurioRepository;
import com.example.jurassic.repository.IslaTerrestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class IslaTerrestreService {

    @Autowired
    private IslaTerrestreRepository islaTerrestreRepository;

    @Autowired
    private HuevoService huevoService;

    @Autowired
    private DinosaurioService dinosaurioService;

    public Flux<IslaTerrestre> obtenerTodos() {
        return Flux.fromIterable(islaTerrestreRepository.findAll());
    }

    public void agregarDinosaurio(Dinosaurio dinosaurio) {
        IslaTerrestre terrestre = new IslaTerrestre(dinosaurio.getId(), dinosaurio.getDieta(), dinosaurio.getTipoHabitat(), dinosaurio.getFechaEclosion(), dinosaurio.getEdad());
        islaTerrestreRepository.save(terrestre);
    }

    /**
     * Incrementar la edad de una isla específica basada en el ID del dinosaurio.
     */
    public void incrementarEdadIsla(Long dinosaurioId) {
        IslaTerrestre isla = islaTerrestreRepository.findById(dinosaurioId).orElse(null);
        if (isla != null) {
            isla.setEdad(isla.getEdad() + 1);
            islaTerrestreRepository.save(isla);
        }
    }


    /**
     * Eliminar un dinosaurio de la isla correspondiente.
     */
    public void eliminarDinosaurio(Dinosaurio dinosaurio) {
        IslaTerrestre isla = islaTerrestreRepository.findById(dinosaurio.getId()).orElse(null);
        if (isla != null) {
            // Actualiza la lógica si hay una relación directa con la isla
            islaTerrestreRepository.delete(isla); // Aquí elimina la referencia en la isla
        }
    }

    //reproduccion
    public void reproducirDinosaurios() {
        // Obtener todos los dinosaurios de la isla terrestre
        List<Dinosaurio> dinosaurios = dinosaurioService.obtenerDinosauriosPorTipoHabitat("TERRESTRE");

        if (dinosaurios.size() < 2) {
            dinosaurioService.enviarMensajeSinSuficientesDinos("TERRESTRE");
            return;
        }

            Dinosaurio dino1 = dinosaurios.get(ThreadLocalRandom.current().nextInt(dinosaurios.size()));
            Dinosaurio dino2 = dinosaurios.stream()
                    .filter(d -> !d.getId().equals(dino1.getId()) && d.getDieta().equals(dino1.getDieta()))
                    .findFirst()
                    .orElse(null);

            if (dino2 != null) {
                dinosaurioService.enviarMensajeReproduccionExitosa(dino1, dino2);

                // Crear y guardar un nuevo huevo
                Huevo huevo = new Huevo(dino1.getDieta(), dino1.getTipoHabitat());
                huevoService.guardarHuevo(huevo);

            }else {

                dinosaurioService.enviarMensajeReproduccionFallida(dino1, dino2);

            }

    }


}


