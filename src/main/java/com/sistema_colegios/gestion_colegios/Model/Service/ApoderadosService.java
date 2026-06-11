package com.sistema_colegios.gestion_colegios.Model.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.ApoderadosRepository;

@Service
public class ApoderadosService {

    @Autowired
    private ApoderadosRepository apoderadosRepository;
    @Autowired
    private AuditorAware<Usuarios> auditorAware;

    public String guardarApoderados(Apoderados apoderado) {

        Apoderados apoderadoExistente = null;

        // Buscar si existe el apoderado
        try {
            apoderadoExistente = obtenerApoderadosPorDni(apoderado.getDni());
        } catch (Exception e) {
        }

        // En caso se exista el apoderado se verifica si estaba eliminado o activo
        if (apoderadoExistente != null) {
            if (apoderadoExistente.isEstadoRegistro()) {

                // Se actualizan los datos del apoderado
                try {
                    actualizarApoderados(apoderado);
                } catch (Exception e) {
                    return e.getMessage();
                }

                return "Apoderado Actualizado Correctamente";

            } else {

                // Existe pero estaba eliminado, reactivar
                apoderadoExistente.setEstadoRegistro(true);
                copiarDatos(apoderado, apoderadoExistente);
                /*apoderadoExistente.setNombre(apoderado.getNombre());
                apoderadoExistente.setPrimerApellido(apoderado.getPrimerApellido());
                apoderadoExistente.setSegundoApellido(apoderado.getSegundoApellido());
                apoderadoExistente.setDni(apoderado.getDni());
                apoderadoExistente.setTelefono(apoderado.getTelefono());
                apoderadoExistente.setCorreo(apoderado.getCorreo());
                apoderadoExistente.setParentesco(apoderado.getParentesco());*/

                apoderadosRepository.save(apoderadoExistente);

                return "Apoderado Reactivado Exitosamente";
            }

        }

        // No existe el apoderado, guardar uno nuevo
        apoderadosRepository.save(apoderado);

        return "Apoderado Guardado Con Exito";
    }

    public Apoderados obtenerApoderadosPorId(int id){
        return apoderadosRepository.findById(id).orElseThrow(null);
    }

    public Apoderados obtenerApoderadosPorDni(String dni) {
        return apoderadosRepository.findByDni(dni).orElse(null);
    }

    public Page<Apoderados> obtenerApoderadosPorDniPage(String dni, int numeroPagina) {
        
        Pageable pageable = PageRequest.of(numeroPagina, 10);

        return apoderadosRepository.findByDniPage(dni, pageable);
    }

    public String eliminarApoderados(Integer id) {

        // Buscar apoderado por id
        Apoderados apoderado = apoderadosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apoderado No Encontrado"));

        // Soft Delete
        Usuarios usuarioActual = auditorAware.getCurrentAuditor().orElse(null);
        apoderado.softDelete(usuarioActual);

        // Guardar nuevamente
        apoderadosRepository.save(apoderado);

        return "Apoderado Eliminado Con Exito";
    }

    public Apoderados actualizarApoderados(Apoderados apoderados) {
        
        Apoderados apoderadoAnterior = obtenerApoderadosPorDni(apoderados.getDni());

        if (apoderadoAnterior == null) {
            throw new RuntimeException("Apoderado no encontrado");
        }

        // Actualizar los atributos
        copiarDatos(apoderados, apoderadoAnterior);

        return apoderadosRepository.save(apoderadoAnterior);
    }

    public List<Apoderados> listarApoderados() {
        return apoderadosRepository.findAll();
    }

    public int obtenerSiguienteId(){
        return (int) apoderadosRepository.count()+1;
    }

    public Page<Apoderados> listarApoderadosActivos(int numeroPagina){
        Pageable pageable = PageRequest.of(numeroPagina, 10);
        return apoderadosRepository.findAllPage(pageable);
    }

    private void copiarDatos(Apoderados origen, Apoderados destino) {
        destino.setNombre(origen.getNombre());
        destino.setPrimerApellido(origen.getPrimerApellido());
        destino.setSegundoApellido(origen.getSegundoApellido());
        destino.setDni(origen.getDni());
        destino.setTelefono(origen.getTelefono());
        destino.setCorreo(origen.getCorreo());
        //destino.setParentesco(origen.getParentesco());
    }
}
