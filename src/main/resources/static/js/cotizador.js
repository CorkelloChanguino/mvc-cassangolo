// ===============================
// CARGAR DATOS DESDE EL MAPA
// ===============================
export function cargarDatosCotizador(lote) {

    // Mostrar textos
    document.getElementById("etapaTexto").textContent = lote.etapa;
    document.getElementById("manzanaTexto").textContent = lote.manzana;
    document.getElementById("numeroTexto").textContent = lote.numero;
    document.getElementById("areaTexto").textContent = lote.metrosCuadrados;

    // Guardar ID real para backend
    document.getElementById("loteId").value = lote.id;

    // Calcular total
    const total = Number(lote.metrosCuadrados) * Number(lote.precioM2);

    // input → usar value
    document.getElementById("precioTotal").value = total;

    // Limpiar cálculos previos
    document.getElementById("precioFinal").value = "";
    document.getElementById("inicialMonto").value = "";
    document.getElementById("saldoMonto").value = "";
    document.getElementById("cuotaMensual").value = "";

    // Guardar lote en memoria
    window.loteSeleccionado = {
        ...lote,
        metrosCuadrados: Number(lote.metrosCuadrados),
        precioM2: Number(lote.precioM2)
    };
}

// ===============================
// RECÁLCULO AUTOMÁTICO
// ===============================
function recalcular() {

    if (!window.loteSeleccionado) return;

    const total = window.loteSeleccionado.metrosCuadrados * window.loteSeleccionado.precioM2;

    const d = parseFloat(document.getElementById("descuento").value) || 0;
    const i = parseFloat(document.getElementById("pagoInicial").value) || 20;
    const m = parseFloat(document.getElementById("meses")?.value) || 1;

    // PRECIO FINAL
    const precioFinal = total - (total * d / 100);
    setIfExists("precioFinal", precioFinal);

    // DESCUENTO EN SOLES
    const descuentoMonto = total * (d / 100);
    setIfExists("descuentoMonto", descuentoMonto.toFixed(2));

    // INICIAL
    const inicialMonto = precioFinal * (i / 100);
    setIfExists("inicialMonto", inicialMonto);

    // SALDO
    const saldoMonto = precioFinal - inicialMonto;
    setIfExists("saldoMonto", saldoMonto);

    // SALDO %
    const saldoPorcentaje = 100 - i;
    setIfExists("saldoPorcentaje", saldoPorcentaje.toFixed(2));

    // CUOTA
    const cuota = saldoMonto / m;
    setIfExists("cuotaMensual", cuota.toFixed(2));
}


// Helper para evitar errores si algún ID aún no existe
function setIfExists(id, value) {
    const el = document.getElementById(id);
    if (!el) return;

    if (el.tagName === "INPUT") {
        el.value = value;
    } else {
        el.textContent = value;
    }
}

// ===============================
// LISTENERS
// ===============================
document.getElementById("descuento")?.addEventListener("input", recalcular);
document.getElementById("pagoInicial")?.addEventListener("input", recalcular);
document.getElementById("meses")?.addEventListener("input", recalcular);
