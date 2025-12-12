package com.construction_app.mvc_cassangolo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction_app.mvc_cassangolo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByUsername(String username);
    
}
