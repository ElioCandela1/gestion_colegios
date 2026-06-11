package com.sistema_colegios.gestion_colegios.Model.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.MatriculasRepository;

@Service
public class MatriculasService implements filtros{

    @Autowired
    private MatriculasRepository matriculasRepository;
    @Autowired
    private EstudiantesService estudianteService;
    @Autowired
    private SeccionesService seccionService;
    @Autowired
    private AuditorAware<Usuarios> auditorAware;

    public String matricularEstudiante(String dniEstudiante, Matriculas matricula) {

        // Verificar estudiante
        Estudiantes estudiante = estudianteService.obtenerEstudiantePorDni(dniEstudiante);
        if (estudiante == null) {
            throw new RuntimeException("No existe el estudiante, debe crear uno en el modulo Gestion de Estudiantes");
        }

        // Asignar estudiante a la matricula
        matricula.setEstudiante(estudiante);

        // Verificar capacidad de la sección
        if (matricula.getSeccion() == null || !seccionService.verificarCapacidad(matricula.getSeccion())) {
            throw new RuntimeException("No hay capacidad en la sección seleccionada.");
        }

        // Verificar estudiante no matriculado
       Optional <Matriculas> matriculaExistenteOpt = obtenerEstudiantesMatriculadosPorAño(estudiante, matricula.getAnioEscolar());

        if (matriculaExistenteOpt.isPresent() && matriculaExistenteOpt.get().isEstadoRegistro()) {
            Matriculas matriculaExistente = matriculaExistenteOpt.get();
            matriculaExistente.setSeccion(matricula.getSeccion());
            matriculaExistente.setGrado(matricula.getGrado());
            matriculaExistente.setAnioEscolar(matricula.getAnioEscolar());
            matriculaExistente.setFechaMatricula(matricula.getFechaMatricula());
            matriculaExistente.setEstado(matricula.getEstado());
            /*if (!matriculaExistente.isEstadoRegistro()) {
                matriculaExistente.setEstadoRegistro(true);
                matriculasRepository.save(matriculaExistente);
                return "Matricula guardada exitosamente";
            }*/
            matriculasRepository.save(matriculaExistente);
        return "Matricula actualizada exitosamente";
        }

        

        // Si no existe, es nueva matrícula
        matriculasRepository.save(matricula);
        return "Matricula creada exitosamente";
    }

    public List<Matriculas> listarEstudiantesMatriculados() {
        List<Matriculas> matriculas = matriculasRepository.findByEstadoRegistroTrue();
        return matriculasRepository.findByEstadoRegistroTrue();

    }

    public Optional<Matriculas> obtenerMatriculaPorId(Integer id) {
        return matriculasRepository.findById(id);
    }

    public Matriculas eliminarMatricula(int id) {

        // 1. Buscar matricula por ID
        Matriculas matricula = matriculasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matricula no encontrada"));

        // 2. Modificar los campos
        Usuarios usuarioActual = auditorAware.getCurrentAuditor().orElse(null);
        matricula.softDelete(usuarioActual);

        // 3. Guardar nuevamente
        return matriculasRepository.save(matricula);
    }

    public Optional<Matriculas> obtenerEstudiantesMatriculadosPorAño(Estudiantes estudiante, Integer anioEscolar ){
        return matriculasRepository.findByEstudianteAndAnioEscolarAndEstadoRegistro(estudiante,
                anioEscolar, true);
                
    }

    @Override
    public Page<Matriculas> filtrar(Integer anio, Integer grado, String seccion, Pageable pageable) {
         return matriculasRepository.buscarConFiltros(
            anio,
            grado,
            seccion,
            pageable
    );
    }

    public List<Integer> listarAnios() {
            return matriculasRepository.findAnios();
    }
}
