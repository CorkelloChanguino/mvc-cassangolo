package com.construction_app.mvc_cassangolo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction_app.mvc_cassangolo.model.Lote;

public interface LoteRepository extends JpaRepository<Lote, Integer>{
    List<Lote> findByEtapa(Integer etapa);

    List<Lote> findByEstado(String estado);

    Lote findByEtapaAndManzanaAndNumero(Integer etapa, String manzana, String numero);
    
}
