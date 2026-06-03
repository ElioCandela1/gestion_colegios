package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    private String descripcion;
}
