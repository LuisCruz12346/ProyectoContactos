package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private ConfigureServices configureServices;
    @GetMapping("/v2/responderEstado")
    public String agregarDep() {
        return  configureServices.regresarPrueba();
    }

}