package com.construction_app.mvc_cassangolo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.construction_app.mvc_cassangolo.model.Lote;
import com.construction_app.mvc_cassangolo.service.LoteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/lotes")
@RequiredArgsConstructor
public class AdminLoteController {

    private final LoteService loteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lotes", loteService.obtenerPorEtapa(2)); // o todos si quieres
        return "admin/lotes";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        Lote lote = loteService.obtenerPorId(id);
        model.addAttribute("lote", lote);
        return "admin/lote-detalle"; // crea esta vista
    }

    @PostMapping("/actualizar-estado/{id}")
    public String actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado
    ) {
        Lote lote = loteService.obtenerPorId(id);
        lote.setEstado(estado);
        loteService.guardar(lote); // agrega método guardar en service
        return "redirect:/admin/lotes";
    }
}
