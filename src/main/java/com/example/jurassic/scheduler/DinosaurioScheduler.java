package com.example.jurassic.scheduler;

import com.example.jurassic.entity.Dinosaurio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import com.example.jurassic.service.DinosaurioService;

@Component
public class DinosaurioScheduler {

    @Autowired
    private DinosaurioService dinosaurioService;

    @Scheduled(fixedRate = 10000) // Cada 10 segundos
    public void actualizarEdadDinosaurios() {
        List<Dinosaurio> dinosaurios = dinosaurioService.obtenerTodosDinosaurios();

        for (Dinosaurio dino : dinosaurios) {
            dino.setEdad(dino.getEdad() + 1);

            // Si la edad llega a 10, eliminar el dinosaurio y notificar su muerte
            if (dino.getEdad() >= 10) {
                dinosaurioService.eliminarDinosaurio(dino);
                dinosaurioService.enviarMensajeMuerte(dino);
            } else {
                dinosaurioService.guardarDinosaurio(dino); // Actualiza la edad en la base de datos
            }
        }
    }
}
