document.addEventListener('DOMContentLoaded', function() {
    const ctxEmpresas = document.getElementById('chartEmpresas');
    const ctxCursos = document.getElementById('chartCursos');

    if (ctxEmpresas) {
        new Chart(ctxEmpresas, {
            type: 'bar',
            data: {
                labels: datosGraficas.nombresEmpresas,
                datasets: [{
                    label: 'Alumnos Asignados',
                    data: datosGraficas.datosEmpresas,
                    backgroundColor: 'rgba(13, 110, 253, 0.5)',
                    borderColor: 'rgb(13, 110, 253)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    if (ctxCursos) {
        new Chart(ctxCursos, {
            type: 'pie',
            data: {
                labels: datosGraficas.nombresCursos,
                datasets: [{
                    data: datosGraficas.datosCursos,
                    backgroundColor: ['#0d6efd', '#6610f2', '#6f42c1', '#d63384']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }
});
