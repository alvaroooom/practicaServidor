package com.proyecto.practicaAlvaro.servicio;

import com.proyecto.practicaAlvaro.modelo.Profesor;
import com.proyecto.practicaAlvaro.repositorio.ProfesorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorServicio {

    private final ProfesorRepositorio profesorRepositorio;
    private final PasswordEncoder passwordEncoder;

    public ProfesorServicio(ProfesorRepositorio profesorRepositorio, PasswordEncoder passwordEncoder) {
        this.profesorRepositorio = profesorRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Profesor> findAll() {
        return profesorRepositorio.findAll();
    }

    public Optional<Profesor> findById(Long id) {
        return profesorRepositorio.findById(id);
    }

    public Optional<Profesor> findByEmail(String email) {
        return profesorRepositorio.findAll().stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
    }

    public Profesor save(Profesor profesor) {
        if (profesor.getId() == null || profesor.getId() == 0) {
            profesor.setPassword(passwordEncoder.encode(profesor.getPassword()));
        } else {
            Profesor existente = profesorRepositorio.findById(profesor.getId())
                    .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

            if (profesor.getPassword() == null || profesor.getPassword().isBlank()) {
                profesor.setPassword(existente.getPassword());
            } else {
                profesor.setPassword(passwordEncoder.encode(profesor.getPassword()));
            }
        }
        return profesorRepositorio.save(profesor);
    }

    public void deleteById(Long id) {
        profesorRepositorio.deleteById(id);
    }
}

