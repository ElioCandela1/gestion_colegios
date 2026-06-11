    function cerrarModal(id) {
        document.getElementById(id).style.display = 'none';
    }

    // Mostrar automáticamente el modal si existe
    window.onload = function () {
        if (document.getElementById("notificacionModal")) {
            document.getElementById("notificacionModal").style.display = "block";
        }
        if (document.getElementById("confirmacionModal")) {
            document.getElementById("confirmacionModal").style.display = "block";
        }
    }
