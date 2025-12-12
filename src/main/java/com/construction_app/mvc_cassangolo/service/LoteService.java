package com.construction_app.mvc_cassangolo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.construction_app.mvc_cassangolo.model.Lote;
import com.construction_app.mvc_cassangolo.repository.LoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;

    public Lote obtenerPorId(Integer id) {
        return loteRepository.findById(id).orElse(null);
    }

    public List<Lote> obtenerDisponibles() {
        return loteRepository.findByEstado("DISPONIBLE");
    }

    public List<Lote> obtenerPorEtapa(int etapa) {
        return loteRepository.findByEtapa(etapa);
    }

    public List<Lote> obtenerTodos() {
        return loteRepository.findAll();
    }

    public Lote obtenerPorEtapaManzanaNumero(Integer etapa, String manzana, String numero) {
        return loteRepository.findByEtapaAndManzanaAndNumero(etapa, manzana, numero);
    }

    public void guardar(Lote lote) {
        loteRepository.save(lote);
    }

}
