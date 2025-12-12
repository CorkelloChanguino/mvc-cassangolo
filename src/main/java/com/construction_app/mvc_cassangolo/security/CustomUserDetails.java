package com.construction_app.mvc_cassangolo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.construction_app.mvc_cassangolo.model.Usuario;

public class CustomUserDetails extends User {

    private final Usuario usuarioReal;

    public CustomUserDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getUsername(), usuario.getPassword(), authorities);
        this.usuarioReal = usuario;
    }

    public Usuario getUsuarioReal() {
        return usuarioReal;
    }
}
