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
    public static final String PELEA_QUEUE = "peleaQueue";
    public static final String MUERTE_PELEA_QUEUE = "muertepeleaQueue";
    public static final String HERIDO_PELEA_QUEUE = "heridoPeleaQueue";
    public static final String REGRESO_HOSPITAL_QUEUE = "regresoHospitalQueue";
    public static final String ENVIO_CEMENTERIO_QUEUE = "envioCementerioQueue";



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

    @Bean
    public Queue envioCementerioQueue() {
        return new Queue(ENVIO_CEMENTERIO_QUEUE, false);
    }



    @Bean
    public Queue peleaQueue() {
        return new Queue(  PELEA_QUEUE, false);
    }

    @Bean
    public Queue muertePeleaQueue() {
        return new Queue(MUERTE_PELEA_QUEUE, false);
    }

    @Bean
    public Queue heridoPeleaQueue() {
        return new Queue(HERIDO_PELEA_QUEUE, false);
    }

    @Bean
    public Queue regresoHospitalQueue() {
        return new Queue(REGRESO_HOSPITAL_QUEUE, false);
    }

    @Bean
    public Queue cementerioQueue() {
        return new Queue(ENVIO_CEMENTERIO_QUEUE, false);
    }


}
