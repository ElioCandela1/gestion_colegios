package com.sistema_colegios.gestion_colegios.Model.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "pago")
public class Pagos extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "estado", nullable = false)
    private String estado;

    private String concepto;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiantes estudiante;
}