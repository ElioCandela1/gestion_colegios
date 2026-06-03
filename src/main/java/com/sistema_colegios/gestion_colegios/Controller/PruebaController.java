package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PruebaController {

    @GetMapping("/prueba")
    public String mostrarPrueba(HttpSession session) {
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/";
        }
        return "prueba";
    }
}
