function abrirModal(id) {
    document.getElementById(id).showModal();
}

function fecharModal(id) {
    document.getElementById(id).close();
}

function abrirModalEditarBtn(btn) {
    const id = btn.getAttribute('data-id');
    const numSala = btn.getAttribute('data-numsala');
    const capacidade = btn.getAttribute('data-capacidade');
    abrirModalEditar(id, numSala, capacidade);
}

function abrirModalEditar(id, numSala, capacidade) {
    document.getElementById('editarNomeSala').value = numSala;
    document.getElementById('editarCapacidadeSala').value = capacidade;
    document.getElementById('formEditarSala').action = '/salas/editar/' + id;
    abrirModal('modalEditarSala');
}

// fecha dialog clicando fora
document.addEventListener('click', function(event) {
    const dialogs = document.querySelectorAll('dialog');
    dialogs.forEach(dialog => {
        if (event.target === dialog) dialog.close();
    });
});
