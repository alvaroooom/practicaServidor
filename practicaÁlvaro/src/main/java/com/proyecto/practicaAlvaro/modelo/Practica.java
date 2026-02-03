package com.proyecto.practicaAlvaro.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "practica")
public class Practica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;

    // relacion n:1 con empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    // constructores
    public Practica() {}

    public Practica(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Empresa empresa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.empresa = empresa;
    }

    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }

    public String getTitulo() { 
    	return titulo; 
    }
    
    public void setTitulo(String titulo) { 
    	this.titulo = titulo; 
    }

    public String getDescripcion() { 
    	return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
    	this.descripcion = descripcion; 
    }

    public LocalDate getFechaInicio() { 
    	return fechaInicio; 
    }
    
    public void setFechaInicio(LocalDate fechaInicio) { 
    	this.fechaInicio = fechaInicio; 
    }

    public LocalDate getFechaFin() { 
    	return fechaFin; 
    }
    
    public void setFechaFin(LocalDate fechaFin) { 
    	this.fechaFin = fechaFin; 
    }

    public Empresa getEmpresa() { 
    	return empresa; 
    }
    
    public void setEmpresa(Empresa empresa) { 
    	this.empresa = empresa; 
    }
}

