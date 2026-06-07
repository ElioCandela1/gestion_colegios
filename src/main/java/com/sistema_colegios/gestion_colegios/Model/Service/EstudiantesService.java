package com.sistema_colegios.gestion_colegios.Model.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Repository.EstudiantesRepository;

@Service
public class EstudiantesService {
    @Autowired
    private EstudiantesRepository estudiantesRepository;

    public Estudiantes guardarEstudiante(Estudiantes estudiante) {
        return estudiantesRepository.save(estudiante);
    }

    public Estudiantes obtenerEstudiantePorCodigo(String codigo) {
        return estudiantesRepository.findByCodigo(codigo).orElse(null);
    }

    public Estudiantes obtenerEstudiantePorNombre(String nombre) {
        return estudiantesRepository.findByNombre(nombre).orElse(null);
    }

    public Estudiantes obtenerEstudiantePorDni(String dni) {
        return estudiantesRepository.findByDni(dni).orElse(null);
    }

    public void eliminarEstudiante(Integer id) {
        estudiantesRepository.deleteById(id);
    }

    public Estudiantes actualizarEstudiante(Estudiantes estudiante) {
        return estudiantesRepository.save(estudiante);
    }

    public List<Estudiantes> listarEstudiantes() {
        return estudiantesRepository.findAll();
    }

    public Estudiantes obtenerEstudiantePorApellido(String valor) {
        return estudiantesRepository.findByPrimerApellido(valor).orElse(null);
    }




}
