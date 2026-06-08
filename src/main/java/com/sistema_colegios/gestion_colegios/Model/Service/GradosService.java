package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Grados;
import com.sistema_colegios.gestion_colegios.Model.Repository.GradosRepository;

@Service
public class GradosService {
    @Autowired
    private GradosRepository gradosRepository;
    public Grados obtenerGradoPorId(Integer idGrado) {
        return gradosRepository.findByIdGrado(idGrado);
    }

}
