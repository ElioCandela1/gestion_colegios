package com.sistema_colegios.gestion_colegios.Model.Service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Repository.DocentesRepository;

@Service
public class DocentesService {

    @Autowired
    DocentesRepository docentesRepository;

    public @Nullable Object listarDocentes() {
        return docentesRepository.findAll();
    }

}
