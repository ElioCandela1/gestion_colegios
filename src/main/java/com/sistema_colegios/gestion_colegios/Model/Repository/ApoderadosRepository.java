package com.sistema_colegios.gestion_colegios.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;

@Repository
public interface ApoderadosRepository extends JpaRepository<Apoderados, Integer> {

    Optional<Apoderados> findByDni(String dni);
    Optional<Apoderados> findByNombre(String nombre);
    Optional<Apoderados> findByPrimerApellido(String primerApellido);
    Optional<Apoderados> findBySegundoApellido(String segundoApellido);

}
