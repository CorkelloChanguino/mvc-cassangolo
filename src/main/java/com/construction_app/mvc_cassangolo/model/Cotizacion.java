package com.construction_app.mvc_cassangolo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cotizaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_cotizacion", nullable = false, unique = true)
    private String numeroCotizacion;

    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @Column(name = "precio_total")
    private Double precioTotal;

    private Double descuento;

    private Double inicial;

    private Integer meses;

    @Column(name = "cuota_mensual")
    private Double cuotaMensual;

    private LocalDateTime fecha;
}