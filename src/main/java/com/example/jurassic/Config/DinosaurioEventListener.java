package com.example.jurassic.Config;



import com.example.jurassic.entity.Dinosaurio;
import com.example.jurassic.entity.Huevo;
import com.example.jurassic.service.DinosaurioService;
import com.example.jurassic.service.IslaCriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DinosaurioEventListener {

    private static final Logger logger = LoggerFactory.getLogger(DinosaurioEventListener.class);

    @Autowired
    private DinosaurioService dinosaurioService;

    @Autowired
    private IslaCriaService islaCriaService;

    @RabbitListener(queues = RabbitMQConfig.HUEVO_CREACION_QUEUE)
    public void onHuevoCreado(String mensaje) {
        try {
            Long huevoId = Long.parseLong(mensaje.replace("Huevo creado con ID: ", ""));
            Huevo huevo = islaCriaService.obtenerHuevoPorId(huevoId);
            if (huevo != null) {
                islaCriaService.iniciarProcesoDeEclosion(huevo); // Inicia el proceso de eclosión desde aquí
                logger.info("creado {} , Iniciando Proceso de eclosión para huevo con id: {}", huevo ,huevoId);
            } else {
                logger.error("Huevo no encontrado para el ID: {}", huevoId);
            }
        } catch (NumberFormatException e) {
            logger.error("Error al procesar el mensaje de creación de huevo: {}", mensaje, e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.HUEVO_ECLOSION_QUEUE)
    public void onHuevoEclosionado(String mensaje) {
        try {
            // Dividir el mensaje en las partes de HuevoID y DinosaurioID
            String[] partes = mensaje.split(",");

            // Asegurarse de que el mensaje tenga dos partes
            if (partes.length == 2) {
                Long huevoId = Long.parseLong(partes[0].split("=")[1].trim());
                Long dinosaurioId = Long.parseLong(partes[1].split("=")[1].trim());

                // Obtener el dinosaurio utilizando el ID
                Dinosaurio dinosaurio = dinosaurioService.obtenerDinosaurioPorId(dinosaurioId);
                if (dinosaurio != null) {
                    dinosaurioService.distribuirDinosaurio(dinosaurio);
                    logger.info("Huevo eclosionado con ID: {}, y Dinosaurio con ID: {} distribuido en isla de tipo {}", huevoId, dinosaurioId, dinosaurio.getTipoHabitat());
                } else {
                    logger.error("Dinosaurio no encontrado para el ID: {}", dinosaurioId);
                }
            } else {
                logger.error("Formato de mensaje inesperado: {}", mensaje);
            }
        } catch (Exception e) {
            logger.error("Error al procesar el mensaje de eclosión de huevo: {}", mensaje, e);
        }
    }


    @RabbitListener(queues = RabbitMQConfig.DINO_MUERTE_QUEUE)
    public void onDinoMuerto(String mensaje) {
        logger.info("Evento de muerte: {}", mensaje);
    }


    @RabbitListener(queues = RabbitMQConfig.REPRODUCCION_EXITOSA_QUEUE)
    public void onReproduccionExitosa(String mensaje) {
        logger.info("Evento de reproducción exitosa: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.REPRODUCCION_FALLIDA_QUEUE)
    public void onReproduccionFallida(String mensaje) {
        logger.info("Evento de reproducción fallida: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.SIN_SUFICIENTES_DINOS_QUEUE)
    public void onSinSuficientesDinos(String mensaje) {
        logger.info("Evento de falta de dinosaurios: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.HUEVO_REPRODUCCION_QUEUE)
    public void onHuevoReproduccion(String mensaje) {
        logger.info("Evento de creación de huevo por reproducción: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.PELEA_QUEUE)
    public void onPelea(String mensaje) {
        logger.info("Evento de pelea: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.MUERTE_PELEA_QUEUE)
    public void onMuertePelea(String mensaje) {
          logger.info("Evento de muerte por pelea: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.HERIDO_PELEA_QUEUE)
    public void onHeridoPelea(String mensaje) {
        logger.info("Evento de herido por pelea: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.REGRESO_HOSPITAL_QUEUE)
    public void onRegresoHospital(String mensaje) {
        logger.info("Evento de regreso del hospital: {}", mensaje);
    }

    @RabbitListener(queues = RabbitMQConfig.ENVIO_CEMENTERIO_QUEUE)
    public void onEnvioCementerio(String mensaje) {
        logger.info("Evento de envio al cementerio: {}", mensaje);
    }
}


