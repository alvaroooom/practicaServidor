package com.proyecto.practicaAlvaro.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.repositorio.AlumnoRepositorio;

@Service
public class AlumnoServicio {

    private final AlumnoRepositorio alumnoRepositorio;

    public AlumnoServicio(AlumnoRepositorio alumnoRepositorio) {
        this.alumnoRepositorio = alumnoRepositorio;
    }

    public List<Alumno> findAll() {
        return alumnoRepositorio.findAll();
    }

    public Optional<Alumno> findById(Long id) {
        return alumnoRepositorio.findById(id);
    }

    public Alumno save(Alumno alumno) {
        return alumnoRepositorio.save(alumno);
    }

    public void deleteById(Long id) {
        alumnoRepositorio.deleteById(id);
    }
}

