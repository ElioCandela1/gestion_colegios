package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;

public interface filtros {
    Page<Matriculas> filtrar(
        Integer anio,
        Integer grado,
        String seccion,
        Pageable pageable);
}
