package com.proyecto.practicaAlvaro.controlador;

import com.proyecto.practicaAlvaro.modelo.Practica;
import com.proyecto.practicaAlvaro.servicio.PracticaServicio;
import com.proyecto.practicaAlvaro.servicio.AlumnoServicio;
import com.proyecto.practicaAlvaro.servicio.EmpresaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/practicas")
public class PracticaControlador {

    private final PracticaServicio practicaServicio;
    private final AlumnoServicio alumnoServicio;
    private final EmpresaServicio empresaServicio;

    public PracticaControlador(PracticaServicio practicaServicio, AlumnoServicio alumnoServicio, 
                               EmpresaServicio empresaServicio) {
        this.practicaServicio = practicaServicio;
        this.alumnoServicio = alumnoServicio;
        this.empresaServicio = empresaServicio;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("practicas", practicaServicio.obtenerTodas());
        return "practicas/index";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("practica", new Practica());
        model.addAttribute("alumnos", alumnoServicio.findAll());
        model.addAttribute("empresas", empresaServicio.findAll());
        return "practicas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        practicaServicio.obtenerPorId(id).ifPresent(p -> {
            model.addAttribute("practica", p);
            model.addAttribute("alumnos", alumnoServicio.findAll());
            model.addAttribute("empresas", empresaServicio.findAll());
        });
        return "practicas/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Practica practica) {
        practicaServicio.save(practica);
        return "redirect:/practicas";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        practicaServicio.eliminarPractica(id);
        return "redirect:/practicas";
    }
}

