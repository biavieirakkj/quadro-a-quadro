function abrirModalEditarFilmeBtn(btn) {
    const id = btn.getAttribute('data-id');
    abrirModalEditarFilme(id);
}

function abrirModalEditarFilme(id) {
    fetch('/filmes/dados/' + id)
        .then(response => response.json())
        .then(filme => {
            document.getElementById('editarTituloFilme').value = filme.titulo;
            document.getElementById('editarDuracaoFilme').value = filme.duracao;
            document.getElementById('editarClassificacaoFilme').value = filme.classificacao;
            document.getElementById('editarSinopseFilme').value = filme.sinopse ?? '';
            document.getElementById('editarStatusFilme').value = filme.status;

            const selectGeneros = document.getElementById('editarGenerosFilme');
            Array.from(selectGeneros.options).forEach(option => {
                option.selected = filme.generos.includes(option.value);
            });

            document.getElementById('formEditarFilme').action = '/filmes/editar/' + id;
            abrirModal('modalEditarFilme');
        });
}