package com.proyecto.practicaAlvaro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.practicaAlvaro.modelo.Alumno;
import java.util.List;

@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno, Long> {
	List<Alumno> findByCursoId(Long cursoId);
}

