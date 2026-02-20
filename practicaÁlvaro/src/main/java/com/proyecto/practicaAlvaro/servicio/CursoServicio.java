package com.proyecto.practicaAlvaro.servicio;

import com.proyecto.practicaAlvaro.modelo.Curso;
import com.proyecto.practicaAlvaro.repositorio.CursoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServicio {

    private final CursoRepositorio cursoRepositorio;

    public CursoServicio(CursoRepositorio cursoRepositorio) {
        this.cursoRepositorio = cursoRepositorio;
    }

    public List<Curso> findAll() {
        return cursoRepositorio.findAll();
    }

    public Optional<Curso> findById(Long id) {
        return cursoRepositorio.findById(id);
    }

    public Curso save(Curso curso) {
        return cursoRepositorio.save(curso);
    }

    public void deleteById(Long id) {
        cursoRepositorio.deleteById(id);
    }
}

