package com.proyecto.practicaAlvaro.controlador;

import com.proyecto.practicaAlvaro.modelo.Profesor;
import com.proyecto.practicaAlvaro.enums.Rol;
import com.proyecto.practicaAlvaro.servicio.ProfesorServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profesores")
public class ProfesorControlador {

    private final ProfesorServicio profesorServicio;

    public ProfesorControlador(ProfesorServicio profesorServicio) {
        this.profesorServicio = profesorServicio;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("profesores", profesorServicio.findAll());
        return "profesores/index";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("profesor", new Profesor());
        model.addAttribute("roles", Rol.values());
        return "profesores/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        profesorServicio.findById(id).ifPresent(p -> {
            model.addAttribute("profesor", p);
            model.addAttribute("roles", Rol.values());
        });
        return "profesores/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Profesor profesor) {
        profesorServicio.save(profesor);
        return "redirect:/profesores";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            profesorServicio.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Profesor eliminado correctamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "No se puede eliminar el profesor porque tiene datos asociados");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/profesores";
    }
}

