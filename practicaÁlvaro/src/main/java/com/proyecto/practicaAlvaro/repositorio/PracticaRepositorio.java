package com.proyecto.practicaAlvaro.repositorio;

import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.modelo.Empresa;
import com.proyecto.practicaAlvaro.modelo.Practica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticaRepositorio extends JpaRepository<Practica, Long> {
	List<Practica> findByEmpresa(Empresa empresa);
	List<Practica> findByAlumno(Alumno alumno);
}

