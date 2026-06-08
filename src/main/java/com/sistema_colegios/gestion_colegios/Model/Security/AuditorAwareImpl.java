package com.sistema_colegios.gestion_colegios.Model.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;

import jakarta.servlet.http.HttpSession;

@Component
public class AuditorAwareImpl implements AuditorAware<Usuarios> {

    @Autowired
    private HttpSession session;

    @Override
    public Optional<Usuarios> getCurrentAuditor() {
        Usuarios usuario = (Usuarios) session.getAttribute("usuarioLogeado");
        return Optional.ofNullable(usuario);
    }
}

