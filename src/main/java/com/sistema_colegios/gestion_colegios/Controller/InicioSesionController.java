package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioSesionController {

    @GetMapping("/")
    public String mostrarLogin() {
        return "index";
    }

}
