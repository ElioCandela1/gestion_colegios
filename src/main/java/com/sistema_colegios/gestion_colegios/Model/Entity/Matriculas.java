package com.sistema_colegios.gestion_colegios.Model.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "matricula")
public class Matriculas extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer idMatricula;

    @NotNull(message = "La fecha de matricula es obligatoria")
    @Column(name = "fecha_matricula")
    private LocalDate fechaMatricula;

    private boolean estado =true;

    @Column(name = "anio_escolar")
    private Integer anioEscolar;

    @NotNull(message = "Debe ingresar un estudiante")
    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiantes estudiante;

    @NotNull(message = "Debe ingresar una sección")
    @ManyToOne
    @JoinColumn(name = "id_seccion")
    private Secciones seccion;

    @NotNull(message = "Debe ingresar un grado")
    @ManyToOne
    @JoinColumn(name = "id_grado")
    private Grados grado;

    public Matriculas() {
    }

    public Matriculas(LocalDate fechaMatricula, boolean estado, Integer anioEscolar, Estudiantes estudiante,
            Secciones seccion, Grados grado) {
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
        this.anioEscolar = anioEscolar;
        this.estudiante = estudiante;
        this.seccion = seccion;
        this.grado = grado;
    }

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Integer getAnioEscolar() {
        return anioEscolar;
    }

    public void setAnioEscolar(Integer anioEscolar) {
        this.anioEscolar = anioEscolar;
    }

    public Estudiantes getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiantes estudiante) {
        this.estudiante = estudiante;
    }

    public Secciones getSeccion() {
        return seccion;
    }

    public void setSeccion(Secciones seccion) {
        this.seccion = seccion;
    }

    public Grados getGrado() {
        return grado;
    }

    public void setGrado(Grados grado) {
        this.grado = grado;
    }
 
}