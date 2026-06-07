package com.sistema_colegios.gestion_colegios.Model.Service;

public class GeneradorCodigoUsuarioService {

    public String generarCodigo() {
        // Lógica para generar un código único para el estudiante
        String codigo = "ESTU" + System.currentTimeMillis(); // Ejemplo simple usando timestamp
        return codigo;
    }

}
