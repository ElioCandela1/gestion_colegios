package com.sistema_colegios.gestion_colegios.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class InicioSesionController {

    @GetMapping("/")    
    public String info(){
        return "index";
    }

}
