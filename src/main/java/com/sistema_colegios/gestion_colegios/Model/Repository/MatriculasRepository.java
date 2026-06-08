package com.sistema_colegios.gestion_colegios.Model.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;

@Repository
public interface MatriculasRepository extends JpaRepository<Matriculas, Integer> {

    Integer countBySeccion_IdSeccion(Integer idSeccion);
}
