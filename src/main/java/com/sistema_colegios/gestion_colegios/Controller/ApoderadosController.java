package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.ApoderadosService;
import com.sistema_colegios.gestion_colegios.Model.Service.GenerarCodigoApoderado;
import com.sistema_colegios.gestion_colegios.Model.Service.Rol;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/apoderados")
public class ApoderadosController {

    @Autowired
    ApoderadosService apoderadosService;
    @Autowired
    GenerarCodigoApoderado generarCodigoApoderado;

    // Atributo global para este controller
    @ModelAttribute("apoderado")
    public Apoderados cargarEstudiante() {
        return new Apoderados();
    }

    // Usuario logeado desde la sesión
    @ModelAttribute("usuarioLogeado")
    public Usuarios usuarioLogeado(HttpSession session) {
        return (Usuarios) session.getAttribute("usuarioLogeado");
    }

    // Mostrar ventana de Gestion de Apoderados
    @GetMapping("/gestionApoderados")
    public String motrarVistaApoderado(Model model, @RequestParam(defaultValue = "0") int page) {

        Page<Apoderados> pagina = apoderadosService.listarApoderadosActivos(page);

        model.addAttribute("apoderados", pagina.getContent());
        model.addAttribute("pagina", pagina);

        return "apoderado";
    }

    // Generar código para nuevo apoderado
    @GetMapping("/nuevo")
    public String nuevoApoderado(RedirectAttributes redirectAttributes, Apoderados apoderado) {

        apoderado.setCodigo(generarCodigoApoderado.generarCodigo());
        redirectAttributes.addFlashAttribute("apoderado", apoderado);

        return "redirect:/apoderados/gestionApoderados";
    }

    // Guardar Apoderado
    @PostMapping("/guardar")
    public String guardarApoderado(
            @ModelAttribute @Valid Apoderados apoderado,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        //Validación de formularios
        if (bindingResult.hasErrors()) {

            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);

            return "redirect:/apoderados/gestionApoderados";
        }

        //Guardar datos
        try {
            String guardar = apoderadosService.guardarApoderados(apoderado);

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", guardar);
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "redirect:/apoderados/gestionApoderados";
    }

    // Editar apoderado
    @GetMapping("/editar/{id}")
    public String editarDatosApoderado(
            RedirectAttributes redirectAttributes,
            @ModelAttribute Apoderados apoderado,
            @PathVariable Integer id) {

        try {
            apoderado = apoderadosService.obtenerApoderadosPorId(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("apoderado", apoderado);
        
        return "redirect:/apoderados/gestionApoderados";
    }

    // Buscar apoderado
    @GetMapping("/buscar")
    public String buscarApoderado(
            @RequestParam String dni,
            Model model,
            @RequestParam(defaultValue = "0") int page) {

        Page<Apoderados> pagina = apoderadosService.obtenerApoderadosPorDniPage(dni, page);

        model.addAttribute("apoderados", pagina.getContent());
        model.addAttribute("pagina", pagina);

        return "apoderado";
    }

    // Eliminar apoderado (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarApoderado(@PathVariable Integer id,
            RedirectAttributes redirectAttributes) {    

        try {
            String mensaje = apoderadosService.eliminarApoderados(id);
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        //redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
        //redirectAttributes.addFlashAttribute("mensaje", mensaje);
        return "redirect:/apoderados/gestionApoderados";
    }

}
