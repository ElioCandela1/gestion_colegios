package com.sistema_colegios.gestion_colegios.Model.Repository;

import org.springframework.stereotype.Repository;

import com.sistema_colegios.gestion_colegios.Model.Entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface  AdministradorRepository extends JpaRepository<Administrador, Integer> {


}
