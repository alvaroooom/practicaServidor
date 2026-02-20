package com.proyecto.practicaAlvaro.servicio;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.practicaAlvaro.modelo.Profesor;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final ProfesorServicio profesorServicio;
    
    public UserDetailsServiceImpl(ProfesorServicio profesorServicio) {
        this.profesorServicio = profesorServicio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profesor profesor = profesorServicio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        
        return User.builder()
                .username(profesor.getEmail())
                .password(profesor.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + profesor.getRol().toString()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
