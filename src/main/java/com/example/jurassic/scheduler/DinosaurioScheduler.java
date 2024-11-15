package com.example.jurassic.scheduler;

import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.service.IslaAcuaticaService;
import com.example.jurassic.service.IslaTerrestreService;
import com.example.jurassic.service.IslaVoladoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import com.example.jurassic.service.DinosaurioService;

@Component
public class DinosaurioScheduler {

    @Autowired
    private DinosaurioService dinosaurioService;

    @Autowired
    private IslaTerrestreService islaTerrestreService;

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

    @Autowired
    private IslaVoladoraService islaVoladoraService;

    @Scheduled(fixedRate = 10000) // Cada 10 segundos
    public void actualizarEdadDinosauriosEIslas() {
        // Incrementar edad de dinosaurios
        List<Dinosaurio> dinosaurios = dinosaurioService.obtenerTodosDinosaurios();
        for (Dinosaurio dino : dinosaurios) {
            dino.setEdad(dino.getEdad() + 1);

            // Actualizar la edad de la isla correspondiente
            switch (dino.getTipoHabitat()) {
                case "TERRESTRE":
                    islaTerrestreService.incrementarEdadIsla(dino.getId());
                    break;
                case "ACUATICO":
                    islaAcuaticaService.incrementarEdadIsla(dino.getId());
                    break;
                case "VOLADOR":
                    islaVoladoraService.incrementarEdadIsla(dino.getId());
                    break;
            }

            // Si el dinosaurio alcanza edad de muerte
            if (dino.getEdad() >= 10) {
                dinosaurioService.eliminarDinosaurio(dino);

                switch (dino.getTipoHabitat()) {
                    case "TERRESTRE":
                        islaTerrestreService.eliminarDinosaurio(dino);
                        break;
                    case "ACUATICO":
                        islaAcuaticaService.eliminarDinosaurio(dino);
                        break;
                    case "VOLADOR":
                        islaVoladoraService.eliminarDinosaurio(dino);
                        break;
                }

                dinosaurioService.enviarMensajeMuerte(dino);
            } else {
                dinosaurioService.guardarDinosaurio(dino);
            }
        }
    }
}
