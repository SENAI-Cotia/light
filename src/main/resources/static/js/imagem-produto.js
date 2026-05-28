const inputUrl = document.getElementById('campo-urlImagem');
const imgPreview = document.getElementById('img-preview');
const imgErro = document.getElementById('img-erro');
const fallbackUrl = imgPreview.getAttribute('data-fallback');

let timeoutId = null;
let carregandoUrl = null;

function validarUrl(url) {
    try {
        new URL(url);
        return true;
    } catch {
        return false;
    }
}

function atualizarPreview() {
    const url = inputUrl.value.trim();

    if (!url) {
        imgPreview.src = fallbackUrl;
        imgErro.style.display = 'none';
        carregandoUrl = null;
        return;
    }

    if (!validarUrl(url)) {
        imgErro.textContent = 'URL inválida. Digite uma URL válida.';
        imgErro.style.display = 'block';
        imgPreview.src = fallbackUrl;
        carregandoUrl = null;
        return;
    }

    carregandoUrl = url;
    imgErro.style.display = 'none';
    imgPreview.src = url;

    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
        if (carregandoUrl === url) {
            imgPreview.src = fallbackUrl;
            imgErro.textContent = 'Não foi possível carregar a imagem. Verifique a URL.';
            imgErro.style.display = 'block';
            carregandoUrl = null;
        }
    }, 5000);
}

function tratarErroImagem() {
    if (carregandoUrl !== null) {
        imgPreview.src = fallbackUrl;
        imgErro.textContent = 'Não foi possível carregar a imagem. Verifique a URL.';
        imgErro.style.display = 'block';
        clearTimeout(timeoutId);
        carregandoUrl = null;
    }
}

function tratarCarregamentoSucesso() {
    clearTimeout(timeoutId);
    carregandoUrl = null;
}

inputUrl.addEventListener('change', atualizarPreview);
inputUrl.addEventListener('input', atualizarPreview);
imgPreview.addEventListener('error', tratarErroImagem);
imgPreview.addEventListener('load', tratarCarregamentoSucesso);

atualizarPreview()