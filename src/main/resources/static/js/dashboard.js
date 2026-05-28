Chart.defaults.font.family = "'Nunito Sans', sans-serif";

const anoAtual = new Date().getFullYear();

new Chart(document.getElementById('kofiChart'), {
    type: 'line',
    data: {
        labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul','Ago', 'Set', 'Out', 'Nov', 'Dez'],
        datasets: [{
            label: 'Vendas mensais',
            data: [95, 102, 132, 153, 185, 10, 0, 0, 0, 0, 0, 0],
            borderColor: '#41210A',
            borderWidth: 4,
            pointRadius: 4,
            pointHoverRadius: 5,
            tension: 0.4,
            fill: true,
            backgroundColor: '#F1EADA'
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        resizeDelay: 100,
        plugins: {
            legend: { display: false },
            tooltip: {
                backgroundColor: '#3b2a1a',
                titleColor: '#f5ede0',
                bodyColor: '#f5ede0',
                padding: 10,
                cornerRadius: 6,
                displayColors: false
            },
            title: {
                display: true,
                text: 'Quantidade de pedidos - Ano: ' + anoAtual,
                color: '#3b2a1a',
                font: {
                    size: 16,
                    weight: 'regular'
                },
                padding: {
                    top: 0,
                    bottom: 10
                }
            }
        },
        scales: {
            x: {
                grid: { color: 'rgba(59,42,26,0.12)', lineWidth: 1 },
                border: { display: false },
                ticks: { color: '#3b2a1a', font: { size: 16 } }
            },
            y: {
                grid: { color: 'rgba(59,42,26,0.12)', lineWidth: 1 },
                border: { display: false },
                ticks: { color: '#3b2a1a', font: { size: 16 }, stepSize: 5 },
                beginAtZero: false
            }
        }
    }
});