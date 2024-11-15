package com.example.jurassic.scheduler;

import com.example.jurassic.service.HuevoService;
import com.example.jurassic.service.IslaAcuaticaService;
import com.example.jurassic.service.IslaTerrestreService;
import com.example.jurassic.service.IslaVoladoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ReproduccionScheduler {

    @Autowired
    private IslaTerrestreService islaTerrestreService;

    @Autowired
    private IslaAcuaticaService islaAcuaticaService;

    @Autowired
    private IslaVoladoraService islaVoladoraService;

    @Autowired
    private HuevoService huevoService;

    @Scheduled(fixedRate = 30000) // Cada 30 segundos
    public void iniciarEventoReproduccion() {
        // Seleccionar aleatoriamente una isla
        int seleccionIsla = ThreadLocalRandom.current().nextInt(1, 4); // 1 = terrestre, 2 = acuática, 3 = voladora

        switch (seleccionIsla) {
            case 1:
                reproducirEnIslaTerrestre();
                break;
            case 2:
                reproducirEnIslaAcuatica();
                break;
            case 3:
                reproducirEnIslaVoladora();
                break;
        }
    }

    private void reproducirEnIslaTerrestre() {
        islaTerrestreService.reproducirDinosaurios();
    }

    private void reproducirEnIslaAcuatica() {
        islaAcuaticaService.reproducirDinosaurios();
    }

    private void reproducirEnIslaVoladora() {
        islaVoladoraService.reproducirDinosaurios();
    }
}

