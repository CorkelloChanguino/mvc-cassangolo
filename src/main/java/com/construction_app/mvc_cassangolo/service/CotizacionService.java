package com.construction_app.mvc_cassangolo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.construction_app.mvc_cassangolo.model.Cotizacion;
import com.construction_app.mvc_cassangolo.model.Lote;
import com.construction_app.mvc_cassangolo.model.Usuario;
import com.construction_app.mvc_cassangolo.repository.CotizacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;
    private final TemplateEngine templateEngine;

    // ============================================================================
    // 1. CALCULAR Y CREAR COTIZACIÓN
    // ============================================================================
    public Cotizacion calcularYCrearCotizacion(
            Lote lote,
            int descuentoEntero,
            double inicialIngresado,
            int meses,
            Usuario vendedor
    ) {

        double precioTotal = lote.getMetrosCuadrados() * lote.getPrecioM2();
        double precioConDescuento = precioTotal * (1 - (descuentoEntero / 100.0));

        double inicialMinimo = precioConDescuento * 0.20;
        if (inicialIngresado < inicialMinimo) {
            throw new IllegalArgumentException(
                    "El inicial debe ser al menos el 20%. Mínimo: S/ " + inicialMinimo
            );
        }

        double saldo = precioConDescuento - inicialIngresado;
        double cuotaMensual = saldo / meses;

        String numero = generarNumeroCotizacion();

        Cotizacion cot = new Cotizacion();
        cot.setNumeroCotizacion(numero);
        cot.setLote(lote);
        cot.setVendedor(vendedor);
        cot.setPrecioTotal(precioConDescuento);
        cot.setDescuento((double) descuentoEntero);
        cot.setInicial(inicialIngresado);
        cot.setMeses(meses);
        cot.setCuotaMensual(cuotaMensual);
        cot.setFecha(LocalDateTime.now());

        return cotizacionRepository.save(cot);
    }

    // ============================================================================
    // 2. GENERAR NÚMERO DE COTIZACIÓN
    // ============================================================================
    private String generarNumeroCotizacion() {
        return "COT-" + System.currentTimeMillis();
    }

    private String encodeImage(String path) throws IOException {
        ClassPathResource img = new ClassPathResource(path);
        byte[] bytes = img.getInputStream().readAllBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

// 3. GENERAR PDF
// ============================================================================
    public byte[] generarPdf(Cotizacion cot) {
        try {
            Context context = new Context();
            context.setVariable("cot", cot);

            // Render Thymeleaf → HTML
            String html = templateEngine.process("pdf/cotizacion-template", context);

            // ================================
            //   INYECTAR IMÁGENES BASE64 AQUÍ
            // ================================
            String logo = encodeImage("static/img/logo-c-a.png");
            html = html.replace("LOGO_BASE64", logo);

            String interbank = encodeImage("static/img/Interbank_logo.png");
            html = html.replace("INTERBANK_BASE64", interbank);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            // Mantienes tu baseUrl como está
            String baseUrl = getClass().getResource("/static/").toExternalForm();

            renderer.setDocumentFromString(html, baseUrl);
            renderer.layout();
            renderer.createPDF(baos);

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    public Cotizacion obtenerPorId(Integer id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
    }



        public List<Cotizacion> listarTodas() {
        return cotizacionRepository.findAll();
    }

    public List<Cotizacion> listarPorVendedor(Integer vendedorId) {
        return cotizacionRepository.findByVendedorId(vendedorId);
    }
}


