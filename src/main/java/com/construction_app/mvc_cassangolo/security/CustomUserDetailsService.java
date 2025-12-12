package com.construction_app.mvc_cassangolo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.construction_app.mvc_cassangolo.model.Usuario;
import com.construction_app.mvc_cassangolo.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = "ROLE_" + u.getRol().getNombre().toUpperCase();

        return new CustomUserDetails(
                u,
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
