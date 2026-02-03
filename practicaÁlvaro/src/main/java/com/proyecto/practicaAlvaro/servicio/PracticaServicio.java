package com.proyecto.practicaAlvaro.servicio;

import com.proyecto.practicaAlvaro.modelo.Practica;
import com.proyecto.practicaAlvaro.repositorio.PracticaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticaServicio {

	private final PracticaRepositorio practicaRepositorio;

    public PracticaServicio(PracticaRepositorio practicaRepositorio) {
        this.practicaRepositorio = practicaRepositorio;
    }

    public List<Practica> obtenerTodas() {
        return practicaRepositorio.findAll();
    }

    public Optional<Practica> obtenerPorId(Long id) {
        return practicaRepositorio.findById(id);
    }

    public Practica guardarPractica(Practica practica) {
        return practicaRepositorio.save(practica);
    }

    public void eliminarPractica(Long id) {
        practicaRepositorio.deleteById(id);
    }
}

