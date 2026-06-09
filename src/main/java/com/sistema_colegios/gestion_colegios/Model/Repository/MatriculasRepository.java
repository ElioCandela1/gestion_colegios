package com.sistema_colegios.gestion_colegios.Model.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;

@Repository
public interface MatriculasRepository extends JpaRepository<Matriculas, Integer> {

    Integer countBySeccion_IdSeccion(Integer idSeccion);
    
}
