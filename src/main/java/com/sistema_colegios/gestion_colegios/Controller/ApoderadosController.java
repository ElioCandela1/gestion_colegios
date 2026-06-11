package com.sistema_colegios.gestion_colegios.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema_colegios.gestion_colegios.Model.Entity.Apoderados;
import com.sistema_colegios.gestion_colegios.Model.Entity.Usuarios;
import com.sistema_colegios.gestion_colegios.Model.Service.ApoderadosService;
import com.sistema_colegios.gestion_colegios.Model.Service.GenerarCodigoApoderado;

import jakarta.servlet.http.HttpSession;

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
    public String guardarApoderado(@ModelAttribute Apoderados apoderado,
            RedirectAttributes redirectAttributes) {

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
    public String editarDatosApoderado(RedirectAttributes redirectAttributes,
            @ModelAttribute Apoderados apoderado,
            @PathVariable Integer id) {

        try {
            apoderado = apoderadosService.obtenerApoderadosPorId(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("apoderado", apoderado);
        // redirectAttributes.addFlashAttribute("pagina", pagina);
        // redirectAttributes.addFlashAttribute("apoderados", pagina.getContent());

        return "redirect:/apoderados/gestionApoderados";
    }

    // Buscar apoderado
    @GetMapping("/buscar")
    public String buscarApoderado(@RequestParam String dni,
            Model model,
            @RequestParam(defaultValue = "0") int page) {

        Page<Apoderados> pagina = apoderadosService.obtenerApoderadosPorDniPage(dni, page);

        System.out.println(pagina.getContent());
        model.addAttribute("apoderados", pagina.getContent());
        model.addAttribute("pagina", pagina);

        return "apoderado";
    }

    // Eliminar apoderado (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminarApoderado(@PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        String mensaje = new String();

        try {
            mensaje = apoderadosService.eliminarApoderados(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
        redirectAttributes.addFlashAttribute("mensaje", mensaje);
        return "redirect:/apoderados/gestionApoderados";
    }

}
