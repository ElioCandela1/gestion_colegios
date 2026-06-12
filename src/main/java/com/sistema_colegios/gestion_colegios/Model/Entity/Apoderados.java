package com.sistema_colegios.gestion_colegios.Model.Entity;

import com.sistema_colegios.gestion_colegios.Model.Service.Rol;
import com.sistema_colegios.gestion_colegios.Model.Service.UsuarioGenerable;

import jakarta.persistence.*;

@Entity
@Table(name = "apoderado")
public class Apoderados extends Persona implements UsuarioGenerable {

    
    @Override
    public Rol getRol() {
      
        return Rol.APODERADO;
    }

    
}