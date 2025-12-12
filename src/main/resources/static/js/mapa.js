const mapa = document.getElementById("svg1");

let zoomLevel = 1;

let isPanning = false;
let startX = 0, startY = 0;
let currentX = 0, currentY = 0;

function applyTransform() {
    mapa.style.transform = `translate(${currentX}px, ${currentY}px) scale(${zoomLevel})`;
}

function zoomIn() {
    zoomLevel += 0.4;
    applyTransform();
}

function zoomOut() {
    zoomLevel -= 0.4;
    applyTransform();
}

mapa.addEventListener("mousedown", e => {
    isPanning = true;
    startX = e.clientX - currentX;
    startY = e.clientY - currentY;
});

document.addEventListener("mousemove", e => {
    if (!isPanning) return;
    currentX = e.clientX - startX;
    currentY = e.clientY - startY;
    applyTransform();
});

document.addEventListener("mouseup", () => {
    isPanning = false;
});
