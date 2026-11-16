package com.example.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "notificaciones.exchange";
    public static final String QUEUE = "notificaciones.queue";
    public static final String ROUTING_KEY = "notificaciones.enviar";
    public static final String DLX = "notificaciones.dlx";
    public static final String DLQ = "notificaciones.dlq";

    /**
     * Creamos un intercambio de tipo directo. 
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }
    
    /**
     * Creamos la cola de mensaje, con su respectivo mensaje, y ademas una cola de rechazo 
     */
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE)
                .deadLetterExchange(DLX)
                .deadLetterRoutingKey(DLQ)
                .build();
    }

    /**
     * Metodo que establece la relacion entre la cola, el intercambiador y la llave de asociacion. 
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }

    /**
     * Definimo un intercambiador para los mensajes rechazados. 
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    /**
     * Creamos la cola que guardaran los mensajes rechazados. 
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    /**
     * Definimos la relacion entre el intercambiador y la cola. 
     */
    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ);
    }

    /**
     * Definimos el comportamiento de los mensajes recibidos y enviados. 
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}