package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.stereotype.Component;

@Component
public class GeneradorCodigoEstudiante implements GenerarCodigoUsuario {

    private final EstudiantesService estudiantesService;

    public GeneradorCodigoEstudiante(EstudiantesService estudiantesService) {
        this.estudiantesService = estudiantesService;
    }

    @Override
    public String generarCodigo() {
        return "E" + estudiantesService.obtenerSiguienteId();
    }

}
