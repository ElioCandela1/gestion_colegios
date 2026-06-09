package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema_colegios.gestion_colegios.Model.Entity.Docentes;
import com.sistema_colegios.gestion_colegios.Model.Service.DocentesService;


@Controller
@RequestMapping("/docentes")
public class DocentesController {

    @Autowired
    DocentesService docentesService;

    @GetMapping("/gestionDocentes")
    public String mostrarGestionDocentes(Model model) {
        model.addAttribute("docente", new Docentes());
        model.addAttribute("docentes", docentesService.listarDocentes());
        return "docenteform";
    }
    

}
