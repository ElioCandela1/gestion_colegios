package com.sistema_colegios.gestion_colegios.Model.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.EstudiantesRepository;

@Service
public class EstudiantesService {
    
    @Autowired
    private EstudiantesRepository estudiantesRepository;
    @Autowired
    private AuditorAware<Usuarios> auditorAware;
    @Autowired
    private ApoderadosService apoderadosService;
    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    private EmailService emailService;

 
    public String guardarEstudiante(Estudiantes estudiante) {
        
        Estudiantes estudianteExistente = null;
        //Buscar si existe estudiante en la DB
         try {
            estudianteExistente = obtenerEstudiantePorDni(estudiante.getDni());
        } catch (Exception e) {
        }
        
        if (estudianteExistente != null) {

            if (estudianteExistente.isEstadoRegistro()) {
                //Ya existe el estudiante y estadoRegistro está activo
                try {
                    actualizarEstudiante(estudiante);
                } catch (Exception e) {
                    return e.getMessage();
                }
                return "Estudiante Actualizado correctamente";
            } else {
                // Existe pero estaba eliminado, reactivar
            estudianteExistente.setEstadoRegistro(true);
            estudianteExistente.setNombre(estudiante.getNombre());
            estudianteExistente.setPrimerApellido(estudiante.getPrimerApellido());
            estudianteExistente.setSegundoApellido(estudiante.getSegundoApellido());
            estudianteExistente.setDireccion(estudiante.getDireccion());
            estudianteExistente.setTelefono(estudiante.getTelefono());
            estudianteExistente.setCorreo(estudiante.getCorreo());

            Apoderados apoderado = apoderadosService.obtenerApoderadosPorId(estudiante.getApoderado().getId()); // Buscar apoderado en la DB
            estudianteExistente.setApoderado(apoderado);

            estudiantesRepository.save(estudianteExistente);
            Usuarios user = usuariosService.obtenerUsuarioPorIdPersona(estudianteExistente.getId());
            emailService.enviarCredenciales(estudianteExistente.getCorreo(), user.getUsername() , null);

            return "Estudiante reactivado exitosamente";
            }
        } 

        // No existe el estudiante, guardar uno nuevo
        estudiantesRepository.save(estudiante);
        Usuarios usuario = new Usuarios();
        usuariosService.crearUsuario(estudiante, estudiante);
        usuario.setPersona(estudiante);
        
        
        return "Estudiante guardado exitosamente";
    }

    public Estudiantes obtenerEstudiantePorId(int id) {
        return estudiantesRepository.findById(id).orElseThrow(null);
    }

    public Estudiantes obtenerEstudiantePorCodigo(String codigo) {
        return estudiantesRepository.findByCodigo(codigo).orElse(null);
    }

    public Estudiantes obtenerEstudiantePorNombre(String nombre) {
        return estudiantesRepository.findByNombre(nombre).orElse(null);
    }

    public Estudiantes obtenerEstudiantePorDni(String dni) {
        return estudiantesRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("No existe el estudiante con DNI: " + dni));
    }
   
    public Page <Estudiantes> obtenerEstudiantePorDniPage(String dni, int numeroPagina) {
       Pageable pageable = PageRequest.of(numeroPagina,10);
       return estudiantesRepository.findByDniPage(dni, pageable);
    }

    public String eliminarEstudiante(int id) {
        // 1. Buscar el estudiante por ID
        Estudiantes estudiante = estudiantesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // 2. Modificar los campos
        Usuarios usuarioActual = auditorAware.getCurrentAuditor().orElse(null);
        estudiante.softDelete(usuarioActual);

        // 3. Guardar nuevamente
        estudiantesRepository.save(estudiante);

        return "Estudiante eliminado con exito";

    }

    public Estudiantes actualizarEstudiante(Estudiantes estudiante) {
    
    Estudiantes estudianteAnt = obtenerEstudiantePorDni(estudiante.getDni());

    if (estudianteAnt == null) {
        throw new RuntimeException("Estudiante no encontrado");
    }

    // Actualizar los campos necesarios
    estudianteAnt.setNombre(estudiante.getNombre());
    estudianteAnt.setPrimerApellido(estudiante.getPrimerApellido());
    estudianteAnt.setSegundoApellido(estudiante.getSegundoApellido());
    estudianteAnt.setDni(estudiante.getDni());
    estudianteAnt.setFechaNacimiento(estudiante.getFechaNacimiento());
    estudianteAnt.setDireccion(estudiante.getDireccion());
    estudianteAnt.setTelefono(estudiante.getTelefono());
    estudianteAnt.setCorreo(estudiante.getCorreo());
    
    Apoderados apoderado = apoderadosService.obtenerApoderadosPorId(estudiante.getApoderado().getId()); // Buscar apoderado en la DB
    estudianteAnt.setApoderado(apoderado);

    return estudiantesRepository.save(estudianteAnt);
}

    public List<Estudiantes> listarEstudiantes() {
        return estudiantesRepository.findAll();
    }

    public Estudiantes obtenerEstudiantePorApellido(String valor) {
        return estudiantesRepository.findByPrimerApellido(valor).orElse(null);
    }

    public int obtenerSiguienteId() {
        int siguienteId = (int) estudiantesRepository.count() + 1;
        return siguienteId;
    }

    public Page<Estudiantes> listarEstudiantesActivos(int numeroPagina) {
    Pageable pageable = PageRequest.of(numeroPagina, 10); // página, tamaño
    return estudiantesRepository.findAllPage(pageable);
}

}
