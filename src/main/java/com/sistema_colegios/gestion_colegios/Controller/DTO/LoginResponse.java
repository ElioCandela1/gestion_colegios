package com.sistema_colegios.gestion_colegios.Controller.DTO;


public class LoginResponse {

    private Integer id;
    private String username;
    private String rol;

    public LoginResponse(
            Integer id,
            String username,
            String rol) {

        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    // getters

    
}