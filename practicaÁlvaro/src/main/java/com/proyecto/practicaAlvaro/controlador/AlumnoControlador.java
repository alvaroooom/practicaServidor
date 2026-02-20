package com.proyecto.practicaAlvaro.controlador;

import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.servicio.AlumnoServicio;
import com.proyecto.practicaAlvaro.servicio.CursoServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
@RequestMapping("/alumnos")
public class AlumnoControlador {

    private final AlumnoServicio alumnoServicio;
    private final CursoServicio cursoServicio;

    public AlumnoControlador(AlumnoServicio alumnoServicio, CursoServicio cursoServicio) {
        this.alumnoServicio = alumnoServicio;
        this.cursoServicio = cursoServicio;
    }

    @GetMapping
    public String index(@RequestParam(value = "cursoId", required = false) Long cursoId, Model model) {
        if (cursoId != null) {
            model.addAttribute("alumnos", alumnoServicio.findByCursoId(cursoId));
        } else {
            model.addAttribute("alumnos", alumnoServicio.findAll());
        }
        model.addAttribute("cursos", cursoServicio.findAll()); // Para el select del CSV y el filtro
        model.addAttribute("cursoSeleccionado", cursoId);
        return "alumnos/index";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("alumno", new Alumno());
        model.addAttribute("cursos", cursoServicio.findAll());
        return "alumnos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        alumnoServicio.findById(id).ifPresent(a -> {
            model.addAttribute("alumno", a);
            model.addAttribute("cursos", cursoServicio.findAll());
        });
        return "alumnos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Alumno alumno) {
        alumnoServicio.save(alumno);
        return "redirect:/alumnos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        alumnoServicio.deleteById(id);
        return "redirect:/alumnos";
    }

    @PostMapping("/importar")
    public String importar(@RequestParam("file") MultipartFile file, @RequestParam("cursoId") Long cursoId) {
        try {
            alumnoServicio.importarAlumnos(file, cursoId);
        } catch (Exception e) {
            e.printStackTrace(); // En un caso real, manejar error en UI
        }
        return "redirect:/alumnos";
    }

    // REST API
    
    @GetMapping("/api")
    @ResponseBody
    public List<Alumno> obtenerAlumnos() {
        return alumnoServicio.findAll();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Alumno obtenerAlumno(@PathVariable Long id) {
        return alumnoServicio.findById(id).orElse(null);
    }

    @PostMapping("/api")
    @ResponseBody
    public Alumno crearAlumno(@RequestBody Alumno alumno) {
        return alumnoServicio.save(alumno);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public Alumno actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        alumno.setId(id);
        return alumnoServicio.save(alumno);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void eliminarAlumno(@PathVariable Long id) {
        alumnoServicio.deleteById(id);
    }
}

