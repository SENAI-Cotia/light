document.addEventListener('DOMContentLoaded', () => {
    const erro = document.getElementById('msg-erro');
    if (erro) {
        setTimeout(() => {
            erro.classList.add('escondendo');
            setTimeout(() => erro.remove(), 500);
        }, 2000);
    }
});