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
    private String direccion;

    @Column
    private String telefono;

    // relacion con las practicas
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Practica> practicas;

    // constructor
    public Empresa() {}
    
    public Empresa(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
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

    public String getDireccion() { 
    	return direccion; 
    }
    
    public void setDireccion(String direccion) { 
    	this.direccion = direccion; 
    }

    public String getTelefono() { 
    	return telefono; 
    }
    
    public void setTelefono(String telefono) { 
    	this.telefono = telefono; 
    }

    public List<Practica> getPracticas() { 
    	return practicas; 
    }
    
    public void setPracticas(List<Practica> practicas) { 
    	this.practicas = practicas; 
    }
}

