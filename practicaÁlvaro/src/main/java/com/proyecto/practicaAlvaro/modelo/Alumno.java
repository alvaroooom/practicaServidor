package com.proyecto.practicaAlvaro.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50)
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 100)
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    @Column(unique = true)
    private String email;

    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    // relación con el curso
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // relación con prácticas
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Practica> practicas;

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

    public String getApellidos() { 
    	return apellidos; 
    }
    
    public void setApellidos(String apellidos) { 
    	this.apellidos = apellidos; 
    }

    public String getEmail() { 
    	return email; 
    }
    
    public void setEmail(String email) { 
    	this.email = email; 
    }

    public LocalDate getFechaNacimiento() { 
    	return fechaNacimiento; 
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) { 
    	this.fechaNacimiento = fechaNacimiento; 
    }

    public Curso getCurso() { 
    	return curso; 
    }
    public void setCurso(Curso curso) { 
    	this.curso = curso; 
    }

    public List<Practica> getPracticas() { 
    	return practicas; 
    }
    
    public void setPracticas(List<Practica> practicas) { 
    	this.practicas = practicas; 
    }
}
