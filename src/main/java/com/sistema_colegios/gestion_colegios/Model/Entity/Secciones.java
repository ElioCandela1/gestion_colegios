package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seccion")
public class Secciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seccion")
    private Integer idSeccion;

    @Column(name = "nombre_seccion")
    private String nombreSeccion;

    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "id_grado")
    private Grados grado;
}
