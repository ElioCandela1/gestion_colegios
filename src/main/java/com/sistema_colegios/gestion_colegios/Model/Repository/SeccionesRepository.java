package com.sistema_colegios.gestion_colegios.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Grados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Secciones;

@Repository
public interface SeccionesRepository extends JpaRepository<Secciones, Integer> {

    Secciones findByNombreSeccionAndGrado(String nombreSeccion, Grados grado);


}
