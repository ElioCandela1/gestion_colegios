package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nota")
public class Notas extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer idNota;

    private String nota1;

    private String nota2;

    private String nota3;

    private String promedio;

    private Integer bimestre;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiantes estudiante;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Cursos curso;
}