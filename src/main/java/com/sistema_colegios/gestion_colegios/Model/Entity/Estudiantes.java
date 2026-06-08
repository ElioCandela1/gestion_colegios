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

    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;
    
    private String segundoApellido;

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

    public Estudiantes() {
    }

    public Estudiantes(String codigo, String nombre, String primerApellido, String dni, LocalDate fechaNacimiento,
            Apoderados apoderado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.apoderado = apoderado;
    }

    

    public Estudiantes(Integer idEstudiante, String codigo, String nombre, String primerApellido,
            String segundoApellido, String dni, LocalDate fechaNacimiento, String direccion, String telefono,
            String correo, Apoderados apoderado) {
        this.idEstudiante = idEstudiante;
        this.codigo = codigo;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.apoderado = apoderado;
    }

    //Getters y Setters

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni != null && !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe contener exactamente 8 dígitos.");
        }
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo != null && !correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }
        this.correo = correo;
    }

    public Apoderados getApoderado() {
        return apoderado;
    }

    public void setApoderado(Apoderados apoderado) {
        this.apoderado = apoderado;
    }

    
}
