package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Service.EstudiantesService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/estudiantes")
public class EstudiantesController {
    @Autowired
    private EstudiantesService estudiantesService;

    //Mostrar el formulario de matriculación
    @GetMapping("/matricular")
    public String mostrarFormularioMatriculacion(Model model) {
        model.addAttribute("estudiante", new Estudiantes());
        model.addAttribute("estudiantes", estudiantesService.listarEstudiantes());
        return "matricular";
    }

    
    //Guardar un nuevo estudiante
    @PostMapping("/matricular")

    public String matricularEstudiante(@ModelAttribute Estudiantes estudiante, Model model) {
        estudiantesService.guardarEstudiante(estudiante);
        model.addAttribute("mensaje", "Estudiante matriculado exitosamente");
        return "redirect:/estudiantes/matricular";
    }

    // Buscar estudiante
    @GetMapping("/buscar")
    public String buscarEstudiante(@RequestParam String parametro, @RequestParam String valor, Model model) {
        Estudiantes estudiante = null;
        switch (parametro) {
            case "codigo":
                estudiante = estudiantesService.obtenerEstudiantePorCodigo(valor);
                break;
            case "nombre":
                estudiante = estudiantesService.obtenerEstudiantePorNombre(valor);
                break;
            case "dni":
                estudiante = estudiantesService.obtenerEstudiantePorDni(valor);
                break;
            case "apellido":
                estudiante = estudiantesService.obtenerEstudiantePorApellido(valor);
                break;
        }
        model.addAttribute("estudiante", estudiante);
        return "resultado_busqueda";
    }

    

    //Listar todos los estudiantes
    @GetMapping("/listarEstudiantes")
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudiantesService.listarEstudiantes());
        return "matricular";
    }



}
