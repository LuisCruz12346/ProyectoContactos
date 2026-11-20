package com.example.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

@Entity
@Table(
    name = "notificacion",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_notificacion_id_mensaje",
        columnNames = "id_mensaje_cobro"
    )
)
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_mensaje_cobro", nullable = false, unique =  true)
    private String idMensaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoNotificacion estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    // Requerido por JPA
    protected Notificacion() {
    }

    public Notificacion(String idMensaje, EstadoNotificacion estado) {
        this.idMensaje = idMensaje;
        this.estado = estado;
    }

    @PrePersist
    void antesDeGuardar() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }
}