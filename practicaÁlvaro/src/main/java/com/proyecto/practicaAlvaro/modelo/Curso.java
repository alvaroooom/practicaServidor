package com.proyecto.practicaAlvaro.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(max = 50)
    @Column(unique = true)
    private String nombre;

    // la relación con el profesor que gestiona el curso
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    // los alumnos del curso
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Alumno> alumnos;

    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }

    public String getNombre() { 
    	return nombre; 
    }
    
    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    }

    public Profesor getProfesor() { 
    	return profesor; 
    }
    
    public void setProfesor(Profesor profesor) { 
    	this.profesor = profesor; 
    }

    public Set<Alumno> getAlumnos() { 
    	return alumnos; 
    }
    
    public void setAlumnos(Set<Alumno> alumnos) { 
    	this.alumnos = alumnos; 
    }
}

