package com.construction_app.mvc_cassangolo.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.construction_app.mvc_cassangolo.model.Usuario;
import com.construction_app.mvc_cassangolo.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void crear(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
    usuarioRepository.deleteById(id);
}
}
