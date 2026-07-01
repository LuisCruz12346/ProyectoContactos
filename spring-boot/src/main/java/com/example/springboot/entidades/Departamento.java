package com.example.springboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@Entity
@Table(name = "departamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToMany
    @JoinTable(
        name = "departamento_empresa",
        joinColumns = @JoinColumn(name = "departamento_id"),
        inverseJoinColumns = @JoinColumn(name = "empresa_id")
    )
    @Builder.Default
    private List<Empresa> empresas = new ArrayList<>();

}