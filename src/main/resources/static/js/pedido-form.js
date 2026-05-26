
document.getElementById('form-busca').addEventListener('submit', function(e) {
    e.preventDefault();

    const termo = document.getElementById('input-busca').value.trim();
    const modoListagem = !painelItens.classList.contains('hidden');

    if (termo.length === 0) {
        window.location.href = `/atendimento?modo=${modoListagem ? 'pedidos' : 'produtos'}`;
        return;
    }

    if (termo.length < 3) return;

    if (modoListagem) {
        window.location.href = `/atendimento?busca=${encodeURIComponent(termo)}&modo=pedidos`;
    } else {
        window.location.href = `/atendimento?busca=${encodeURIComponent(termo)}&modo=produtos`;
    }
});

function limparBusca() {
    const modoListagem = !painelItens.classList.contains('hidden');
    window.location.href = `/atendimento?modo=${modoListagem ? 'pedidos' : 'produtos'}`;
}

const trocarConteudo = document.getElementById("main-content-switcher-button")
const painelPedido = document.querySelector(".painel-container")
const painelItens = document.querySelector(".pedidos-grid")

trocarConteudo.addEventListener("click", () => {
    painelPedido.classList.toggle("hidden")
    painelItens.classList.toggle("hidden")

    painelPedido.classList.contains("hidden") ? trocarConteudo.innerText = "Novo pedido" : trocarConteudo.innerText = "Listar pedidos"
})

// Filtro
document.querySelectorAll('.filtro-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();

        const filtro = btn.dataset.filtro;
        filtrar(filtro);

        // Remove ativo
        document.querySelectorAll('.filtro-btn').forEach(b => {
            b.classList.remove('ativo');
        });

        // Add ativo
        btn.classList.add('ativo');
    });
});


const itensSelecionados = new Map();


function adicionar(btn) {
    const card = btn.closest('.produto-card');
    const id    = card.dataset.id;
    const nome  = card.dataset.nome;
    const preco = parseFloat(card.dataset.preco);

    if (itensSelecionados.has(id)) {
        itensSelecionados.get(id).quantidade++;
    } else {
        itensSelecionados.set(id, { id, nome, preco, quantidade: 1 });
    }

    renderizarConta();
}

function remover(id) {
    itensSelecionados.delete(id);
    renderizarConta();
}


function mudarQuantidade(id, valor) {
    const qtd = parseInt(valor);
    if (qtd < 1 || isNaN(qtd)) return;
    itensSelecionados.get(id).quantidade = qtd;
    renderizarConta();
}


function renderizarConta() {
    const lista = document.getElementById('lista-selecionados');

    if (itensSelecionados.size === 0) {
        lista.innerHTML = '<p id="msg-vazia">Nenhum item adicionado.</p>';
        document.getElementById('valor-total').textContent = 'R$ 0,00';
        document.getElementById('total-pedido').value = '0';
        return;
    }

    let total = 0;
    let html  = '';

    itensSelecionados.forEach(item => {
        const subtotal = item.preco * item.quantidade;
        total += subtotal;

        html += `
    <div class="item-selecionado">
        <div class="item-info">
            <strong>${item.nome}</strong>
            <span>R$ ${item.preco.toFixed(2)} x ${item.quantidade} = R$ ${subtotal.toFixed(2)}</span>
        </div>
        <div class="item-acoes">
            <input type="number" min="1" value="${item.quantidade}"
                   onchange="mudarQuantidade('${item.id}', this.value)"
                   style="width: 50px"/>
            <button type="button" onclick="remover('${item.id}')">✕</button>
        </div>
    </div>
`;
    });

    lista.innerHTML = html;
    const totalFormatado = total.toFixed(2).replace('.', ',');
    document.getElementById('valor-total').textContent = 'R$ ' + totalFormatado;
    document.getElementById('total-pedido').value = total.toFixed(2);
}

function filtrar(categoria) {
    document.querySelectorAll('.produto-card').forEach(card => {
        const pertence = categoria === 'todos' || card.dataset.categoria === categoria;

        const parent = card.parentElement
        parent.style.display = pertence ? '' : 'none';
    });
}


const form = document.getElementById('form-pedido');

form.addEventListener('submit', function(e) {
    e.preventDefault();

    if (itensSelecionados.size === 0) {
        alert('Adicione ao menos um produto.');
        return;
    }

    const formaPagamentoSelecionada = document.querySelector('input[name="formaPagamento"]:checked');
    if (!formaPagamentoSelecionada) {
        alert('Selecione uma forma de pagamento.');
        return;
    }

    const container = document.getElementById('itens-ocultos');
    container.innerHTML = '';

    let index = 0;
    itensSelecionados.forEach(item => {
        criarOculto(container, `itens[${index}].produto.id`, item.id);
        criarOculto(container, `itens[${index}].quantidade`, item.quantidade);
        index++;
    });

    this.submit();
});

function criarOculto(container, name, value) {
    const input = document.createElement('input');
    input.type  = 'hidden';
    input.name  = name;
    input.value = value;
    container.appendChild(input);
}
