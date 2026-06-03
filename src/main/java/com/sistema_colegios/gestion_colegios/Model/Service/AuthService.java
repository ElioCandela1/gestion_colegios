package com.sistema_colegios.gestion_colegios.Model.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema_colegios.gestion_colegios.Model.Entity.Usuario;
import com.sistema_colegios.gestion_colegios.Model.Repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario login(String username, String password, HttpSession session) {

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getPassword().equals(password)) {
            return null;
        }

        if (!usuario.getEstado()) {
            return null;
        }

        session.setAttribute("usuarioLogeado", usuario);
        return usuario;
    }
}