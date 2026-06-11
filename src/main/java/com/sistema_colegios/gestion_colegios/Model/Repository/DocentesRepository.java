package com.sistema_colegios.gestion_colegios.Model.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Docentes;

@Repository
public interface DocentesRepository extends JpaRepository<Docentes, Integer> {

    Optional<Docentes> findByDni(String dni);

    @Query("""
           SELECT d FROM Docentes d
           WHERE d.estadoRegistro = true
           """)
    Page<Docentes> findAllPage(Pageable pageable);

    @Query("SELECT d FROM Docentes d WHERE d.estadoRegistro = true AND d.dni = :dni")
    Page<Docentes> findByDniPage(@Param("dni") String dni, Pageable pageable);
}
