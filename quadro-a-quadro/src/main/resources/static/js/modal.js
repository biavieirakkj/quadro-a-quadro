function abrirModal(id) {
    document.getElementById(id).showModal();
}

function fecharModal(id) {
    document.getElementById(id).close();
}

// fecha dialog clicando fora
document.addEventListener('click', function(event) {
    const dialogs = document.querySelectorAll('dialog');
    dialogs.forEach(dialog => {
        if (event.target === dialog) dialog.close();
    });
});