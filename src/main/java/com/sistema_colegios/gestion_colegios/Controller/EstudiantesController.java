package com.sistema_colegios.gestion_colegios.Controller;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Estudiantes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.ApoderadosService;
import com.sistema_colegios.gestion_colegios.Model.Service.EstudiantesService;
import com.sistema_colegios.gestion_colegios.Model.Service.GeneradorCodigoEstudiante;
import com.sistema_colegios.gestion_colegios.Model.Service.MatriculasService;
import com.sistema_colegios.gestion_colegios.Model.Service.Rol;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    @Autowired
    GeneradorCodigoEstudiante generadorCodigoEstudiante;

    // Atributo global para este controller
    @ModelAttribute("estudiante")
    public Estudiantes cargarEstudiante() {
        return new Estudiantes();
    }

    // Usuario logeado desde la sesión
    @ModelAttribute("usuarioLogeado")
    public Usuarios usuarioLogeado(HttpSession session) {
        return (Usuarios) session.getAttribute("usuarioLogeado");
    }

    // Verificación de seguridad temporal
    @GetMapping
    public String verificarSession(@ModelAttribute("usuarioLogeado") Usuarios usuarioLogeado) {
        // Validar si existe sesión activa
        if (usuarioLogeado == null || usuarioLogeado.getRol() != Rol.ADMIN) {
            return "redirect:/index";
        }

        // Si hay sesión activa, mostrar la vista de estudiantes
        return "estudiantes";
    }

    // Mostrar la ventana de Gestion de Estudiantes
    @GetMapping("/gestionEstudiantes")
    public String mostrarGestionEstudiantes(Model model, @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("estudiante") Estudiantes estudiante) {

        Page<Estudiantes> pagina = estudiantesService.listarEstudiantesActivos(page);

        // Solo se inicializa apoderado si el estudiante está vacío
        if (estudiante.getApoderado() == null) {
            estudiante.setApoderado(new Apoderados());
        }

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("estudiantes", pagina.getContent()); // lista de estudiantes
        model.addAttribute("pagina", pagina);

        return "estudianteform";
    }

    // Generar codigo para nuevo estudiante
    @GetMapping("/nuevo")
    public String nuevoEstudiante(RedirectAttributes redirectAttributes, Estudiantes estudiante) {

        estudiante.setCodigo(generadorCodigoEstudiante.generarCodigo());
        redirectAttributes.addFlashAttribute("estudiante", estudiante);
        return "redirect:/estudiantes/gestionEstudiantes";
    }

    // Guardar Estudiante
    @PostMapping("/guardar")
    public String guardarEstudiante(
            @ModelAttribute @Valid Estudiantes estudiante,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page) {

        if (bindingResult.hasErrors()) {

            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);

            return "redirect:/estudiantes/gestionEstudiantes";
        }

        // verificar codigo del estudiante

        if (estudiante.getCodigo() == null || estudiante.getCodigo().isBlank()) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", "Debe generar un código");
            redirectAttributes.addFlashAttribute(estudiante);
            return "redirect:/estudiantes/gestionEstudiantes";
        }
        
        try {
            String guardar = estudiantesService.guardarEstudiante(estudiante);

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", guardar);
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        Page<Estudiantes> pagina = estudiantesService.listarEstudiantesActivos(page);

        redirectAttributes.addFlashAttribute("pagina", pagina);
        redirectAttributes.addFlashAttribute("estudiantes", pagina.getContent());
        return "redirect:/estudiantes/gestionEstudiantes";
    }

    // Listar todos los estudiantes
    @GetMapping("/listarEstudiantes")
    public void listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudiantesService.listarEstudiantes());
    }

    // Editar estudiante
    @GetMapping("/editar/{id}")
    public String editarDatosEstudiante(RedirectAttributes redirectAttributes,
            @ModelAttribute Estudiantes estudiante,
            @RequestParam(defaultValue = "0") int page,
            @PathVariable Integer id) {

        try {
            estudiante = estudiantesService.obtenerEstudiantePorId(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        // Pageable pageable = PageRequest.of(page, 10);
        Page<Estudiantes> pagina = estudiantesService.listarEstudiantesActivos(page);

        redirectAttributes.addFlashAttribute("estudiante", estudiante);
        redirectAttributes.addFlashAttribute("pagina", pagina);
        redirectAttributes.addFlashAttribute("estudiantes", pagina.getContent());

        return "redirect:/estudiantes/gestionEstudiantes";

    }

    // Buscar estudiante
    @GetMapping("/buscar")
    public String buscarEstudiante(@RequestParam String dni,
            Model model,
            @RequestParam(defaultValue = "0") int page) {

        try {
            Page<Estudiantes> pagina = estudiantesService.obtenerEstudiantePorDniPage(dni, page);
            model.addAttribute("estudiantes", pagina.getContent());
            model.addAttribute("pagina", pagina);
        } catch (Exception e) {
            model.addAttribute("tipoModal", "notificacion");
            model.addAttribute("mensaje", e);
        }

        return "estudianteform";
    }

    // Buscar apoderado
    @GetMapping("/buscarApoderado")
    public String buscarApoderado(@RequestParam("dniApoderado") String dniApoderado,
            RedirectAttributes redirectAttributes,
            @ModelAttribute Estudiantes estudiante) {

        Apoderados apoderado;
        try {
            apoderado = apoderadosService.obtenerApoderadosPorDni(dniApoderado);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("estudiante", estudiante);
            return "redirect:/estudiantes/gestionEstudiantes";
        }

        estudiante.setApoderado(apoderado);
        redirectAttributes.addFlashAttribute("estudiante", estudiante);
        redirectAttributes.addFlashAttribute("apoderado", apoderado);

        return "redirect:/estudiantes/gestionEstudiantes";
    }

    // Eliminar estudiante (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Integer id,
            RedirectAttributes redirectAttributes) {
        String mensaje = new String();
        try {
            mensaje = estudiantesService.eliminarEstudiante(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
        redirectAttributes.addFlashAttribute("mensaje", mensaje);
        return "redirect:/estudiantes/gestionEstudiantes";
    }

}
