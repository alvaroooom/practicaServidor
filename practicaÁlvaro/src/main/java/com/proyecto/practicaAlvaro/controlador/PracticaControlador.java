package com.proyecto.practicaAlvaro.controlador;

import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.modelo.Empresa;
import com.proyecto.practicaAlvaro.modelo.Practica;
import com.proyecto.practicaAlvaro.servicio.AlumnoServicio;
import com.proyecto.practicaAlvaro.servicio.EmpresaServicio;
import com.proyecto.practicaAlvaro.servicio.PracticaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/practicas")
public class PracticaControlador {

    private final PracticaServicio practicaServicio;
    private final AlumnoServicio alumnoServicio;
    private final EmpresaServicio empresaServicio;

    public PracticaControlador(PracticaServicio practicaServicio, AlumnoServicio alumnoServicio, EmpresaServicio empresaServicio) {
        this.practicaServicio = practicaServicio;
        this.alumnoServicio = alumnoServicio;
        this.empresaServicio = empresaServicio;
    }

    // listado
    @GetMapping
    public String listarPracticas(Model model) {
        List<Practica> practicas = practicaServicio.obtenerTodas();
        model.addAttribute("practicas", practicas);
        return "practicas/index";
    }

    // formulario para crear nueva practica
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("practica", new Practica());
        model.addAttribute("alumnos", alumnoServicio.findAll());
        model.addAttribute("empresas", empresaServicio.findAll());
        return "practicas/form";
    }

    // guardar practica
    @PostMapping("/guardar")
    public String guardarPractica(@Valid @ModelAttribute Practica practica,
                                  BindingResult result,
                                  @RequestParam Long alumnoId,
                                  @RequestParam Long empresaId,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alumnos", alumnoServicio.findAll());
            model.addAttribute("empresas", empresaServicio.findAll());
            return "practicas/form";
        }

        Alumno alumno = alumnoServicio.findById(alumnoId).orElse(null);
        Empresa empresa = empresaServicio.findById(empresaId).orElse(null);

        if (alumno == null || empresa == null) {
            model.addAttribute("error", "Alumno o empresa no válido");
            model.addAttribute("alumnos", alumnoServicio.findAll());
            model.addAttribute("empresas", empresaServicio.findAll());
            return "practicas/form";
        }

        practicaServicio.guardarPractica(practica, alumno, empresa);
        return "redirect:/practicas";
    }

    // formulario para editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Practica practica = practicaServicio.obtenerPorId(id).orElse(null);
        if (practica == null) {
            return "redirect:/practicas";
        }
        model.addAttribute("practica", practica);
        model.addAttribute("alumnos", alumnoServicio.findAll());
        model.addAttribute("empresas", empresaServicio.findAll());
        return "practicas/form";
    }

    // eliminar practica
    @GetMapping("/eliminar/{id}")
    public String eliminarPractica(@PathVariable Long id) {
        practicaServicio.eliminarPractica(id);
        return "redirect:/practicas";
    }
}
