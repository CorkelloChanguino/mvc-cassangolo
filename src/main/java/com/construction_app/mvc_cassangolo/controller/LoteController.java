package com.construction_app.mvc_cassangolo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.construction_app.mvc_cassangolo.model.Lote;
import com.construction_app.mvc_cassangolo.service.LoteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/lotes")
@RequiredArgsConstructor
public class LoteController {

    private final LoteService loteService;

    @GetMapping("/etapa2")
    public String vistaEtapa2(Model model) {
        model.addAttribute("lotes", loteService.obtenerDisponibles());
        return "etapa-2";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Lote obtenerLote(@PathVariable Integer id) {
        return loteService.obtenerPorId(id);
    }

    @GetMapping("/etapa/{num}/json")
    @ResponseBody
    public List<Lote> obtenerLotesPorEtapa(@PathVariable int num) {
        return loteService.obtenerPorEtapa(num);
    }

    @GetMapping("/detalle")
    @ResponseBody
    public Lote obtenerDetalle(
            @RequestParam Integer etapa,
            @RequestParam String manzana,
            @RequestParam String numero) {

        return loteService.obtenerPorEtapaManzanaNumero(etapa, manzana, numero);
    }

}
