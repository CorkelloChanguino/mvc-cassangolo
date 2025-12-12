package com.construction_app.mvc_cassangolo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "lotes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"etapa", "manzana", "numero"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer etapa;

    private String manzana;

    private String numero;

    @Column(name = "metros_cuadrados")
    private Integer metrosCuadrados;

    @Column(name = "precio_m2")
    private Double precioM2;

    private String estado; // DISPONIBLE / VENDIDO / RESERVADO
}
