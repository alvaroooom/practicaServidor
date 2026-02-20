package com.proyecto.practicaAlvaro.servicio;

import com.proyecto.practicaAlvaro.modelo.Empresa;
import com.proyecto.practicaAlvaro.repositorio.EmpresaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServicio {

    private final EmpresaRepositorio empresaRepositorio;
    
    public EmpresaServicio(EmpresaRepositorio empresaRepositorio) {
        this.empresaRepositorio = empresaRepositorio;
    }

    public List<Empresa> findAll() {
        return empresaRepositorio.findAll();
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepositorio.findById(id);
    }

    public Empresa save(Empresa empresa) {
        return empresaRepositorio.save(empresa);
    }

    public void deleteById(Long id) {
        empresaRepositorio.deleteById(id);
    }
}
