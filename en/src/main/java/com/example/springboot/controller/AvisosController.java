package com.example.springboot.controller;

import com.example.springboot.dto.NotificacionDto;
import com.example.springboot.service.NotificacionService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvisosController {

    private final NotificacionService service;

    public AvisosController(NotificacionService service) {
        this.service = service;
    }

    @PostMapping("/notificaciones")
    public ResponseEntity<Void> recibir(@RequestBody NotificacionDto dto) {
        System.out.println("Estoy");
        try {
            service.guardar(dto);
        } catch (DataIntegrityViolationException e) {
             if (e.getCause() instanceof ConstraintViolationException cve
                    &&  cve.getSQLState().equals("23505")) {
                System.out.println("Mensaje Duplicado");
                //log.info("Mensaje duplicado {}, se ignora", dto.getIdMensaje());
                return ResponseEntity.ok().build();
            }
            System.out.println("Error en el mensaje");
            //throw e;
        }
        return ResponseEntity.ok().build();
    }
}