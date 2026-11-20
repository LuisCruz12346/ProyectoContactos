package com.example.springboot.repository;

import com.example.springboot.model.EstadoNotificacion;
import com.example.springboot.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    @Modifying
    @Transactional
    @Query("update Notificacion n set n.estado = :estado where n.idMensaje = :id")
    int cambiarEstado(@Param("id") String id, @Param("estado") EstadoNotificacion estado);

    @Modifying
    @Transactional
    @Query("""
        update Notificacion n
           set n.estado = 'FALLIDO'
         where n.estado = 'PENDIENTE'
           and n.fechaCreacion < :limite
        """)
    int marcarVencidos(@Param("limite") Instant limite);

    List<Notificacion> findTop50ByEstadoOrderByFechaCreacionAsc(EstadoNotificacion estado);
}