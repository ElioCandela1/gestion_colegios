package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditoriaEntity {

    @ManyToOne
    @JoinColumn(name = "creado_por")
    @CreatedBy
    private Usuarios creadoPor;

    @ManyToOne
    @JoinColumn(name = "modificado_por")
    @LastModifiedBy
    private Usuarios modificadoPor;

    @ManyToOne
    @JoinColumn(name = "eliminado_por")
    private Usuarios eliminadoPor;

    @Column(name = "fecha_creacion")
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @LastModifiedDate
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "estado_registro")
    private boolean estadoRegistro = true;

    

    public AuditoriaEntity() {
    }

    public AuditoriaEntity(Usuarios creadoPor, Usuarios modificadoPor, Usuarios eliminadoPor,
            LocalDateTime fechaCreacion, LocalDateTime fechaModificacion, LocalDateTime fechaEliminacion, boolean estadoRegistro) {
        this.creadoPor = creadoPor;
        this.modificadoPor = modificadoPor;
        this.eliminadoPor = eliminadoPor;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.fechaEliminacion = fechaEliminacion;
        this.estadoRegistro = estadoRegistro;
    }

    public void softDelete(Usuarios usuarioActual){

        this.estadoRegistro = false;
        this.fechaEliminacion = LocalDateTime.now();
        this.eliminadoPor = usuarioActual;
    }

    // Getters y Setter
    public Usuarios getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuarios creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Usuarios getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuarios modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Usuarios getEliminadoPor() {
        return eliminadoPor;
    }

    public void setEliminadoPor(Usuarios eliminadoPor) {
        this.eliminadoPor = eliminadoPor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public boolean isEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(boolean estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    

    
}