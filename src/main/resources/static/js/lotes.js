import { cargarDatosCotizador } from "./cotizador.js";

document.addEventListener("DOMContentLoaded", () => {

    const g = document.getElementById("tooltip");
    const bg = document.getElementById("tooltip-bg");
    const textEl = document.getElementById("tooltip-text");

    fetch("/lotes/etapa/2/json")
        .then(r => r.json())
        .then(lotes => {

            lotes.forEach(lote => {

                const svgLote = document.getElementById(`lote-${lote.manzana}-${lote.numero}`);
                if (!svgLote) return;

                // Dataset
                svgLote.dataset.id = lote.id;
                svgLote.dataset.numero = lote.numero;
                svgLote.dataset.manzana = lote.manzana;
                svgLote.dataset.m2 = lote.metrosCuadrados;
                svgLote.dataset.total = lote.metrosCuadrados * lote.precioM2;

                // Colores
                svgLote.classList.add("lote");
                svgLote.classList.add(lote.estado.toLowerCase());

                // Click
                svgLote.addEventListener("click", () => {

                    document.querySelectorAll(".lote")
                        .forEach(l => l.classList.remove("lote-seleccionado"));
                    svgLote.classList.add("lote-seleccionado");

                    const loteData = {
                        id: svgLote.dataset.id,
                        etapa: lote.etapa,
                        numero: svgLote.dataset.numero,
                        manzana: svgLote.dataset.manzana,
                        metrosCuadrados: svgLote.dataset.m2,
                        precioM2: lote.precioM2,
                        total: svgLote.dataset.total
                    };

                    cargarDatosCotizador(loteData);
                });
            });

            // ===== TOOLTIP =====
            document.querySelectorAll(".lote.disponible").forEach(lote => {

                lote.addEventListener("mousemove", e => {

                    const txt = `Mz ${lote.dataset.manzana} - Lote ${lote.dataset.numero} • ${lote.dataset.m2} m² • S/ ${lote.dataset.total}`;
                    textEl.textContent = txt;

                    const bbox = textEl.getBBox();
                    bg.setAttribute("x", bbox.x - 6);
                    bg.setAttribute("y", bbox.y - 4);
                    bg.setAttribute("width", bbox.width + 12);
                    bg.setAttribute("height", bbox.height + 8);

                    g.setAttribute("transform", `translate(${e.offsetX + 12}, ${e.offsetY - 12})`);
                    g.setAttribute("visibility", "visible");
                });

                lote.addEventListener("mouseleave", () => {
                    g.setAttribute("visibility", "hidden");
                });
            });

        })
        .catch(err => console.error("Error cargando lotes:", err));

});
