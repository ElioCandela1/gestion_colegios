package com.sistema_colegios.gestion_colegios.Model.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "matricula")
public class Matriculas extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer idMatricula;

    @Column(name = "fecha_matricula")
    private LocalDate fechaMatricula;

    private String estado;

    @Column(name = "anio_escolar")
    private Integer anioEscolar;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiantes estudiante;

    @ManyToOne
    @JoinColumn(name = "id_seccion")
    private Secciones seccion;
}