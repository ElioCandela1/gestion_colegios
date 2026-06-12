package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.stereotype.Component;

@Component
public class GenerarCodigoApoderado implements GenerarCodigoUsuario {


        private ApoderadosService apoderadosService;

    public GenerarCodigoApoderado(ApoderadosService apoderadosService) {
            this.apoderadosService = apoderadosService;
        }

    @Override
    public String generarCodigo() {
        
        return "P" + apoderadosService.obtenerSiguienteId();
    }

}
