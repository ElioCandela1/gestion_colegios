package com.sistema_colegios.gestion_colegios.Model.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Grados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;
import com.sistema_colegios.gestion_colegios.Model.Entity.Secciones;
import com.sistema_colegios.gestion_colegios.Model.Repository.MatriculasRepository;

@Service
public class MatriculasService {

    @Autowired
    private MatriculasRepository matriculasRepository;
    @Autowired
    private EstudiantesService estudianteService;
    @Autowired
    private SeccionesService seccionService;

    public Matriculas matricularEstudiante(Estudiantes estudiante, 
                                           Secciones seccion, 
                                           Grados grado,
                                           Integer anioEscolar,
                                           LocalDate fechaMatricula,
                                           boolean estado) {
        // Verificar estudiante
        if(estudianteService.obtenerEstudiantePorDni(estudiante.getDni()) == null) {
            throw new RuntimeException("No existe el estudiante");
        } else {
            estudiante = estudianteService.obtenerEstudiantePorDni(estudiante.getDni());
        }

        // Verificar capacidad de la sección
        if(!seccionService.verificarCapacidad(seccion)) {
            throw new RuntimeException("No hay capacidad en la sección seleccionada.");
        }

        // Crear nueva matrícula
        Matriculas matricula = new Matriculas();
        matricula.setEstudiante(estudiante);
        matricula.setSeccion(seccion);
        matricula.setGrado(grado);
        matricula.setAnioEscolar(anioEscolar);
        matricula.setFechaMatricula(fechaMatricula);
        matricula.setEstado(estado);

        return matriculasRepository.save(matricula);
    }

    public List<Matriculas> listarEstudiantesMatriculados(){
        return matriculasRepository.findAll();
        
    }

}
