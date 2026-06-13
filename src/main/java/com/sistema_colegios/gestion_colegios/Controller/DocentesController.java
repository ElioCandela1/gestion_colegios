package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema_colegios.gestion_colegios.Model.Entity.Docentes;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.DocentesService;
import com.sistema_colegios.gestion_colegios.Model.Service.GeneradorCodigoDocente;
import com.sistema_colegios.gestion_colegios.Model.Service.Rol;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/docentes")
public class DocentesController {

    @Autowired
    DocentesService docentesService;

    @Autowired
    GeneradorCodigoDocente generarCodigoDocente;

    // Atributo global para este controller
    @ModelAttribute("docente")
    public Docentes cargarDocente() {
        return new Docentes();
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

        // Si hay sesión activa, mostrar la vista de docentes
        return "docentes";
    }

    // Mostrar ventana de Gestion de Apoderados
    @GetMapping("/gestionDocentes")
    public String mostrarVistaDocente(Model model, @RequestParam(defaultValue = "0") int page) {

        Page<Docentes> pagina = docentesService.listarDocentesActivos(page);

        model.addAttribute("docentes", pagina.getContent());
        model.addAttribute("pagina", pagina);

        return "docenteform";
    }

    // Generar código para nuevo docente
    @GetMapping("/nuevo")
    public String nuevoDocente(RedirectAttributes redirectAttributes, Docentes docente) {

        docente.setCodigo(generarCodigoDocente.generarCodigo());
        redirectAttributes.addFlashAttribute("docente", docente);

        return "redirect:/docentes/gestionDocentes";
    }

    // Guardar Docente
    @PostMapping("/guardar")
    public String guardarDocente(
            @ModelAttribute @Valid Docentes docente,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page) {

        if (bindingResult.hasErrors()) {

            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);

            return "redirect:/docentes/gestionDocentes";
        }

        // Validaciones
        if (docente.getCodigo() == null || docente.getCodigo().isBlank())

        {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", "Debe generar un codigo de usuario");
            return "redirect:/docentes/gestionDocentes";
        }

        try {
            String guardar = docentesService.guardarDocentes(docente);

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", guardar);
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "redirect:/docentes/gestionDocentes";
    }

    // Editar docente
    @GetMapping("/editar/{id}")
    public String editarDatosDocente(RedirectAttributes redirectAttributes,
            @ModelAttribute Docentes docente,
            @PathVariable Integer id) {

        try {
            docente = docentesService.obtenerDocentePorId(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("docente", docente);

        return "redirect:/docentes/gestionDocentes";
    }

    // Buscar docente
    @GetMapping("/buscar")
    public String buscarDocente(@RequestParam String dni,
            Model model,
            @RequestParam(defaultValue = "0") int page) {

        Page<Docentes> pagina = docentesService.obtenerDocentesPorDniPage(dni, page);

        System.out.println(pagina.getContent());
        model.addAttribute("docentes", pagina.getContent());
        model.addAttribute("pagina", pagina);

        return "docenteform";
    }

    // Eliminar docente (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarDocente(@PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        String mensaje = new String();

        try {
            mensaje = docentesService.eliminarDocentes(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
        redirectAttributes.addFlashAttribute("mensaje", mensaje);
        return "redirect:/docentes/gestionDocentes";
    }

}
