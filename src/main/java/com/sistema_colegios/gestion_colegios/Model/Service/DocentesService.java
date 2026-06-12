package com.sistema_colegios.gestion_colegios.Model.Service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Docentes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.DocentesRepository;

@Service
public class DocentesService {

    @Autowired
    DocentesRepository docentesRepository;
    @Autowired
    private AuditorAware<Usuarios> auditorAware;
    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    private EmailService emailService;

    public String guardarDocentes(Docentes docente) {

        Docentes docenteExistente = null;

        // Buscar si existe el docente por DNI
        try {
            docenteExistente = obtenerDocentePorDni(docente.getDni());
        } catch (Exception e) {
            // Ignorar excepción si no existe
        }

        if (docenteExistente != null) {
            if (docenteExistente.isEstadoRegistro()) {
                // Actualizar datos del docente
                try {
                    actualizarDocentes(docente);
                } catch (Exception e) {
                    return e.getMessage();
                }
                return "Docente Actualizado Correctamente";
            } else {
                // Reactivar docente eliminado
                docenteExistente.setEstadoRegistro(true);
                copiarDatos(docente, docenteExistente);
                docentesRepository.save(docenteExistente);

                Usuarios user = usuariosService.obtenerUsuarioPorIdPersona(docenteExistente.getId());
                emailService.enviarCredenciales(docenteExistente.getCorreo(), user.getUsername(), null);

                return "Docente Reactivado Exitosamente";
            }
        }

        // Guardar nuevo docente
        docentesRepository.save(docente);

        Usuarios usuario = new Usuarios();
        usuariosService.crearUsuario(docente, docente);
        usuario.setPersona(docente);

        return "Docente Guardado Con Éxito";
    }

    public Docentes obtenerDocentePorId(int id) {
        return docentesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con id: " + id));
    }

    public Docentes obtenerDocentePorDni(String dni) {
        return docentesRepository.findByDni(dni).orElse(null);
    }

    public Page<Docentes> obtenerDocentesPorDniPage(String dni, int numeroPagina) {

        PageRequest pageable = PageRequest.of(numeroPagina, 10);

        return docentesRepository.findByDniPage(dni, pageable);
    }

    public String eliminarDocentes(Integer id) {

        // Buscar docente por id
        Docentes docente = docentesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente No Encontrado"));

        // Soft Delete
        Usuarios usuarioActual = auditorAware.getCurrentAuditor().orElse(null);
        docente.softDelete(usuarioActual);

        // Guardar nuevamente
        docentesRepository.save(docente);

        return "Docente Eliminado Con Éxito";
    }

    public Docentes actualizarDocentes(Docentes docente) {

        Docentes docenteAnterior = obtenerDocentePorDni(docente.getDni());

        if (docenteAnterior == null) {
            throw new RuntimeException("Docente no encontrado");
        }

        // Actualizar los atributos
        copiarDatos(docente, docenteAnterior);

        return docentesRepository.save(docenteAnterior);
    }

    public List<Docentes> listarDocentes() {
        return docentesRepository.findAll();
    }

    public int obtenerSiguienteId() {
        return (int) docentesRepository.count() + 1;
    }

    public Page<Docentes> listarDocentesActivos(int numeroPagina) {
        Pageable pageable = PageRequest.of(numeroPagina, 10);
        return docentesRepository.findAllPage(pageable);
    }

    private void copiarDatos(Docentes origen, Docentes destino) {
        destino.setNombre(origen.getNombre());
        destino.setPrimerApellido(origen.getPrimerApellido());
        destino.setSegundoApellido(origen.getSegundoApellido());
        destino.setDni(origen.getDni());
        destino.setEspecialidad(origen.getEspecialidad());
        destino.setTelefono(origen.getTelefono());
        destino.setCorreo(origen.getCorreo());
    }

}
