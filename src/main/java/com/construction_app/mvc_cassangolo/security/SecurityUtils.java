package com.construction_app.mvc_cassangolo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.construction_app.mvc_cassangolo.model.Usuario;

public class SecurityUtils {

    public static Usuario getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        if (auth.getPrincipal() instanceof CustomUserDetails cud) {
            return cud.getUsuarioReal();
        }

        return null;
    }
}
