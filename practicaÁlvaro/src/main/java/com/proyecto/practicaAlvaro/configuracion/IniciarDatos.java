package com.proyecto.practicaAlvaro.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;
import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.modelo.Curso;
import com.proyecto.practicaAlvaro.modelo.Empresa;
import com.proyecto.practicaAlvaro.modelo.Profesor;
import com.proyecto.practicaAlvaro.modelo.Practica;
import com.proyecto.practicaAlvaro.enums.Rol;
import com.proyecto.practicaAlvaro.repositorio.AlumnoRepositorio;
import com.proyecto.practicaAlvaro.repositorio.CursoRepositorio;
import com.proyecto.practicaAlvaro.repositorio.EmpresaRepositorio;
import com.proyecto.practicaAlvaro.repositorio.PracticaRepositorio;
import com.proyecto.practicaAlvaro.repositorio.ProfesorRepositorio;
import com.proyecto.practicaAlvaro.servicio.ProfesorServicio;

import java.time.LocalDate;

@Configuration
public class IniciarDatos {

    @Bean
    CommandLineRunner initData(ProfesorRepositorio profesorRepositorio, 
                               AlumnoRepositorio alumnoRepositorio, 
                               CursoRepositorio cursoRepositorio, 
                               EmpresaRepositorio empresaRepositorio, 
                               PracticaRepositorio practicaRepositorio,
                               ProfesorServicio profesorServicio) {
        return args -> {
            Faker faker = new Faker();
            
            // Crear profesores con datos aleatorios de Faker
            if (profesorRepositorio.count() == 0) {
                System.out.println("\n========== CREANDO USUARIOS DE PRUEBA ==========");
                
                // Usuario de prueba conocido
                Profesor adminTest = new Profesor();
                adminTest.setNombre("Admin");
                adminTest.setApellidos("Test");
                adminTest.setEmail("test@example.com");
                adminTest.setPassword("password123");
                adminTest.setRol(Rol.DIRECTIVO);
                profesorServicio.save(adminTest);
                
                // Usuarios aleatorios con Faker
                for (int i = 0; i < 10; i++) {
                    Profesor profesor = new Profesor();
                    profesor.setNombre(faker.name().firstName());
                    profesor.setApellidos(faker.name().lastName() + " " + faker.name().lastName());
                    profesor.setEmail(faker.internet().emailAddress());
                    profesor.setPassword("password123");
                    profesor.setRol(i % 2 == 0 ? Rol.DIRECTIVO : Rol.NORMAL);
                    profesorServicio.save(profesor);
                }
            }
            
            // Crear cursos
            if (cursoRepositorio.count() == 0) {
                String[] cursoNombres = {"DAM", "DAW", "ASIX", "SMR", "1DAW"};
                var profesores = profesorRepositorio.findAll();
                int profesorIndex = 0;
                
                for (String nombre : cursoNombres) {
                    Curso curso = new Curso();
                    curso.setNombre(nombre);
                    if (!profesores.isEmpty()) {
                        curso.setProfesor(profesores.get(profesorIndex % profesores.size()));
                        profesorIndex++;
                    }
                    cursoRepositorio.save(curso);
                }
            }
            
            // Crear empresas
            if (empresaRepositorio.count() == 0) {
                for (int i = 0; i < 8; i++) {
                    Empresa empresa = new Empresa();
                    empresa.setNombre(faker.company().name());
                    empresa.setNombreTutor(faker.name().fullName());
                    empresa.setEmailTutor(faker.internet().emailAddress());
                    empresaRepositorio.save(empresa);
                }
            }
            
            // Crear alumnos
            if (alumnoRepositorio.count() == 0) {
                var cursos = cursoRepositorio.findAll();
                for (int i = 0; i < 15; i++) {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(faker.name().firstName());
                    alumno.setApellidos(faker.name().lastName() + " " + faker.name().lastName());
                    alumno.setEmail(faker.internet().emailAddress());
                    alumno.setFechaNacimiento(LocalDate.of(
                        faker.number().numberBetween(2000, 2008),
                        faker.number().numberBetween(1, 12),
                        faker.number().numberBetween(1, 28)
                    ));
                    if (!cursos.isEmpty()) {
                        alumno.setCurso(cursos.stream().findAny().orElse(null));
                    }
                    alumnoRepositorio.save(alumno);
                }
            }
            
            // Crear prácticas
            if (practicaRepositorio.count() == 0) {
                var alumnos = alumnoRepositorio.findAll();
                var empresas = empresaRepositorio.findAll();
                
                for (int i = 0; i < Math.min(10, alumnos.size()); i++) {
                    if (!empresas.isEmpty()) {
                        Practica practica = new Practica();
                        practica.setAlumno(alumnos.get(i));
                        practica.setEmpresa(empresas.get((int) (Math.random() * empresas.size())));
                        practica.setTitulo(faker.job().title());
                        practica.setDescripcion(faker.lorem().paragraph(3));
                        practica.setFechaInicio(LocalDate.now().minusMonths(faker.number().numberBetween(1, 6)));
                        if (Math.random() > 0.5) {
                            practica.setFechaFin(LocalDate.now().minusDays(faker.number().numberBetween(0, 30)));
                        }
                        practica.setComentarios(faker.lorem().paragraph(2));
                        practicaRepositorio.save(practica);
                    }
                }
            }
        };
    }
}
