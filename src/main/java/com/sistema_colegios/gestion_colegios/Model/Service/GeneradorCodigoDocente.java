package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.stereotype.Component;

@Component
public class GeneradorCodigoDocente implements GenerarCodigoUsuario {

    private final DocentesService docentesService;

    public GeneradorCodigoDocente(DocentesService docentesService) {
        this.docentesService = docentesService;
    }

    @Override
    public String generarCodigo() {
        return "D" + docentesService.obtenerSiguienteId();
    }
}
