package com.proyecto.practicaAlvaro.controlador;

import com.proyecto.practicaAlvaro.modelo.Curso;
import com.proyecto.practicaAlvaro.servicio.CursoServicio;
import com.proyecto.practicaAlvaro.servicio.ProfesorServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cursos")
public class CursoControlador {

    private final CursoServicio cursoServicio;
    private final ProfesorServicio profesorServicio;

    public CursoControlador(CursoServicio cursoServicio, ProfesorServicio profesorServicio) {
        this.cursoServicio = cursoServicio;
        this.profesorServicio = profesorServicio;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("cursos", cursoServicio.findAll());
        return "cursos/index";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("profesores", profesorServicio.findAll());
        return "cursos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        cursoServicio.findById(id).ifPresent(c -> {
            model.addAttribute("curso", c);
            model.addAttribute("profesores", profesorServicio.findAll());
        });
        return "cursos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Curso curso) {
        cursoServicio.save(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            cursoServicio.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Curso eliminado correctamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "No se puede eliminar el curso porque tiene alumnos asociados");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/cursos";
    }
}

