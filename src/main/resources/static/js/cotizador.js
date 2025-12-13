// ===============================
// CARGAR DATOS DESDE EL MAPA
// ===============================
export function cargarDatosCotizador(lote) {

    // Mostrar textos
    document.getElementById("etapaTexto").textContent = lote.etapa;
    document.getElementById("manzanaTexto").textContent = lote.manzana;
    document.getElementById("numeroTexto").textContent = lote.numero;
    document.getElementById("areaTexto").textContent = lote.metrosCuadrados;

    // Guardar ID real
    document.getElementById("loteId").value = lote.id;

    const total = Number(lote.metrosCuadrados) * Number(lote.precioM2);
    document.getElementById("precioTotal").value = total;

    // Limpiar
    ["precioFinal", "inicialMonto", "saldoMonto", "cuotaMensual"].forEach(id => {
        document.getElementById(id).value = "";
    });

    // Guardar en memoria
    window.loteSeleccionado = {
        ...lote,
        metrosCuadrados: Number(lote.metrosCuadrados),
        precioM2: Number(lote.precioM2)
    };
}

// ===============================
// RECÁLCULO AUTOMÁTICO GENERAL
// ===============================
function recalcular() {

    if (!window.loteSeleccionado) return;

    const total = window.loteSeleccionado.metrosCuadrados * window.loteSeleccionado.precioM2;

    const d = parseFloat(document.getElementById("descuento").value) || 0;
    const i = parseFloat(document.getElementById("pagoInicial").value) || 20;
    const m = parseFloat(document.getElementById("meses")?.value) || 1;

    const precioFinal = total - (total * d / 100);
    set("precioFinal", precioFinal);

    const descuentoMonto = total * (d / 100);
    set("descuentoMonto", descuentoMonto.toFixed(2));

    const inicialMonto = precioFinal * (i / 100);
    const saldoMonto = precioFinal - inicialMonto;

    set("inicialMonto", inicialMonto);
    set("saldoMonto", saldoMonto);
    set("saldoPorcentaje", (100 - i).toFixed(2));

    const cuota = saldoMonto / m;
    set("cuotaMensual", cuota.toFixed(2));
}


// ===============================
// EDICIÓN MANUAL DE MONTOS
// ===============================

// Inicial S/
document.getElementById("inicialMonto").addEventListener("input", function () {
    if (!window.loteSeleccionado) return;

    const precioFinal = parseFloat(document.getElementById("precioFinal").value) || 0;
    let inicial = parseFloat(this.value) || 0;

    if (inicial > precioFinal) inicial = precioFinal;

    const porcentaje = (inicial / precioFinal) * 100;
    const saldo = precioFinal - inicial;

    set("pagoInicial", porcentaje.toFixed(2));
    set("saldoMonto", saldo);
    set("saldoPorcentaje", (100 - porcentaje).toFixed(2));

    const meses = parseFloat(document.getElementById("meses").value) || 1;
    set("cuotaMensual", (saldo / meses).toFixed(2));
});

// Saldo S/
document.getElementById("saldoMonto").addEventListener("input", function () {
    if (!window.loteSeleccionado) return;

    const precioFinal = parseFloat(document.getElementById("precioFinal").value) || 0;
    let saldo = parseFloat(this.value) || 0;

    if (saldo > precioFinal) saldo = precioFinal;

    const inicial = precioFinal - saldo;
    const porcentaje = (inicial / precioFinal) * 100;

    set("inicialMonto", inicial);
    set("pagoInicial", porcentaje.toFixed(2));
    set("saldoPorcentaje", (100 - porcentaje).toFixed(2));

    const meses = parseFloat(document.getElementById("meses").value) || 1;
    set("cuotaMensual", (saldo / meses).toFixed(2));
});


// ===============================
// Helper
// ===============================
function set(id, value) {
    const el = document.getElementById(id);
    if (!el) return;
    el.value = value;
}

// ===============================
// LISTENERS DE CÁLCULO NORMAL
// ===============================
document.getElementById("descuento")?.addEventListener("input", recalcular);
document.getElementById("pagoInicial")?.addEventListener("input", recalcular);
document.getElementById("meses")?.addEventListener("input", recalcular);

