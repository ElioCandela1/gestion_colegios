package com.sistema_colegios.gestion_colegios.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistema_colegios.gestion_colegios.Model.Entity.Administrador;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.AdministradorRepository;
import com.sistema_colegios.gestion_colegios.Model.Repository.UsuarioRepository;
import com.sistema_colegios.gestion_colegios.Model.Service.Rol;

@Configuration
public class AdminInitializer {

    /*@Bean
    CommandLineRunner crearAdmin(
            AdministradorRepository administradorRepository,
            UsuarioRepository usuarioRepository) {

        return args -> {

            if (usuarioRepository.existsByUsername("admin")) {
                return;
            }

            // Crear usuario SYSTEM
            if (!usuarioRepository.existsByUsername("SYSTEM")) {

                Usuarios system = new Usuarios();
                system.setUsername("SYSTEM");
                system.setPassword("SYSTEM");
                system.setRol(Rol.SYSTEM);
                system.setEstado(true);

                usuarioRepository.save(system);
            }


            Administrador admin = new Administrador();
            admin.setDni("00000000");
            admin.setNombre("Administrador");
            admin.setPrimerApellido("Principal");
            admin.setCorreo("eliocandela8@gmail.com");

            administradorRepository.save(admin);

            Usuarios usuario = new Usuarios();
            usuario.setUsername("admin");
            usuario.setPassword("123"); 
            usuario.setRol(Rol.ADMIN);
            usuario.setPersona(admin);

            usuarioRepository.save(usuario);

            System.out.println("Administrador inicial creado correctamente.");
        };
    }*/
}