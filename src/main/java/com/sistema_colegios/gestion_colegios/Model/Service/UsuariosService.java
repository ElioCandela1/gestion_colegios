package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Persona;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Repository.UsuarioRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuarioRepository usuariosRepository;

    @Autowired
    private EmailService emailService;

    public void crearUsuario(UsuarioGenerable entidadGenerable, Persona persona ) {

        String username = entidadGenerable.getCodigo();

        String password = generarPassword(
                entidadGenerable.getNombre(),
                entidadGenerable.getDni()
        );

        Usuarios usuario = new Usuarios();

        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRol(entidadGenerable.getRol());
        usuario.setEstado(true);

        usuario.setPersona(persona);

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

    public Usuarios obtenerUsuarioPorIdPersona(int idPersona) {
    return usuariosRepository.findByPersonaId(idPersona)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
}

}
