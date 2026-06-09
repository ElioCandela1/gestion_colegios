package com.sistema_colegios.gestion_colegios.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
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
        model.addAttribute("estudiante", new Estudiantes());
        model.addAttribute("estudiantesMatriculados", matriculasService.listarEstudiantesMatriculados());
        return "matricular";
    }

    // Guardar una nueva matrícula
    @PostMapping("/matricular")
    public String matricularEstudiante(@ModelAttribute Estudiantes estudiante,
            @RequestParam("seccion") String nombreSeccion,
            @RequestParam("grado") Integer idGrado,
            @RequestParam("fechaMatricula") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaMatricula,
            @RequestParam("estado") boolean estado,
            Model model) {
        matriculasService.matricularEstudiante(estudiante,
                seccionesService.obtenerSeccionPorNombreYGrado(nombreSeccion, gradosService.obtenerGradoPorId(idGrado)), // Obtener la sección por su nombre y grado
                gradosService.obtenerGradoPorId(idGrado), // Obtener el grado por su ID
                fechaMatricula.getYear(), // Año escolar
                fechaMatricula, // Fecha de la matricula
                estado);
        model.addAttribute("mensaje", "Estudiante matriculado exitosamente");
        return "redirect:/matriculas/matricular";
    }

    
    

    // Editar una Matricula ... se debe modificar
    @GetMapping("/editar/{id}")
    public String mostrarFormulario(@PathVariable Integer id, Model model, HttpSession session) {
        Estudiantes estudiante = estudiantesService.obtenerEstudiantePorId(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("estudiantesMatriculados", matriculasService.listarEstudiantesMatriculados());
        Usuarios usuarioLogeado = (Usuarios) session.getAttribute("usuarioLogeado");
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        return "matricular";
    }
}
