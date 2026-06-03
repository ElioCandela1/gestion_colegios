package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grado")
public class Grados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grado")
    private Integer idGrado;

    @Column(name = "nombre_grado", nullable = false)
    private String nombreGrado;

    @Column(name = "nivel", nullable = false)
    private String nivel;
}