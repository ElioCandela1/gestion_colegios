package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditoriaEntity {

    @ManyToOne
    @JoinColumn(name = "creado_por")
    private Usuarios creadoPor;

    @ManyToOne
    @JoinColumn(name = "modificado_por")
    private Usuarios modificadoPor;

    @ManyToOne
    @JoinColumn(name = "eliminado_por")
    private Usuarios eliminadoPor;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "estado_registro")
    private String estadoRegistro;
}