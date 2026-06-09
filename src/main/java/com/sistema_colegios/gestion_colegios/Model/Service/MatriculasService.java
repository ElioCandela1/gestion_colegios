package com.sistema_colegios.gestion_colegios.Model.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Matriculas matricularEstudiante(String dniEstudiante, Matriculas matricula) {
    
    // Verificar estudiante
    Estudiantes estudiante = estudianteService.obtenerEstudiantePorDni(dniEstudiante);
    if (estudiante == null) {
        throw new RuntimeException("No existe el estudiante");
    }
    matricula.setEstudiante(estudiante);

    // Verificar capacidad de la sección
    if (matricula.getSeccion() == null || !seccionService.verificarCapacidad(matricula.getSeccion())) {
        throw new RuntimeException("No hay capacidad en la sección seleccionada.");
    }

    // Si viene con id, intentamos actualizar
    if (matricula.getIdMatricula() != null) {
        Matriculas matriculaExistente = matriculasRepository.findById(matricula.getIdMatricula())
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

        // Copiar cambios
        matriculaExistente.setEstudiante(matricula.getEstudiante());
        matriculaExistente.setSeccion(matricula.getSeccion());
        matriculaExistente.setGrado(matricula.getGrado());
        matriculaExistente.setAnioEscolar(matricula.getAnioEscolar());
        matriculaExistente.setFechaMatricula(matricula.getFechaMatricula());
        matriculaExistente.setEstado(matricula.getEstado());

        return matriculasRepository.save(matriculaExistente);
    }

    // Si no tiene id, es nueva matrícula
    return matriculasRepository.save(matricula);
}

    public List<Matriculas> listarEstudiantesMatriculados() {
        return matriculasRepository.findAll();

    }

    public Optional<Matriculas> obtenerMatriculaPorId(Integer id) {
        return matriculasRepository.findById(id);
    }

}
