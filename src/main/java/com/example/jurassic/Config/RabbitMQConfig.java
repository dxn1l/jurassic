package com.example.jurassic.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String HUEVO_CREACION_QUEUE = "huevoCreacionQueue";
    public static final String HUEVO_ECLOSION_QUEUE = "huevoEclosionQueue";
    public static final String DINO_MUERTE_QUEUE = "dinoMuerteQueue";
    public static final String REPRODUCCION_EXITOSA_QUEUE = "reproduccionExitosaQueue";
    public static final String REPRODUCCION_FALLIDA_QUEUE = "reproduccionFallidaQueue";
    public static final String SIN_SUFICIENTES_DINOS_QUEUE = "sinSuficientesDinosQueue";
    public static final String HUEVO_REPRODUCCION_QUEUE = "huevoReproduccionQueue";



    @Bean
    public Queue huevoCreacionQueue() {
        return new Queue(HUEVO_CREACION_QUEUE, false);
    }

    @Bean
    public Queue huevoEclosionQueue() {
        return new Queue(HUEVO_ECLOSION_QUEUE, false);
    }

    @Bean
    public Queue dinoMuerteQueue() {
        return new Queue(DINO_MUERTE_QUEUE, false);
    }

    @Bean
    public Queue reproduccionExitosaQueue() {
        return new Queue(REPRODUCCION_EXITOSA_QUEUE, false);
    }

    @Bean
    public Queue reproduccionFallidaQueue() {
        return new Queue(REPRODUCCION_FALLIDA_QUEUE, false);
    }

    @Bean
    public Queue sinSuficientesDinosQueue() {
        return new Queue(SIN_SUFICIENTES_DINOS_QUEUE, false);
    }

    @Bean
    public Queue huevoReproduccionQueue() {
        return new Queue(HUEVO_REPRODUCCION_QUEUE, false);
    }

}
