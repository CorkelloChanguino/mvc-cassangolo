package com.construction_app.mvc_cassangolo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.construction_app.mvc_cassangolo.model.Cotizacion;
import com.construction_app.mvc_cassangolo.model.Lote;
import com.construction_app.mvc_cassangolo.model.Usuario;
import com.construction_app.mvc_cassangolo.security.SecurityUtils;
import com.construction_app.mvc_cassangolo.service.CotizacionService;
import com.construction_app.mvc_cassangolo.service.LoteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cotizaciones")
public class CotizacionController {

    private final LoteService loteService;
    private final CotizacionService cotizacionService;

    // 2. Procesar la cotización y redirigir a descarga del PDF
    @PostMapping("/crear")
    public String crearCotizacion(
            @RequestParam Integer loteId,
            @RequestParam int descuento,
            @RequestParam(name = "inicialMonto") double inicial,
            @RequestParam int meses
    ) {

        Lote lote = loteService.obtenerPorId(loteId);
        if (!"DISPONIBLE".equals(lote.getEstado())) {
            throw new RuntimeException("Lote no disponible");
        }

        Usuario vendedor = SecurityUtils.getUsuarioActual();
        if (vendedor == null) {
            throw new RuntimeException("No se pudo obtener el usuario autenticado.");
        }

        var cot = cotizacionService.calcularYCrearCotizacion(
                lote, descuento, inicial, meses, vendedor
        );

        // Redirige a la descarga del PDF
        return "redirect:/cotizaciones/pdf/" + cot.getId();
    }

    // 3. Descargar PDF de una cotización existente
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Integer id) {

        var cot = cotizacionService.obtenerPorId(id);  // ← usa SERVICE

        byte[] pdf = cotizacionService.generarPdf(cot);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"cotizacion_" + id + ".pdf\"")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping
    public String listarCotizaciones(Model model, Authentication auth) {

        // Usuario logueado
        Usuario u = SecurityUtils.getUsuarioActual();

        List<Cotizacion> cotizaciones;

        if (u.getRol().getNombre().equals("ADMIN")) {
            cotizaciones = cotizacionService.listarTodas();
        } else {
            cotizaciones = cotizacionService.listarPorVendedor(u.getId());
        }

        model.addAttribute("cotizaciones", cotizaciones);

        return "cotizaciones";
    }

    @GetMapping("/cotizar/lote/{id}")
    public String cotizarLote(@PathVariable int id, Model model) {

        Lote lote = loteService.obtenerPorId(id);

        if (!"DISPONIBLE".equals(lote.getEstado())) {
            return "redirect:/lotes/mapa?error=no-disponible";
        }

        model.addAttribute("lote", lote);
        return "cotizador";
    }
}
