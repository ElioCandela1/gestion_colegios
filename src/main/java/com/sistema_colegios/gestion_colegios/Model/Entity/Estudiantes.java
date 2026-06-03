package com.sistema_colegios.gestion_colegios.Model.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiantes extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    private String apellidos;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    private String direccion;

    private String telefono;

    private String correo;

    @ManyToOne
    @JoinColumn(name = "id_apoderado")
    private Apoderados apoderado;
}
