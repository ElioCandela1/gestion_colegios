package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "docente")
public class Docentes extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Integer idDocente;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    private String apellidos;

    @Column(unique = true, nullable = false)
    private String dni;

    private String especialidad;

    private String telefono;

    private String correo;
}