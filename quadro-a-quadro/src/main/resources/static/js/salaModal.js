function abrirModal(id) {
    document.getElementById(id).showModal();
}

function fecharModal(id) {
    document.getElementById(id).close();
}

function abrirModalEditar(id, capacidade) {
    document.getElementById('editarNomeSala').value = id;
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