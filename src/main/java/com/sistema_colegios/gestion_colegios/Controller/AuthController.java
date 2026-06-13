package com.sistema_colegios.gestion_colegios.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.AuthService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String username,
            @RequestParam String password,   
            Model model,
            HttpSession session) {

        Usuarios usuario = authService.login(username, password, session);

        if (usuario == null) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "index"; // vuelve al formulario
        }

        session.setAttribute("usuarioLogeado", usuario);
        // Redirige a otra página después del login exitoso
        usuario.setUltimoAcceso(LocalDateTime.now());
        return "redirect:/inicio";
    }

    @GetMapping("/logout")
    public String CerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/"; // Redirige al login después de cerrar sesión
    }
    
}