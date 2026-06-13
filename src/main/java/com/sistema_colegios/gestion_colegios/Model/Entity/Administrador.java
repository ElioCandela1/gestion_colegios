package com.sistema_colegios.gestion_colegios.Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador extends Persona {

    private String cargo;

    private String area;



    public Administrador(String cargo, String area) {this.cargo = cargo; this.area = area; }

    public Administrador(int id, String codigo, String nombre, String primerApellido, String segundoApellido,
            String dni, String telefono, String correo, String cargo, String area) {
        super(id, codigo, nombre, primerApellido, segundoApellido, dni, telefono, correo);
        this.cargo = cargo;
        this.area = area;
    }

    public Administrador() {    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}