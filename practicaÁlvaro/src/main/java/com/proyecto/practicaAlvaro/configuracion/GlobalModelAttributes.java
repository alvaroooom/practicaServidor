package com.proyecto.practicaAlvaro.configuracion;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("esDirectivo")
    public boolean esDirectivo(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null) {
            return false;
        }
        return authorities.stream().anyMatch(auth -> "ROLE_DIRECTIVO".equals(auth.getAuthority())
                || "DIRECTIVO".equals(auth.getAuthority()));
    }
}
