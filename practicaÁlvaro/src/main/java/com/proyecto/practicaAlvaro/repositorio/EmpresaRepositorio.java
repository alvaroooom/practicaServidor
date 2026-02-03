package com.proyecto.practicaAlvaro.repositorio;

import com.proyecto.practicaAlvaro.modelo.Empresa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
	List<Empresa> findByNombre(String nombre);
}

