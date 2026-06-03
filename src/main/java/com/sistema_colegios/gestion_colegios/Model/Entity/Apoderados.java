package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "apoderado")
public class Apoderados extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apoderado")
    private Integer idApoderado;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    private String apellidos;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "parentesco", nullable = false)
    private String parentesco;
}