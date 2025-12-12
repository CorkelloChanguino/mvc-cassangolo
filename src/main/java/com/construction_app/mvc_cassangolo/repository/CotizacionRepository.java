package com.construction_app.mvc_cassangolo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction_app.mvc_cassangolo.model.Cotizacion;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer>{
    Optional<Cotizacion> findByNumeroCotizacion(String numeroCotizacion);
    List<Cotizacion> findByVendedorId(Integer vendedorId);
}
