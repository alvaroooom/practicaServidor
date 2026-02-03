package com.proyecto.practicaAlvaro.servicio;

import com.proyecto.practicaAlvaro.modelo.Profesor;
import com.proyecto.practicaAlvaro.repositorio.ProfesorRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorServicio {

    private final ProfesorRepositorio profesorRepositorio;

    public ProfesorServicio(ProfesorRepositorio profesorRepositorio) {
        this.profesorRepositorio = profesorRepositorio;
    }

    public List<Profesor> findAll() {
        return profesorRepositorio.findAll();
    }

    public Optional<Profesor> findById(Long id) {
        return profesorRepositorio.findById(id);
    }

    public Profesor save(Profesor profesor) {
        return profesorRepositorio.save(profesor);
    }

    public void deleteById(Long id) {
        profesorRepositorio.deleteById(id);
    }
}

