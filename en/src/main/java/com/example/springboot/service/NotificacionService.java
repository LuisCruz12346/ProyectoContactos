package com.example.springboot.service;

import com.example.springboot.dto.NotificacionDto;
import com.example.springboot.model.EstadoNotificacion;
import com.example.springboot.model.Notificacion;
import com.example.springboot.repository.NotificacionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.Instant;

@Service
public class NotificacionService {

    private final NotificacionRepository repository;
    //private final WebClient webClient;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void guardar(NotificacionDto dto) {
        repository.saveAndFlush(
            new Notificacion(dto.getIdMensaje(), EstadoNotificacion.PENDIENTE));
    }

    public void enviar(Notificacion n) {
        try {
           System.out.println("Enviando");
            repository.cambiarEstado(n.getIdMensaje(), EstadoNotificacion.ENTREGADO);
        } catch (Exception e) {
            // Se queda PENDIENTE; el siguiente ciclo reintenta o lo vence
            System.out.println("Falló el envío");
            //log.warn("Falló el envío de {}", n.getIdMensaje(), e);
        }
    }

    @Scheduled(fixedDelay = 20_000)
    public void intentarPendientes() {
        Instant limite = Instant.now().minus(Duration.ofMinutes(2));

        int vencidos = repository.marcarVencidos(limite);
        if (vencidos > 0) {
            System.out.println("Pasaron a fallido");
            //log.warn("{} notificaciones pasaron a FALLIDO por vencimiento", vencidos);
        }

        repository.findTop50ByEstadoOrderByFechaCreacionAsc(EstadoNotificacion.PENDIENTE)
                .forEach(this::enviar);
    }
}