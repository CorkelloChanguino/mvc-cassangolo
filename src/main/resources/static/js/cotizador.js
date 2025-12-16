document.addEventListener("DOMContentLoaded", () => {

    // ===============================
    // INICIALIZACIÓN DESDE HTML
    // ===============================
    const totalInput = document.getElementById("precioTotal");
    const precioFinalInput = document.getElementById("precioFinal");
    const descuentoInput = document.getElementById("descuento");
    const descuentoMontoInput = document.getElementById("descuentoMonto");

    const pagoInicialPorcentajeInput = document.getElementById("pagoInicial");
    const inicialMontoInput = document.getElementById("inicialMonto");

    const saldoPorcentajeInput = document.getElementById("saldoPorcentaje");
    const saldoMontoInput = document.getElementById("saldoMonto");

    const mesesInput = document.getElementById("meses");
    const cuotaMensualInput = document.getElementById("cuotaMensual");

    let total = parseFloat(totalInput.value) || 0;

    // ===============================
    // CÁLCULO GENERAL
    // ===============================
    function recalcularDesdeTotal() {

        const descuentoPorc = parseFloat(descuentoInput.value) || 0;
        const descuentoMonto = total * (descuentoPorc / 100);
        const precioFinal = total - descuentoMonto;

        descuentoMontoInput.value = descuentoMonto.toFixed(2);
        precioFinalInput.value = precioFinal.toFixed(2);

        recalcularDesdeInicial();
    }

    // ===============================
    // DESDE INICIAL (%)
    // ===============================
    function recalcularDesdeInicial() {

        const precioFinal = parseFloat(precioFinalInput.value) || 0;
        let inicialPorc = parseFloat(pagoInicialPorcentajeInput.value) || 0;

        if (inicialPorc > 100) inicialPorc = 100;
        if (inicialPorc < 0) inicialPorc = 0;

        const inicialMonto = precioFinal * (inicialPorc / 100);
        const saldoMonto = precioFinal - inicialMonto;
        const saldoPorc = 100 - inicialPorc;

        inicialMontoInput.value = inicialMonto.toFixed(2);
        saldoMontoInput.value = saldoMonto.toFixed(2);
        saldoPorcentajeInput.value = saldoPorc.toFixed(2);

        recalcularCuota();
    }

    // ===============================
    // DESDE INICIAL (S/)
    // ===============================
    function recalcularDesdeInicialMonto() {

        const precioFinal = parseFloat(precioFinalInput.value) || 0;
        let inicialMonto = parseFloat(inicialMontoInput.value) || 0;

        if (inicialMonto > precioFinal) inicialMonto = precioFinal;
        if (inicialMonto < 0) inicialMonto = 0;

        const inicialPorc = (inicialMonto / precioFinal) * 100;
        const saldoMonto = precioFinal - inicialMonto;

        pagoInicialPorcentajeInput.value = inicialPorc.toFixed(2);
        saldoMontoInput.value = saldoMonto.toFixed(2);
        saldoPorcentajeInput.value = (100 - inicialPorc).toFixed(2);

        recalcularCuota();
    }

    // ===============================
    // DESDE SALDO (S/)
    // ===============================
    function recalcularDesdeSaldoMonto() {

        const precioFinal = parseFloat(precioFinalInput.value) || 0;
        let saldoMonto = parseFloat(saldoMontoInput.value) || 0;

        if (saldoMonto > precioFinal) saldoMonto = precioFinal;
        if (saldoMonto < 0) saldoMonto = 0;

        const inicialMonto = precioFinal - saldoMonto;
        const inicialPorc = (inicialMonto / precioFinal) * 100;

        inicialMontoInput.value = inicialMonto.toFixed(2);
        pagoInicialPorcentajeInput.value = inicialPorc.toFixed(2);
        saldoPorcentajeInput.value = (100 - inicialPorc).toFixed(2);

        recalcularCuota();
    }

    // ===============================
    // CUOTA
    // ===============================
    function recalcularCuota() {

        const saldoMonto = parseFloat(saldoMontoInput.value) || 0;
        const meses = parseInt(mesesInput.value) || 1;

        cuotaMensualInput.value = (saldoMonto / meses).toFixed(2);
    }

    // ===============================
    // LISTENERS
    // ===============================
    descuentoInput.addEventListener("input", recalcularDesdeTotal);

    pagoInicialPorcentajeInput.addEventListener("input", recalcularDesdeInicial);
    inicialMontoInput.addEventListener("input", recalcularDesdeInicialMonto);

    saldoMontoInput.addEventListener("input", recalcularDesdeSaldoMonto);

    mesesInput.addEventListener("input", recalcularCuota);

    // ===============================
    // ARRANQUE
    // ===============================
    recalcularDesdeTotal();
});
