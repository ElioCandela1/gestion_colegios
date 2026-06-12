package com.sistema_colegios.gestion_colegios.Model.Entity;


import com.sistema_colegios.gestion_colegios.Model.Service.Rol;
import com.sistema_colegios.gestion_colegios.Model.Service.UsuarioGenerable;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiantes extends Persona implements UsuarioGenerable {

    @ManyToOne
    @JoinColumn(name = "id_apoderado", nullable = false)
    private Apoderados apoderado;

    public Estudiantes(){
        
    }

    public Estudiantes(Apoderados apoderado) {
        this.apoderado = apoderado;
    }

    public Estudiantes(int id, String codigo, String nombre, String primerApellido, String segundoApellido, String dni,
            String telefono, String correo, Apoderados apoderado) {
        super(id, codigo, nombre, primerApellido, segundoApellido, dni, telefono, correo);
        this.apoderado = apoderado;
    }

    public Apoderados getApoderado() {
        return apoderado;
    }

    public void setApoderado(Apoderados apoderado) {
        this.apoderado = apoderado;
    }

    @Override
    public Rol getRol() {

        return Rol.ESTUDIANTE;
    }

}
