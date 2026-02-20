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

    // relación n:1 con empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    // relación n:1 con alumno
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @Column(columnDefinition = "LONGTEXT")
    private String comentarios;

    public Practica() {}

    public Practica(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Empresa empresa, Alumno alumno) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.empresa = empresa;
        this.alumno = alumno;
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

    public Alumno getAlumno() { 
    	return alumno; 
    }
    
    public void setAlumno(Alumno alumno) { 
    	this.alumno = alumno; 
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
