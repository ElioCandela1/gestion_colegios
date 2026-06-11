package com.sistema_colegios.gestion_colegios.Model.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;

@Repository
public interface ApoderadosRepository extends JpaRepository<Apoderados, Integer> {

    Optional<Apoderados> findByDni(String dni);

    @Query("""
                SELECT a FROM Apoderados a
                WHERE a.estadoRegistro = true
            """)
    Page<Apoderados> findAllPage(Pageable pageable);

    // Búsqueda por DNI con paginación
    @Query("SELECT a FROM Apoderados a WHERE a.estadoRegistro = true AND a.dni = :dni")
    Page<Apoderados> findByDniPage(@Param("dni") String dni, Pageable pageable);
    
    
}
