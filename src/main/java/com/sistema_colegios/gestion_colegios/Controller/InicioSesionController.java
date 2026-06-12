package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class InicioSesionController {

    @GetMapping("/")
    public String mostrarLogin() {
        return "index";
    }

    @GetMapping("/inicio")
    public String mostrarPaginaInicio() {
        return "inicio";
    }
    

}
