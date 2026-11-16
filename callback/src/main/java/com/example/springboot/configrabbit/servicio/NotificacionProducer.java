package com.example.springboot;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Service
@RequiredArgsConstructor
public class NotificacionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publicar(Mensaje mensaje) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                mensaje);
    }
}