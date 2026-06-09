package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.autoconfigure.ServerProperties.Reactive.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Service.ApoderadosService;
import com.sistema_colegios.gestion_colegios.Model.Service.EstudiantesService;
import com.sistema_colegios.gestion_colegios.Model.Service.MatriculasService;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/estudiantes")
public class EstudiantesController {
    @Autowired
    private EstudiantesService estudiantesService;
    @Autowired
    MatriculasService matriculasService;
    @Autowired
    ApoderadosService apoderadosService;

    //Mostrar la ventana de Gestion de Estudiantes

    @GetMapping("/gestionEstudiantes")
    public String mostrarGestionEstudiantes(Model model) {
        model.addAttribute("estudiante",new Estudiantes());
        model.addAttribute("estudiantes", estudiantesService.listarEstudiantes());
        model.addAttribute("apoderados", apoderadosService.listarApoderados());
        return "estudianteform";
    }
    

    // Listar todos los estudiantes
    @GetMapping("/listarEstudiantes")
    public void listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudiantesService.listarEstudiantes());
    }

    // Editar estudiante
    // Llenar los datos del formulario con los del estudiante seleccionado

    @PostMapping("/editar")
    public void editarDatosEstudiante(Model model, @ModelAttribute Estudiantes estudiante) {
        estudiantesService.actualizarEstudiante(estudiante);
        model.addAttribute("confirmación", "Estudiante modificado");

    }

    // Buscar estudiante
    @GetMapping("/buscar")
    public String buscarEstudiante(@RequestParam String parametro, @RequestParam String valor, Model model, Session usuarioActual) {
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

        // Cargar el estudiante encontrado en el formulario
        model.addAttribute("estudiante", estudiante != null ? estudiante : new Estudiantes());

        // Cargar la lista completa de matriculados
        model.addAttribute("estudiantesMatriculados", matriculasService.listarEstudiantesMatriculados());

        // Cargar la sesion actual
        model.addAttribute("usuarioLogeado", usuarioActual);
        return "matricular";
    }

}
