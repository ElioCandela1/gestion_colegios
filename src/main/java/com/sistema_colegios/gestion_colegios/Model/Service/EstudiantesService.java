package com.sistema_colegios.gestion_colegios.Model.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.EstudiantesRepository;
import com.sistema_colegios.gestion_colegios.Model.Security.AuditorAwareImpl;

@Service
public class EstudiantesService {
    @Autowired
    private EstudiantesRepository estudiantesRepository;
    @Autowired
    private AuditorAware<Usuarios> auditorAware;

    public Estudiantes guardarEstudiante(Estudiantes estudiante) {
        return estudiantesRepository.save(estudiante);
    }

    public Optional<Estudiantes> obtenerEstudiantePorId(int id){
        return estudiantesRepository.findById(id);
    }

    public Estudiantes obtenerEstudiantePorCodigo(String codigo) {
        return estudiantesRepository.findByCodigo(codigo).orElse(null);
    }

    public Estudiantes obtenerEstudiantePorNombre(String nombre) {
        return estudiantesRepository.findByNombre(nombre).orElse(null);
    }

    public Estudiantes obtenerEstudiantePorDni(String dni) {
        return estudiantesRepository.findByDni(dni).orElseThrow(()->new RuntimeException("No existe el estudiante con DNI: " + dni));
    }

    public Estudiantes eliminarEstudiante(int id) {
         // 1. Buscar el estudiante por ID
        Estudiantes estudiante = estudiantesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // 2. Modificar los campos
        Usuarios usuarioActual = auditorAware.getCurrentAuditor().orElse(null);
        estudiante.softDelete(usuarioActual);

        // 3. Guardar nuevamente
        return estudiantesRepository.save(estudiante);

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
