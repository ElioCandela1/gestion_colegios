package com.sistema_colegios.gestion_colegios.Controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/matriculas")
@SessionAttributes({
        "anioSeleccionado",
        "gradoSeleccionado",
        "seccionSeleccionada"
})
public class MatricularEstudianteController {

    @Autowired
    private EstudiantesService estudiantesService;
    @Autowired
    private SeccionesService seccionesService;
    @Autowired
    private MatriculasService matriculasService;

    @ModelAttribute("matricula")
    public Matriculas cargarMatricula() {
        return new Matriculas();
    }

    // Usuario logeado desde la sesión
    @ModelAttribute("usuarioLogeado")
    public Usuarios usuarioLogeado(HttpSession session) {
        return (Usuarios) session.getAttribute("usuarioLogeado");
    }

    // Mostrar el formulario de matriculación
    @GetMapping("/matricular")
    public String mostrarFormularioMatriculacion(@RequestParam(required = false) Integer anioEscolar,
            @RequestParam(required = false) Integer grado,
            @RequestParam(required = false) String seccion,

            @RequestParam(defaultValue = "0") int page,
            // @RequestParam(defaultValue = "10") int size,
            Model model) {

        model.addAttribute("anios", matriculasService.listarAnios());

        Pageable pageable = PageRequest.of(page, 10);

        if (seccion != null && seccion.isBlank()) {
            seccion = null;
        }

        Page<Matriculas> pagina = matriculasService.filtrar(
                anioEscolar,
                grado,
                seccion,
                pageable);

        model.addAttribute(
                "estudiantesMatriculados",
                pagina.getContent());

        model.addAttribute("pagina", pagina);

        // Mantener filtros seleccionados
        model.addAttribute("anioSeleccionado", anioEscolar);
        model.addAttribute("gradoSeleccionado", grado);
        model.addAttribute("seccionSeleccionada", seccion);

        return "matricular";
    }

    // Guardar una nueva matrícula
    @PostMapping("/matricular")
    public String matricularEstudiante(@ModelAttribute("matricula") Matriculas matricula,
            @RequestParam("seccion.nombreSeccion") String nombreSeccion,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Settear el año y sección, ya que la vista los envia con formato distinto
        matricula.setSeccion(seccionesService.obtenerSeccionPorNombreYGrado(nombreSeccion, matricula.getGrado()));
        matricula.setAnioEscolar(matricula.getFechaMatricula().getYear());

        // proceso de matriculación
        try {
            String matricular = matriculasService.matricularEstudiante(matricula.getEstudiante().getDni(), matricula);

            /*
             * if (matricular.equals("YA_MATRICULADO")) {
             * model.addAttribute("tipoModal", "confirmacion");
             * model.addAttribute("mensaje",
             * "El estudiante ya está matriculado. ¿Desea editar la matrícula?");
             * //int idMatriculaExistente =
             * matriculasService.obtenerMatriculaPorEstudiante(matricula.getEstudiante());
             * model.addAttribute("idMatricula", matricula.getIdMatricula());
             * } else {
             * model.addAttribute("tipoModal", "notificación");
             * model.addAttribute("mensaje", matricular);
             * }
             */

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", matricular);

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        model.addAttribute("matricula", matricula);

        return "redirect:/matriculas/matricular";
    }

    // Editar una Matricula
    @GetMapping("/editar/{id}")
    public String editar(   @PathVariable Integer id,
                            Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @SessionAttribute(value = "anioSeleccionado", required = false) Integer anioEscolar,
                            @SessionAttribute(value = "gradoSeleccionado", required = false) Integer grado,
                            @SessionAttribute(value = "seccionSeleccionada", required = false) String seccion) {

        Matriculas matricula = matriculasService.obtenerMatriculaPorId(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

        Pageable pageable = PageRequest.of(page, 10);
        Page<Matriculas> pagina = matriculasService.filtrar(anioEscolar, grado, seccion, pageable);

        model.addAttribute("matricula", matricula);
        model.addAttribute("pagina", pagina);
        model.addAttribute("estudiantesMatriculados", pagina.getContent());

        return "matricular";
    }

    // Eliminar una matricula (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarMatricula(@PathVariable int id, Matriculas matricula,
            RedirectAttributes redirectAttributes) {
        matriculasService.eliminarMatricula(id);

        return "redirect:/matriculas/matricular";
    }

    // Buscar estudiante
    @GetMapping("/buscar")
    public String buscarEstudiante(@RequestParam String dni, RedirectAttributes redirectAttributes) {

        Integer anioEscolarActual = LocalDate.now().getYear();
        Matriculas matricula = new Matriculas();
        Estudiantes estudiante;

        // Verifica que exista el estudiante
        try {
            estudiante = estudiantesService.obtenerEstudiantePorDni(dni);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/matriculas/matricular";
        }

        // Verifica si ya está matriculado en el presente año
        Optional<Matriculas> matriculaExistente = matriculasService.obtenerEstudiantesMatriculadosPorAño(estudiante,
                anioEscolarActual);

        if (matriculaExistente.isPresent() && matriculaExistente.get().isEstadoRegistro()) {
            matricula = matriculaExistente.get();
            //redirectAttributes.addFlashAttribute("matricula", matricula);
            redirectAttributes.addFlashAttribute("tipoModal", "confirmacion");
            redirectAttributes.addFlashAttribute("mensaje", "El estudiante ya está matriculado. ¿Desea editar la matrícula?");
        } else {
            matricula.setEstudiante(estudiante);
        }

        redirectAttributes.addFlashAttribute("matricula", matricula);
        redirectAttributes.addAttribute("id", matricula.getIdMatricula());
        return "redirect:/matriculas/matricular";
    }
}
