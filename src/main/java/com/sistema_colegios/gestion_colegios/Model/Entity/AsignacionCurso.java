package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asignacion_curso")
public class AsignacionCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Integer idAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docentes docente;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Cursos curso;

    @ManyToOne
    @JoinColumn(name = "id_seccion")
    private Secciones seccion;
}