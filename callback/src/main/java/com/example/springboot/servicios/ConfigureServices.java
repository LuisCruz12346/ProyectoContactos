package com.example.springboot;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfigureServices {

    private final NotificacionProducer producer;

    public String regresarPrueba() {
        producer.publicar(Mensaje.builder()
                            .destino("Mi tio")
                            .estado(1L)
                            .build());
        
        return  "Yo sigo con mis cosas";
    }
}