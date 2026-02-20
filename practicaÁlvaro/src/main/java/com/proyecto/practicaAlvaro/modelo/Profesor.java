package com.proyecto.practicaAlvaro.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

import com.proyecto.practicaAlvaro.enums.Rol;

@Entity
@Table(name = "profesores")
public class Profesor {

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

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.NORMAL;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private Set<Curso> cursos;

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

    public String getPassword() { 
    	return password; 
    }
    
    public void setPassword(String password) { 
    	this.password = password; 
    }

    public boolean esDirectivo() {
        return this.rol == Rol.DIRECTIVO;
    }
    
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Curso> getCursos() { 
    	return cursos; 
    }
    
    public void setCursos(Set<Curso> cursos) { 
    	this.cursos = cursos; 
    }
}
