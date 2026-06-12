package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.UsuarioRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuarioRepository usuariosRepository;

    @Autowired
    private EmailService emailService;

    public void crearUsuario(UsuarioGenerable persona) {

        String username = persona.getCodigo();

        String password = generarPassword(
                persona.getNombre(),
                persona.getDni()
        );

        Usuarios usuario = new Usuarios();

        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRol(persona.getRol());
        usuario.setEstado(true);

        usuariosRepository.save(usuario);

        emailService.enviarCredenciales(
                persona.getCorreo(),
                username,
                password
        );
    }

    private String generarPassword(String nombre, String dni) {

        String primerNombre = nombre.split(" ")[0]; // toma solo la primera palabra
        String nombreFormateado =
                primerNombre.substring(0, 1).toUpperCase()
                + primerNombre.substring(1).toLowerCase();

        return "*" + nombreFormateado + dni;
    }
}
