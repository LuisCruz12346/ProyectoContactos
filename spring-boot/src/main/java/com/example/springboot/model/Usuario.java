package com.example.springboot;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.Builder;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Data 
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario{
    String nombre;
    int edad;
    String telefono;
    String email;
    String direccion;

}


