package com.example.springboot;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class NotificacionConsumer{
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void procesar(Mensaje mensaje) {
        System.out.println("Es el des: " + mensaje.getDestino() + " Es el mensaje: " + mensaje.getEstado());
    }
}
