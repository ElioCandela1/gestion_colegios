package com.sistema_colegios.gestion_colegios.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;

@Repository
public interface EstudiantesRepository extends JpaRepository<Estudiantes, Integer> {

    Optional <Estudiantes> findByCodigo(String codigo);
    Optional<Estudiantes> findByNombre(String nombre);
    Optional<Estudiantes> findByPrimerApellido(String primerApellido);
    Optional<Estudiantes> findByDni(String dni);

}
