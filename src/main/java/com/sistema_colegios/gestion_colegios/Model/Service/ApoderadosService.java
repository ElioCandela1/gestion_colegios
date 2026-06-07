package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Repository.ApoderadosRepository;

@Service
public class ApoderadosService {

    @Autowired
    private ApoderadosRepository apoderadosRepository;
    public Apoderados guardarApoderados(Apoderados apoderados) {
        return apoderadosRepository.save(apoderados);
    }

    public Apoderados obtenerApoderadosPorPrimerApellido(String primerApellido) {
        return apoderadosRepository.findByPrimerApellido(primerApellido).orElse(null);
    }

    public Apoderados obtenerApoderadosPorSegundoApellido(String segundoApellido) {
        return apoderadosRepository.findBySegundoApellido(segundoApellido).orElse(null);
    }

    public Apoderados obtenerApoderadosPorNombre(String nombre) {
        return apoderadosRepository.findByNombre(nombre).orElse(null);
    }

    public Apoderados obtenerApoderadosPorDni(String dni) {
        return apoderadosRepository.findByDni(dni).orElse(null);
    }

    public void eliminarApoderados(Integer id) {
        apoderadosRepository.deleteById(id);
    }

    public Apoderados actualizarApoderados(Apoderados apoderados) {
        return apoderadosRepository.save(apoderados);
    }
    
}
