package com.sistema_colegios.gestion_colegios.Model.Entity;

import com.sistema_colegios.gestion_colegios.Model.Service.Rol;
import com.sistema_colegios.gestion_colegios.Model.Service.UsuarioGenerable;

import jakarta.persistence.*;

@Entity
@Table(name = "docente")
public class Docentes extends Persona implements UsuarioGenerable {

    private String especialidad;

    public Docentes(){
        
    }

    public Docentes(String especialidad) {
        this.especialidad = especialidad;
    }

    public Docentes(int id, String codigo, String nombre, String primerApellido, String segundoApellido, String dni,
            String telefono, String correo, String especialidad) {
        super(id, codigo, nombre, primerApellido, segundoApellido, dni, telefono, correo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public Rol getRol() {
        
        return Rol.DOCENTE;
    }

    
    
}