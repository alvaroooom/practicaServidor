package com.proyecto.practicaAlvaro.servicio;

import com.proyecto.practicaAlvaro.modelo.Empresa;
import com.proyecto.practicaAlvaro.modelo.Practica;
import com.proyecto.practicaAlvaro.modelo.Alumno;
import com.proyecto.practicaAlvaro.repositorio.PracticaRepositorio;
import com.proyecto.practicaAlvaro.repositorio.AlumnoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;

@Service
public class PracticaServicio {

    private final PracticaRepositorio practicaRepositorio;
    private final AlumnoRepositorio alumnoRepositorio;
    private final com.proyecto.practicaAlvaro.repositorio.EmpresaRepositorio empresaRepositorio;
    
    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailFrom;

    private static final String DEFAULT_FROM_EMAIL = "pruebasAlvaroDev@gmail.com";
    private static final String PRACTICAS_SUBJECT = "PRACTICAS EN EMPRESA";

    public PracticaServicio(PracticaRepositorio practicaRepositorio, AlumnoRepositorio alumnoRepositorio, com.proyecto.practicaAlvaro.repositorio.EmpresaRepositorio empresaRepositorio) {
        this.practicaRepositorio = practicaRepositorio;
        this.alumnoRepositorio = alumnoRepositorio;
        this.empresaRepositorio = empresaRepositorio;
    }

    public List<Practica> obtenerTodas() {
        return practicaRepositorio.findAll();
    }

    public Optional<Practica> obtenerPorId(Long id) {
        return practicaRepositorio.findById(id);
    }

    public List<Practica> obtenerPorAlumno(Alumno alumno) {
        return practicaRepositorio.findByAlumno(alumno);
    }

    public List<Practica> obtenerPorEmpresa(Empresa empresa) {
        return practicaRepositorio.findByEmpresa(empresa);
    }

    public Practica guardarPractica(Practica practica, Alumno alumno, Empresa empresa) {
        practica.setAlumno(alumno);
        practica.setEmpresa(empresa);
        return save(practica);
    }

    public void eliminarPractica(Long id) {
        practicaRepositorio.deleteById(id);
    }

    public Practica save(Practica practica) {
        if (practica.getAlumno() != null && practica.getAlumno().getId() != null) {
            Alumno alumno = alumnoRepositorio.findById(practica.getAlumno().getId())
                    .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
            practica.setAlumno(alumno);
        }

        if (practica.getEmpresa() != null && practica.getEmpresa().getId() != null) {
            Empresa empresa = empresaRepositorio.findById(practica.getEmpresa().getId())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            practica.setEmpresa(empresa);
        }

        boolean esNueva = (practica.getId() == null);
        Practica guardada = practicaRepositorio.save(practica);
        
        if (esNueva && mailSender != null && practica.getAlumno() != null) {
            try {
                // Recuperar alumno y empresa para obtener datos completos
                alumnoRepositorio.findById(practica.getAlumno().getId()).ifPresent(alumno -> {
                    String nombreEmpresa = "la empresa asignada";
                    String nombreTutor = "";

                    if (practica.getEmpresa() != null && practica.getEmpresa().getId() != null) {
                        var empresa = empresaRepositorio.findById(practica.getEmpresa().getId()).orElse(null);
                        if (empresa != null) {
                            if (empresa.getNombre() != null && !empresa.getNombre().isBlank()) {
                                nombreEmpresa = empresa.getNombre();
                            }
                            if (empresa.getNombreTutor() != null && !empresa.getNombreTutor().isBlank()) {
                                nombreTutor = empresa.getNombreTutor();
                            }
                        }
                    }

                    SimpleMailMessage message = new SimpleMailMessage();
                    if (mailFrom != null && !mailFrom.isBlank()) {
                        message.setFrom(mailFrom);
                    } else {
                        message.setFrom(DEFAULT_FROM_EMAIL);
                    }
                    message.setTo(alumno.getEmail());
                    message.setSubject(PRACTICAS_SUBJECT);
                    message.setText(buildEmailBody(alumno.getNombre(), nombreEmpresa, nombreTutor, practica));
                    mailSender.send(message);
                });
            } catch (Exception e) {
                System.err.println("Error enviando email: " + e.getMessage());
            }
        }
        return guardada;
    }

    private String buildEmailBody(String nombreAlumno, String nombreEmpresa, String nombreTutor, Practica practica) {
        StringBuilder builder = new StringBuilder();
        builder.append("Hola ").append(nombreAlumno).append(",\n\n");
        builder.append("Se te ha asignado una practica en la empresa ").append(nombreEmpresa).append(".\n");
        if (nombreTutor != null && !nombreTutor.isBlank()) {
            builder.append("Tu tutor laboral es ").append(nombreTutor).append(".\n");
        }
        builder.append("Fecha de inicio: ").append(practica.getFechaInicio()).append("\n");
        builder.append("Fecha de fin: ").append(practica.getFechaFin()).append("\n");
        builder.append("\nSi tienes dudas, contacta con tu profesor.");
        return builder.toString();
    }

    public void deleteById(Long id) {
        practicaRepositorio.deleteById(id);
    }
}
