package com.sistema_colegios.gestion_colegios.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Grados;

@Repository
public interface GradosRepository extends JpaRepository<Grados, Integer> {

    Grados findByIdGrado(Integer idGrado);
}
