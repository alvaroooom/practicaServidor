package com.proyecto.practicaAlvaro.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private String nombreTutor;

    @Column
    private String emailTutor;

    // relacion con las practicas
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Practica> practicas;

    // constructor
    public Empresa() {}
    
    public Empresa(String nombre, String nombreTutor, String emailTutor) {
        this.nombre = nombre;
        this.nombreTutor = nombreTutor;
        this.emailTutor = emailTutor;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreTutor() { 
    	return nombreTutor; 
    }
    
    public void setNombreTutor(String nombreTutor) { 
    	this.nombreTutor = nombreTutor; 
    }

    public String getEmailTutor() { 
    	return emailTutor; 
    }
    
    public void setEmailTutor(String emailTutor) { 
    	this.emailTutor = emailTutor; 
    }

    public List<Practica> getPracticas() { 
    	return practicas; 
    }
    
    public void setPracticas(List<Practica> practicas) { 
    	this.practicas = practicas; 
    }
}

