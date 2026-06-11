package com.sistema_colegios.gestion_colegios.Model.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;

@Repository
public interface EstudiantesRepository extends JpaRepository<Estudiantes, Integer> {

    Optional <Estudiantes> findByCodigo(String codigo);
    Optional<Estudiantes> findByNombre(String nombre);
    Optional<Estudiantes> findByPrimerApellido(String primerApellido);
    Optional<Estudiantes> findByDni(String dni);

    @Query("""
                SELECT e FROM Estudiantes e
                WHERE e.estadoRegistro = true
            """)
    Page<Estudiantes> findAllPage(Pageable pageable);

    // Búsqueda por DNI con paginación
    @Query("SELECT e FROM Estudiantes e WHERE e.estadoRegistro = true AND e.dni = :dni")
    Page<Estudiantes> findByDniPage(@Param("dni") String dni, Pageable pageable);
            

}
