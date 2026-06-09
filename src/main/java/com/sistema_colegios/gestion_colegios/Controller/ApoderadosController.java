package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Service.ApoderadosService;


@Controller
@RequestMapping("/apoderados")
public class ApoderadosController {

    @Autowired
    ApoderadosService apoderadosService;

    // Mostrar ventana de Gestion de Apoderados
    @GetMapping("/gestionApoderados")
    public String motrarVistaApoderado(Model model) {
        model.addAttribute("apoderado", new Apoderados());
        model.addAttribute("apoderados", apoderadosService.listarApoderados());
        return "apoderado";
    }
    
}
