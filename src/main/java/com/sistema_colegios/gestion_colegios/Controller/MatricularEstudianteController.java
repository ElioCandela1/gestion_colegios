package com.sistema_colegios.gestion_colegios.Controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Matriculas;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
        public String mostrarFormularioMatriculacion(
                        @RequestParam(required = false) Integer anioEscolar,
                        @RequestParam(required = false) Integer grado,
                        @RequestParam(required = false) String seccion,
                        @RequestParam(defaultValue = "0") int page,
                        Model model) {

                model.addAttribute("anios", matriculasService.listarAnios());

                if (seccion != null && seccion.isBlank()) {
                        seccion = null;
                }

                Pageable pageable = PageRequest.of(page, 10);

                Page<Matriculas> pagina = matriculasService.filtrar(
                                anioEscolar,
                                grado,
                                seccion,
                                pageable);

                model.addAttribute("estudiantesMatriculados", pagina.getContent());
                model.addAttribute("pagina", pagina);

                // Mantener filtros seleccionados
                model.addAttribute("anioSeleccionado", anioEscolar);
                model.addAttribute("gradoSeleccionado", grado);
                model.addAttribute("seccionSeleccionada", seccion);

                return "matricular";
        }

        // Guardar una nueva matrícula
        @PostMapping("/matricular")
        public String matricularEstudiante(
                        @ModelAttribute("matricula") @Valid Matriculas matricula,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        @RequestParam("seccion.nombreSeccion") String nombreSeccion,
                        @RequestParam(required = false) Integer anioEscolar) {

                if (bindingResult.hasErrors()) {

                        String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();

                        redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
                        redirectAttributes.addFlashAttribute("mensaje", mensaje);

                        return "redirect:/matriculas/matricular";
                }

                // Valdiaciónes

                if (matricula.getEstudiante() == null || matricula.getEstudiante().getDni().isEmpty()) {

                        redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
                        redirectAttributes.addFlashAttribute("mensaje", "Busque un alumno registrado para continuar");

                        return "redirect:/matriculas/matricular";
                }

                // proceso de matriculación
                try {

                        // Settear seccion en la matricula
                        matricula.setSeccion(seccionesService.obtenerSeccionPorNombreYGrado(nombreSeccion,
                                        matricula.getGrado()));

                        // Settear año escolar
                        matricula.setAnioEscolar(matricula.getFechaMatricula().getYear());

                        System.out.println("datos del estudiante: " + matricula.getEstudiante().getNombre() + " "
                                        + matricula.getSeccion().getCapacidad() + " "
                                        + matricula.getSeccion().getIdSeccion());
                        String matricular = matriculasService.matricularEstudiante(
                                        matricula.getEstudiante().getDni(),
                                        matricula);

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

                // redirectAttributes.addFlashAttribute("matricula", matricula);

                return "redirect:/matriculas/matricular";
        }

        // Editar una Matricula
        @GetMapping("/editar/{id}")
        public String editar(@PathVariable Integer id,
                        Model model) {

                Matriculas matricula = matriculasService.obtenerMatriculaPorId(id)
                                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

                model.addAttribute("matricula", matricula);

                return mostrarFormularioMatriculacion(
                                null,
                                null,
                                null,
                                0,
                                model);
        }

        // Eliminar una matricula (Soft Delete)
        @GetMapping("/eliminar/{id}")
        public String eliminarMatricula(
                        @PathVariable Integer id,
                        RedirectAttributes redirectAttributes) {

                try {

                        matriculasService.eliminarMatricula(id);

                        redirectAttributes.addFlashAttribute(
                                        "tipoModal",
                                        "notificacion");

                        redirectAttributes.addFlashAttribute(
                                        "mensaje",
                                        "Matrícula eliminada correctamente");

                } catch (RuntimeException e) {

                        redirectAttributes.addFlashAttribute(
                                        "tipoModal",
                                        "notificacion");

                        redirectAttributes.addFlashAttribute(
                                        "mensaje",
                                        e.getMessage());
                }

                return "redirect:/matriculas/matricular";
        }

        // Buscar estudiante
        @GetMapping("/buscar")
        public String buscarEstudiante(
                        @RequestParam String dni,
                        RedirectAttributes redirectAttributes) {

                Integer anioActual = LocalDate.now().getYear();

                try {

                        Estudiantes estudiante = estudiantesService.obtenerEstudiantePorDni(dni);

                        Matriculas matricula;

                        Optional<Matriculas> existente = matriculasService.obtenerEstudiantesMatriculadosPorAño(
                                        estudiante,
                                        anioActual);

                        if (existente.isPresent() &&
                                        existente.get().isEstadoRegistro()) {

                                matricula = existente.get();

                                redirectAttributes.addFlashAttribute(
                                                "tipoModal",
                                                "confirmacion");

                                redirectAttributes.addFlashAttribute(
                                                "mensaje",
                                                "El estudiante ya está matriculado. ¿Desea editar la matrícula?");

                        } else {

                                matricula = new Matriculas();
                                matricula.setEstudiante(estudiante);
                        }

                        redirectAttributes.addFlashAttribute(
                                        "matricula",
                                        matricula);

                } catch (RuntimeException e) {

                        redirectAttributes.addFlashAttribute(
                                        "tipoModal",
                                        "notificacion");

                        redirectAttributes.addFlashAttribute(
                                        "mensaje",
                                        e.getMessage());
                }

                return "redirect:/matriculas/matricular";
        }
}
