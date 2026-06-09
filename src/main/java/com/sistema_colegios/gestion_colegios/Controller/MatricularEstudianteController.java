package com.sistema_colegios.gestion_colegios.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/matriculas")
public class MatricularEstudianteController {

    @Autowired
    private EstudiantesService estudiantesService;
    @Autowired
    private SeccionesService seccionesService;
    @Autowired
    private GradosService gradosService;
    @Autowired
    private MatriculasService matriculasService;

    // Mostrar el formulario de matriculación
    @GetMapping("/matricular")
    public String mostrarFormularioMatriculacion(Model model) {
        model.addAttribute("matricula", new Matriculas());
        model.addAttribute("estudiantesMatriculados", matriculasService.listarEstudiantesMatriculados());
        return "matricular";
    }

    // Guardar una nueva matrícula
    @PostMapping("/matricular")
    public String matricularEstudiante(@ModelAttribute("matricula") Matriculas matricula, BindingResult result,
            @RequestParam("seccion.nombre") String nombreSeccion,
            Model model) {
        if (result.hasErrors()) {
            // Aquí puedes imprimir los errores para ver qué campo falla
            result.getAllErrors().forEach(System.out::println);
            return "matricular";
        }

        matricula.setSeccion(seccionesService.obtenerSeccionPorNombreYGrado(nombreSeccion, matricula.getGrado()));
        matricula.setAnioEscolar(matricula.getFechaMatricula().getYear());
        matriculasService.matricularEstudiante(matricula.getEstudiante().getDni(), matricula);
        model.addAttribute("mensaje", "Estudiante matriculado exitosamente");
        return "redirect:/matriculas/matricular";
    }

    // Editar una Matricula ... se debe modificar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, HttpSession session) {
        Matriculas matricula = matriculasService.obtenerMatriculaPorId(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        model.addAttribute("matricula", matricula);
        model.addAttribute("estudiantesMatriculados", matriculasService.listarEstudiantesMatriculados());
        Usuarios usuarioLogeado = (Usuarios) session.getAttribute("usuarioLogeado");
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        return "matricular";
    }

    //Buscar estudiante

    @GetMapping("/buscar")
    public String buscarEstudianteporDni(@RequestParam String dni, Model model){

        Estudiantes estudiante = estudiantesService.obtenerEstudiantePorDni(dni);
        Matriculas matricula = new Matriculas();
        matricula.setEstudiante(estudiante);
        model.addAttribute("matricula", matricula);
        model.addAttribute("estudiantesMatriculados", matriculasService.listarEstudiantesMatriculados());
        return "matricular";
    }
}
