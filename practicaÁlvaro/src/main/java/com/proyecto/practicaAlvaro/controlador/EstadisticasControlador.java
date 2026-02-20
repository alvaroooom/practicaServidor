package com.proyecto.practicaAlvaro.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.practicaAlvaro.servicio.AlumnoServicio;
import com.proyecto.practicaAlvaro.servicio.CursoServicio;
import com.proyecto.practicaAlvaro.servicio.EmpresaServicio;
import com.proyecto.practicaAlvaro.servicio.PracticaServicio;
import com.proyecto.practicaAlvaro.servicio.ProfesorServicio;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/estadisticas")
public class EstadisticasControlador {
    
    private final ProfesorServicio profesorServicio;
    private final AlumnoServicio alumnoServicio;
    private final CursoServicio cursoServicio;
    private final EmpresaServicio empresaServicio;
    private final PracticaServicio practicaServicio;
    
    public EstadisticasControlador(ProfesorServicio profesorServicio, AlumnoServicio alumnoServicio,
            CursoServicio cursoServicio, EmpresaServicio empresaServicio, PracticaServicio practicaServicio) {
        this.profesorServicio = profesorServicio;
        this.alumnoServicio = alumnoServicio;
        this.cursoServicio = cursoServicio;
        this.empresaServicio = empresaServicio;
        this.practicaServicio = practicaServicio;
    }

    @GetMapping("")
    public String estadisticas(Model model) {
        var profesores = profesorServicio.findAll();
        var alumnos = alumnoServicio.findAll();
        var cursos = cursoServicio.findAll();
        var empresas = empresaServicio.findAll();
        var practicas = practicaServicio.obtenerTodas();
        
        // Contar totales
        model.addAttribute("totalProfesores", profesores.size());
        model.addAttribute("totalAlumnos", alumnos.size());
        model.addAttribute("totalCursos", cursos.size());
        model.addAttribute("totalEmpresas", empresas.size());
        model.addAttribute("totalPracticas", practicas.size());
        
        // Estadísticas de prácticas
        long practicasActivas = practicas.stream().filter(p -> p.getFechaFin() == null).count();
        long practicasCompletadas = practicas.stream().filter(p -> p.getFechaFin() != null).count();
        
        model.addAttribute("practicasActivas", practicasActivas);
        model.addAttribute("practicasCompletadas", practicasCompletadas);
        
        // Alumnos en prácticas
        long alumnosEnPracticas = alumnos.stream().filter(a -> a.getPracticas() != null && !a.getPracticas().isEmpty()).count();
        model.addAttribute("alumnosEnPracticas", alumnosEnPracticas);
        
        // Empresas principales
        model.addAttribute("empresasPrincipales", empresas);
        
        // Datos para las gráficas
        List<String> nombresEmpresas = empresas.stream()
            .map(e -> e.getNombre())
            .collect(Collectors.toList());
        
        List<Integer> conteoEmpresas = empresas.stream()
            .map(e -> e.getPracticas() != null ? e.getPracticas().size() : 0)
            .collect(Collectors.toList());
        
        List<String> nombresCursos = cursos.stream()
            .map(c -> c.getNombre())
            .collect(Collectors.toList());
        
        List<Integer> conteoCursos = cursos.stream()
            .map(c -> c.getAlumnos() != null ? c.getAlumnos().size() : 0)
            .collect(Collectors.toList());
        
        model.addAttribute("nombresEmpresas", nombresEmpresas);
        model.addAttribute("conteoEmpresas", conteoEmpresas);
        model.addAttribute("nombresCursos", nombresCursos);
        model.addAttribute("conteoCursos", conteoCursos);
        
        return "estadisticas";
    }
}
