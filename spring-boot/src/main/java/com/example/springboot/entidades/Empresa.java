package com.example.springboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "empresa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", unique= true ,nullable = false)
    private String nombre;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "numEmpleados", nullable = false)
    @Builder.Default
    private int numEmpleados = 0;

    @Column(name = "pais", nullable = false)
    private String pais;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToMany(mappedBy = "empresas")
    @JsonIgnore // Por ahora para luego manejarlo con DTOs
    @Builder.Default
    private List<Departamento> departamentos = new ArrayList<>();


}