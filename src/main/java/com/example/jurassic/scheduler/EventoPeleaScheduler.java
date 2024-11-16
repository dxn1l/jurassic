package com.example.jurassic.scheduler;


import com.example.jurassic.service.PeleaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventoPeleaScheduler {

    @Autowired
    private PeleaService peleaService;

    @Scheduled(fixedRate = 10000) // Cada 30 segundos
    public void ejecutarEventoPelea() {
        peleaService.iniciarEventoPelea().subscribe();
    }
}

