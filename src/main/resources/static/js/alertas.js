document.addEventListener('DOMContentLoaded', () => {

    const alertas = ['msg-erro', 'msg-sucesso'];

    alertas.forEach(id => {
        const elemento = document.getElementById(id);

        if (elemento) {
            setTimeout(() => {
                elemento.classList.add('hidden');

                setTimeout(() => elemento.remove(), 500);
            }, 2000);
        }
    });
});