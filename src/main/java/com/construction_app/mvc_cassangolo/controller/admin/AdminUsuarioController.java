package com.construction_app.mvc_cassangolo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.construction_app.mvc_cassangolo.model.Rol;
import com.construction_app.mvc_cassangolo.model.Usuario;
import com.construction_app.mvc_cassangolo.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {

        usuario.setRol(new Rol(2, "VENDEDOR")); // Rol vendedor
        usuarioService.crear(usuario);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id); // o eliminar si elegiste FULL DELETE
        return "redirect:/admin/usuarios";
    }

}
