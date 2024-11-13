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


    @Bean
    public Queue huevoCreacionQueue() {
        return new Queue(HUEVO_CREACION_QUEUE, false);
    }

    @Bean
    public Queue huevoEclosionQueue() {
        return new Queue(HUEVO_ECLOSION_QUEUE, false);
    }

}
