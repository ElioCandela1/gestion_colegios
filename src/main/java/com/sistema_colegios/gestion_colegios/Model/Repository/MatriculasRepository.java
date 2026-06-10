package com.sistema_colegios.gestion_colegios.Model.Repository;


import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;

@Repository
public interface MatriculasRepository extends JpaRepository<Matriculas, Integer> {

    Integer countBySeccion_IdSeccion(Integer idSeccion);

    boolean existsByEstudiante(Estudiantes estudiante);

    //@Query("SELECT m FROM Matriculas m WHERE m.estadoRegistro = true")
    List<Matriculas> findByEstadoRegistroTrue();

    Optional<Matriculas> findByEstudianteAndAnioEscolarAndEstadoRegistro(
        Estudiantes estudiante,
        Integer anioEscolar,
        boolean estadoRegistro);

     @Query("""
        SELECT m FROM Matriculas m
        WHERE m.estadoRegistro = true 
        AND(:anio IS NULL OR m.anioEscolar = :anio)
        AND (:grado IS NULL OR m.grado.idGrado = :grado)
        AND (:seccion IS NULL OR m.seccion.nombreSeccion = :seccion)
    """)
    Page<Matriculas> buscarConFiltros(
            @Param("anio") Integer anio,
            @Param("grado") Integer grado,
            @Param("seccion") String seccion,
            Pageable pageable
    );

     @Query("SELECT DISTINCT m.anioEscolar FROM Matriculas m ORDER BY m.anioEscolar ASC")
     List<Integer> findAnios();
    
}
