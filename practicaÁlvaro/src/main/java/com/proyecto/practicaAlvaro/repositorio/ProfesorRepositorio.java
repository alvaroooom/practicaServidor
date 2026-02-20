package com.proyecto.practicaAlvaro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.practicaAlvaro.modelo.Profesor;

@Repository
public interface ProfesorRepositorio extends JpaRepository<Profesor, Long> {

}

