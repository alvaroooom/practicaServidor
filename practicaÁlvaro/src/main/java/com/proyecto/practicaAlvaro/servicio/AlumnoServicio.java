package com.proyecto.practicaAlvaro.servicio;

import java.time.LocalDate;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.Optional;
import com.opencsv.CSVReader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.modelo.Curso;
import com.proyecto.practicaAlvaro.repositorio.AlumnoRepositorio;
import com.proyecto.practicaAlvaro.repositorio.CursoRepositorio;

@Service
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;
    private final CursoRepositorio cursoRepositorio;

    public AlumnoServicio(AlumnoRepositorio alumnoRepositorio, CursoRepositorio cursoRepositorio) {
        this.alumnoRepositorio = alumnoRepositorio;
        this.cursoRepositorio = cursoRepositorio;
    }

    public List<Alumno> findAll() {
        return alumnoRepositorio.findAll();
    }

    public Optional<Alumno> findById(Long id) {
        return alumnoRepositorio.findById(id);
    }

    public List<Alumno> findByCursoId(Long cursoId) {
        return alumnoRepositorio.findByCursoId(cursoId);
    }

    public Alumno save(Alumno alumno) {
        return alumnoRepositorio.save(alumno);
    }

    public void deleteById(Long id) {
        alumnoRepositorio.deleteById(id);
    }

    public void importarAlumnos(MultipartFile file, Long cursoId) throws Exception {
        Curso curso = cursoRepositorio.findById(cursoId).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        
           try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
               CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            csvReader.readNext(); // Saltar cabecera si existe
            while ((line = csvReader.readNext()) != null) {
                // Formato esperado: Nombre, Apellidos, Email, FechaNacimiento (YYYY-MM-DD)
                if (line.length >= 4) {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(line[0].trim());
                    alumno.setApellidos(line[1].trim());
                    alumno.setEmail(line[2].trim());
                    try {
                        alumno.setFechaNacimiento(LocalDate.parse(line[3].trim()));
                    } catch (Exception e) {
                        // Si falla la fecha, puede que esté vacía o mal formato
                    }
                    alumno.setCurso(curso);
                    alumnoRepositorio.save(alumno);
                }
            }
        }
    }
}

