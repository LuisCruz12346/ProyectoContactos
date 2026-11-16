package com.example.springboot;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EnvioMensajes{
    @Async
    public void yoEnvioLosMensajes(){
        try {
            Thread.sleep(2000); // Espera unos segundos
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    } 
}