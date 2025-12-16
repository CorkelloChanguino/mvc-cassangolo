document.addEventListener("DOMContentLoaded", () => {

    const g = document.getElementById("tooltip");
    const bg = document.getElementById("tooltip-bg");
    const textEl = document.getElementById("tooltip-text");

    fetch("/lotes/etapa/2/json")
        .then(r => r.json())
        .then(lotes => {

            lotes.forEach(lote => {

                const svgLote = document.getElementById(
                    `lote-${lote.manzana}-${lote.numero}`
                );
                if (!svgLote) return;

                // ===== CLASES =====
                svgLote.classList.add("lote", lote.estado.toLowerCase());

                // ===== DATASET (tooltip) =====
                svgLote.dataset.manzana = lote.manzana;
                svgLote.dataset.numero = lote.numero;
                svgLote.dataset.m2 = lote.metrosCuadrados;
                svgLote.dataset.total = lote.metrosCuadrados * lote.precioM2;

                // ===== CLICK → COTIZADOR =====
                if (lote.estado === "DISPONIBLE") {
                    svgLote.addEventListener("click", () => {
                        window.location.href =
                            `/cotizaciones/cotizar/lote/${lote.id}`;
                    });
                }

                // ===== TOOLTIP SOLO DISPONIBLES =====
                if (lote.estado === "DISPONIBLE") {

                    svgLote.addEventListener("mousemove", e => {

                        const svg = svgLote.ownerSVGElement;
                        const pt = svg.createSVGPoint();
                        pt.x = e.clientX;
                        pt.y = e.clientY;

                        const cursor =
                            pt.matrixTransform(svg.getScreenCTM().inverse());

                        const txt =
                            `Mz ${svgLote.dataset.manzana} - ` +
                            `Lote ${svgLote.dataset.numero} • ` +
                            `${svgLote.dataset.m2} m² • ` +
                            `S/ ${Number(svgLote.dataset.total).toLocaleString("es-PE", {
                                minimumFractionDigits: 2
                            })}`;

                        textEl.textContent = txt;

                        const bbox = textEl.getBBox();
                        bg.setAttribute("x", bbox.x - 6);
                        bg.setAttribute("y", bbox.y - 4);
                        bg.setAttribute("width", bbox.width + 12);
                        bg.setAttribute("height", bbox.height + 8);

                        g.setAttribute(
                            "transform",
                            `translate(${cursor.x + 12}, ${cursor.y - 12})`
                        );
                        g.setAttribute("visibility", "visible");
                    });

                    svgLote.addEventListener("mouseleave", () => {
                        g.setAttribute("visibility", "hidden");
                    });
                }
            });
        })
        .catch(err => console.error("Error cargando lotes:", err));
});
