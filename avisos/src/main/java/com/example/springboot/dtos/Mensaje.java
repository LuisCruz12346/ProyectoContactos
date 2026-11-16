package com.example.springboot;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Mensaje{
    private String destino;
    private Long estado; //Resultado contenido;
}