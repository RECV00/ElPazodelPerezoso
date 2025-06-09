// Variables globales para la paginación
let currentPage = 1;
const itemsPerPage = 4;
let totalItems = 0;
let totalPages = 0;

// Mapeo de métodos de pago
const metodosPago = {
    "1": "Tarjeta",
    "2": "Transferencia",
    "3": "Billetera Digital"
};

// Mapeo de estados de pago
const estadosPago = {
    "1": "Pendiente",
    "2": "Completado",
    "3": "Fallido"
};

function mostrarDatos() {
    // Mostrar el indicador de carga
    $('#loadingIndicator').show();
    $('.cabin-table').addClass('loading');
    $('#tablaDatos').hide();

    // Realizar la solicitud AJAX para obtener los datos
    $.ajax({
        type: "GET",
        url: "/payment/all",
        contentType: "application/json",
        success: function(response) {
            totalItems = response.length;
            totalPages = Math.ceil(totalItems / itemsPerPage);
            
            // Limpiar la tabla antes de agregar nuevos datos
            const tablaDatos = document.getElementById("tablaDatos");
            tablaDatos.innerHTML = "";

            // Calcular el rango de elementos a mostrar
            const startIndex = (currentPage - 1) * itemsPerPage;
            const endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            if (response.length === 0) {
                tablaDatos.innerHTML = '<tr><td colspan="8" class="no-data">No se encontraron pagos</td></tr>';
            } else {
                // Iterar sobre los datos y agregarlos a la tabla
                for(let i = startIndex; i < endIndex; i++) {
                    const payment = response[i];
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${payment.identificationFiscal}</td>
                        <td>${payment.transactionAmount}</td>
                        <td>${payment.dateTransfer}</td>
                        <td>${metodosPago[payment.methodPayment] || payment.methodPayment}</td>
                        <td>${payment.numberReference}</td>
                        <td>${estadosPago[payment.statePayment] || payment.statePayment}</td>
                        <td>${payment.proof ? `<a href="/uploads/${payment.proof}" target="_blank" class="btn-edit">Ver</a>` : "N/A"}</td>
                    `;
                    tablaDatos.appendChild(row);
                }
            }

            actualizarPaginacion();
            $('#tablaDatos').fadeIn(300);
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar los datos:", xhr.responseText);
            $('#tablaDatos').html('<tr><td colspan="8" class="no-data">Error al cargar los datos</td></tr>');
            $('#tablaDatos').show();
        },
        complete: function() {
            $('#loadingIndicator').hide();
            $('.cabin-table').removeClass('loading');
        }
    });
}

function actualizarPaginacion() {
    const paginationContainer = document.querySelector('.pagination');
    paginationContainer.innerHTML = '';

    if (totalPages <= 1) return;

    // Botón "Anterior"
    const prevButton = document.createElement('button');
    prevButton.innerHTML = '&laquo;';
    prevButton.disabled = currentPage === 1;
    prevButton.onclick = () => cambiarPagina(currentPage - 1);
    paginationContainer.appendChild(prevButton);

    // Calcular el rango de páginas a mostrar
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, startPage + 4);
    
    if (endPage - startPage < 4) {
        startPage = Math.max(1, endPage - 4);
    }

    // Primera página y elipsis si es necesario
    if (startPage > 1) {
        const firstPageButton = document.createElement('button');
        firstPageButton.textContent = '1';
        firstPageButton.onclick = () => cambiarPagina(1);
        paginationContainer.appendChild(firstPageButton);

        if (startPage > 2) {
            const ellipsis = document.createElement('button');
            ellipsis.textContent = '...';
            ellipsis.disabled = true;
            paginationContainer.appendChild(ellipsis);
        }
    }

    // Botones de página
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement('button');
        pageButton.textContent = i;
        pageButton.className = i === currentPage ? 'active' : '';
        pageButton.onclick = () => cambiarPagina(i);
        paginationContainer.appendChild(pageButton);
    }

    // Última página y elipsis si es necesario
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            const ellipsis = document.createElement('button');
            ellipsis.textContent = '...';
            ellipsis.disabled = true;
            paginationContainer.appendChild(ellipsis);
        }

        const lastPageButton = document.createElement('button');
        lastPageButton.textContent = totalPages;
        lastPageButton.onclick = () => cambiarPagina(totalPages);
        paginationContainer.appendChild(lastPageButton);
    }

    // Botón "Siguiente"
    const nextButton = document.createElement('button');
    nextButton.innerHTML = '&raquo;';
    nextButton.disabled = currentPage === totalPages;
    nextButton.onclick = () => cambiarPagina(currentPage + 1);
    paginationContainer.appendChild(nextButton);
}

function cambiarPagina(nuevaPagina) {
    if (nuevaPagina >= 1 && nuevaPagina <= totalPages) {
        currentPage = nuevaPagina;
        mostrarDatos();
    }
}

function buscarPorCedula() {
    const input = document.getElementById("busquedaCedula").value.toLowerCase();
    const filas = document.querySelectorAll("#tablaDatos tr");
    filas.forEach(fila => {
        const cedula = fila.children[0]?.textContent.toLowerCase();
        fila.style.display = cedula?.includes(input) ? "" : "none";
    });
}

function eliminarPago(id) {
    Swal.fire({
        title: '¿Está seguro?',
        text: "Esta acción no se puede deshacer",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#a2a26e',
        cancelButtonColor: '#dc3545',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "DELETE",
                url: "/payment/delete/" + id,
                success: function(response) {
                    Swal.fire({
                        title: '¡Eliminado!',
                        text: 'El pago ha sido eliminado exitosamente',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    });
                    mostrarDatos();
                },
                error: function(xhr, status, error) {
                    console.error("Error al eliminar el pago:", xhr.responseText);
                    Swal.fire({
                        title: 'Error',
                        text: 'Error al eliminar el pago: ' + (xhr.responseText || error),
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                }
            });
        }
    });
}

function editarPago(id) {
    window.location.href = '/payment/payments?id=' + id;
}

// Inicializar cuando se carga la página
document.addEventListener("DOMContentLoaded", function() {
    mostrarDatos();

    // Agregar evento de búsqueda con debounce
    let searchTimeout;
    document.getElementById("busquedaCedula").addEventListener("input", function() {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(buscarPorCedula, 300);
    });
}); 