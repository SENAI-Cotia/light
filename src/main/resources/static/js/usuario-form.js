
const formulario = document.getElementById('form-usuarios');
const tituloFormulario = document.getElementById('titulo-formulario');
const usuarioIdInput = document.getElementById('usuario-id');
const nomeInput = document.getElementById('novo-usuario-nome');
const emailInput = document.getElementById('novo-usuario-email');
const senhaInput = document.getElementById('novo-usuario-senha');
const papelSelect = document.getElementById('papel');
const btnSalvar = document.getElementById('btn-salvar');
const btnCancelar = document.getElementById('btn-cancelar');
const btnLimpar = document.getElementById('btn-limpar');
const botoesEditar = document.querySelectorAll('.btn-editar');

// Estado padrão de criação
const estadoPadrao = {
    titulo: 'Cadastrar usuário',
    acao: '/cadastrar',
    id: '',
    nome: '',
    email: '',
    senha: '',
    papel: ''
};

// Editar usuario atual
botoesEditar.forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();

        const userItem = btn.closest('.user-item');
        const id = userItem.dataset.id;
        const nome = userItem.dataset.nome;
        const email = userItem.dataset.email;
        const papel = userItem.dataset.papel;

        usuarioIdInput.value = id;
        nomeInput.value = nome;
        emailInput.value = email;
        papelSelect.value = papel;

        senhaInput.value = '';
        senhaInput.parentElement.querySelector('label').textContent = 'Nova senha';


        tituloFormulario.textContent = 'Atualizar usuário';

        formulario.action = `/configuracoes/atualizar-usuario`;


        btnCancelar.classList.remove('hidden');
        btnLimpar.classList.add('hidden');


        formulario.scrollIntoView({ behavior: 'smooth' });
    });
});

// Cancelar edição
btnCancelar.addEventListener('click', (e) => {
    e.preventDefault();
    cancelarEdicao();
});

function cancelarEdicao() {

    formulario.reset();
    usuarioIdInput.value = '';


    senhaInput.parentElement.style.opacity = '1';
    senhaInput.parentElement.querySelector('label').textContent = 'Senha *';


    tituloFormulario.textContent = estadoPadrao.titulo;


    formulario.action = estadoPadrao.acao;


    btnCancelar.classList.add('hidden');
    btnLimpar.classList.remove('hidden');

}
