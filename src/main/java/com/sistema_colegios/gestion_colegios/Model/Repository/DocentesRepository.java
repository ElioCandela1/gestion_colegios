package com.sistema_colegios.gestion_colegios.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Docentes;

@Repository
public interface DocentesRepository extends JpaRepository<Docentes, Integer>{

}
