package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Grados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Secciones;
import com.sistema_colegios.gestion_colegios.Model.Repository.MatriculasRepository;
import com.sistema_colegios.gestion_colegios.Model.Repository.SeccionesRepository;

@Service
public class SeccionesService {

    @Autowired
    private MatriculasRepository matriculasRepository;
    @Autowired
    private SeccionesRepository seccionesRepository;

    public boolean verificarCapacidad(Secciones seccion) {
        
        if(matriculasRepository.countBySeccion_IdSeccion(seccion.getIdSeccion()) >= seccion.getCapacidad()) {
            return false; // No hay capacidad
        }
        return true; // Retorna true si hay capacidad, false si no
    }

    public Secciones obtenerSeccionPorNombreYGrado(String nombreSeccion, Grados grado) {
        return seccionesRepository.findByNombreSeccionAndGrado(nombreSeccion, grado);
    }

}
