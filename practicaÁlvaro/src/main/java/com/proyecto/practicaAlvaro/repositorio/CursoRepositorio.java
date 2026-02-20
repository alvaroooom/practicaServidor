package com.proyecto.practicaAlvaro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.practicaAlvaro.modelo.Curso;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, Long> {

}

