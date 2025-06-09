function savePayment(){
    var isFormValid = validateForm();
    if(isFormValid){
        const fileInput = document.getElementById("proof");
        if (fileInput.files.length > 0) {
            const formData = new FormData();
            formData.append("file", fileInput.files[0]);

            $.ajax({
                type: "POST",
                url: "/payment/upload",
                data: formData,
                processData: false,
                contentType: false,
                success: function(fileName) {
                    // Una vez subido el archivo, guardar el pago
                    var payment = getObjectPaymentToSend();
                    payment.proof = fileName;
                    sendPayment(payment);
                },
                error: function(xhr, status, error) {
                    console.error("Error al subir el archivo:", xhr.responseText);
                    alert("Error al subir el archivo: " + (xhr.responseText || error));
                }
            });
        } else {
            // Si no hay archivo, guardar el pago directamente
            var payment = getObjectPaymentToSend();
            sendPayment(payment);
        }
    }
}

function sendPayment(payment){
    $.ajax({
        type: "POST",
        data: JSON.stringify(payment),
        url: "/payment/save",
        contentType: "application/json",
        success: function(response) {
            Swal.fire({
                title: '¡Éxito!',
                text: 'Pago guardado exitosamente',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            }).then(() => {
                // Limpiar el formulario
                resetForm();
                // Mostrar la tabla después de guardar
                mostrarDatos();
            });
        },
        error: function(xhr, status, error) {
            console.error("Error details:", xhr.responseText);
            let errorMessage = 'Error al guardar el pago';
            try {
                const response = JSON.parse(xhr.responseText);
                errorMessage = response.message || errorMessage;
            } catch (e) {
                errorMessage += ': ' + (xhr.responseText || error);
            }
            Swal.fire({
                title: 'Error',
                text: errorMessage,
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });
        }
    });
}

function removeErrorMessage(){
    document.querySelectorAll('.error-message').forEach(function(element) {
        element.classList.remove('error-message');
    });
    document.querySelectorAll('.message').forEach(function(element) {
        element.innerHTML = '';
    });
}

function scrollToError(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
}

function validateForm(){
    removeErrorMessage();
    var hasntErrorForm = true;

    const transactionAmount = document.getElementById("transactionAmount");
    if(!transactionAmount.value){
        transactionAmount.classList.add('error-message');
        document.getElementById("errorTransactionAmount").innerHTML = 'Este campo es obligatorio';
        scrollToError("transactionAmount");
        hasntErrorForm = false;
    } else if(!esNumeroMayorQueCero(transactionAmount.value)){
        transactionAmount.classList.add('error-message');
        document.getElementById("errorTransactionAmount").innerHTML = 'El monto debe ser un número válido y mayor que 0';
        scrollToError("transactionAmount");
        hasntErrorForm = false;
    }

    const referenceNumber = document.getElementById("referenceNumber");
    if(!referenceNumber.value){
        referenceNumber.classList.add('error-message');
        document.getElementById("errorReferenceNumber").innerHTML = 'Este campo es obligatorio';
        scrollToError("referenceNumber");
        hasntErrorForm = false;
    }

    const clientName = document.getElementById("clientName");
    if(!clientName.value){
        clientName.classList.add('error-message');
        document.getElementById("errorClientName").innerHTML = 'Este campo es obligatorio';
        scrollToError("clientName");
        hasntErrorForm = false;
    }

    const fiscalIdentification = document.getElementById("fiscalIdentification");
    if(!fiscalIdentification.value){
        fiscalIdentification.classList.add('error-message');
        document.getElementById("errorFiscalIdentification").innerHTML = 'Este campo es obligatorio';
        scrollToError("fiscalIdentification");
        hasntErrorForm = false;
    }

    const address = document.getElementById("address");
    if(!address.value){
        address.classList.add('error-message');
        document.getElementById("errorAddress").innerHTML = 'Este campo es obligatorio';
        scrollToError("address");
        hasntErrorForm = false;
    }

    const dateTransaction = document.getElementById("dateTransaction");
    if(!dateTransaction.value){
        dateTransaction.classList.add('error-message');
        document.getElementById("errorDateTransaction").innerHTML = 'Este campo es obligatorio';
        scrollToError("dateTransaction");
        hasntErrorForm = false;
    }

    const selectPaymentMethod = document.getElementById("selectPaymentMethod");
    if(!selectPaymentMethod.value){
        selectPaymentMethod.classList.add('error-message');
        document.getElementById("errorSelectPaymentMethod").innerHTML = 'Este campo es obligatorio';
        scrollToError("selectPaymentMethod");
        hasntErrorForm = false;
    }

    const selectPaymentState = document.getElementById("selectPaymentState");
    if(!selectPaymentState.value){
        selectPaymentState.classList.add('error-message');
        document.getElementById("errorSelectPaymentState").innerHTML = 'Este campo es obligatorio';
        scrollToError("selectPaymentState");
        hasntErrorForm = false;
    }

    return hasntErrorForm;
}

function esNumeroMayorQueCero(valor) {
    const regex = /^[1-9][0-9]*$/;
    return regex.test(valor);
}

function getObjectPaymentToSend() {
    return {
        transactionAmount: parseInt(document.getElementById("transactionAmount").value),
        numberReference: document.getElementById("referenceNumber").value,
        dateTransfer: document.getElementById("dateTransaction").value,
        methodPayment: document.getElementById("selectPaymentMethod").value,
        statePayment: document.getElementById("selectPaymentState").value,
        nameClient: document.getElementById("clientName").value,
        identificationFiscal: document.getElementById("fiscalIdentification").value,
        direction: document.getElementById("address").value,
        proof: document.getElementById("proof").files[0] ? document.getElementById("proof").files[0].name : null
    };
}

function buscarPorCedula() {
    const input = document.getElementById("busquedaCedula").value.toLowerCase();
    const filas = document.querySelectorAll("#tablaDatos tr");
    filas.forEach(fila => {
        const cedula = fila.children[0]?.textContent.toLowerCase();
        fila.style.display = cedula?.includes(input) ? "" : "none";
    });
}

// Variables globales para la paginación
let currentPage = 1;
const itemsPerPage = 3;
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
    // Mostrar el contenedor de la tabla y el indicador de carga
    document.getElementById("tablaContainer").style.display = "block";
    $('#loadingIndicator').show();
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
                        <td>
                            <button class="btn-edit" onclick="editarPago(${payment.idPayment})">
                                <i class="fas fa-edit"></i> Editar
                            </button>
                            <button class="btn-delete" onclick="eliminarPago(${payment.idPayment})">
                                <i class="fas fa-trash"></i> Eliminar
                            </button>
                        </td>
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
        }
    });
}

function actualizarPaginacion() {
    const paginationContainer = document.querySelector('.pagination');
    paginationContainer.innerHTML = '';

    if (totalPages <= 1) return;

    // Botón "Anterior"
    const prevButton = document.createElement('button');
    prevButton.innerHTML = '&laquo; Anterior';
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
    nextButton.innerHTML = 'Siguiente &raquo;';
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

// Inicializar los campos cuando se carga la página
document.addEventListener("DOMContentLoaded", function() {
    // Mostrar la tabla y cargar los datos
    mostrarDatos();
    
    // Asignar la fecha actual al campo de fecha de transacción
    const dateTransactionField = document.getElementById("dateTransaction");
    if (dateTransactionField) {
        const today = new Date().toISOString().split("T")[0]; 
        dateTransactionField.value = today;
    }

    // Obtener el siguiente número de referencia si estamos en la página de pagos
    const referenceNumberField = document.getElementById("referenceNumber");
    if (referenceNumberField) {
        fetch("/payment/nextReference")
            .then(response => response.text())
            .then(referenceNumber => {
                referenceNumberField.value = referenceNumber;
            })
            .catch(error => {
                console.error("Error al obtener el número de referencia:", error);
            });
    }
});

function eliminarPago(id) {
    Swal.fire({
        title: '¿Está seguro?',
        text: "Esta acción no se puede deshacer",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
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

let editingPaymentId = null;

function editarPago(id) {
    editingPaymentId = id;
    
    // Obtener los datos del pago
    $.ajax({
        type: "GET",
        url: "/payment/get/" + id,
        success: function(payment) {
            // Cargar los datos en el formulario
            document.getElementById("transactionAmount").value = payment.transactionAmount;
            document.getElementById("selectPaymentMethod").value = payment.methodPayment;
            document.getElementById("selectPaymentState").value = payment.statePayment;
            
            // Cargar los datos de solo lectura
            document.getElementById("dateTransaction").value = payment.dateTransfer;
            document.getElementById("referenceNumber").value = payment.numberReference;
            document.getElementById("clientName").value = payment.nameClient;
            document.getElementById("fiscalIdentification").value = payment.identificationFiscal;
            document.getElementById("address").value = payment.direction;
            
            // Deshabilitar los campos que no se pueden editar
            document.getElementById("dateTransaction").disabled = true;
            document.getElementById("referenceNumber").disabled = true;
            document.getElementById("clientName").disabled = true;
            document.getElementById("fiscalIdentification").disabled = true;
            document.getElementById("address").disabled = true;
            
            // Cambiar el texto del botón de guardar
            const saveButton = document.querySelector('.btn-save');
            if (saveButton) {
                saveButton.textContent = 'Actualizar';
                saveButton.onclick = function() { actualizarPago(); };
            }
            
            // Scroll al formulario
            document.querySelector('.formulario-container').scrollIntoView({ behavior: 'smooth' });
        },
        error: function(xhr, status, error) {
            console.error("Error al cargar los datos del pago:", xhr.responseText);
            Swal.fire({
                title: 'Error',
                text: 'Error al cargar los datos del pago: ' + (xhr.responseText || error),
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });
        }
    });
}

function actualizarPago() {
    if (!validateEditForm()) {
        return;
    }

    const fileInput = document.getElementById("proof");
    if (fileInput.files.length > 0) {
        // Si hay un nuevo archivo, subirlo primero
        const formData = new FormData();
        formData.append("file", fileInput.files[0]);

        $.ajax({
            type: "POST",
            url: "/payment/upload",
            data: formData,
            processData: false,
            contentType: false,
            success: function(fileName) {
                // Una vez subido el archivo, actualizar el pago
                const payment = getObjectPaymentToSend();
                payment.proof = fileName;
                sendUpdate(payment);
            },
            error: function(xhr, status, error) {
                console.error("Error al subir el archivo:", xhr.responseText);
                Swal.fire({
                    title: 'Error',
                    text: 'Error al subir el archivo: ' + (xhr.responseText || error),
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
            }
        });
    } else {
        // Si no hay nuevo archivo, actualizar directamente
        const payment = getObjectPaymentToSend();
        sendUpdate(payment);
    }
}

function sendUpdate(payment) {
    $.ajax({
        type: "PUT",
        url: "/payment/update/" + editingPaymentId,
        data: JSON.stringify(payment),
        contentType: "application/json",
        success: function(response) {
            Swal.fire({
                title: '¡Éxito!',
                text: 'Pago actualizado exitosamente',
                icon: 'success',
                confirmButtonText: 'Aceptar'
            }).then(() => {
                resetForm();
                mostrarDatos();
            });
        },
        error: function(xhr, status, error) {
            console.error("Error al actualizar el pago:", xhr.responseText);
            let errorMessage = 'Error al actualizar el pago';
            try {
                const response = JSON.parse(xhr.responseText);
                errorMessage = response.message || errorMessage;
            } catch (e) {
                errorMessage += ': ' + (xhr.responseText || error);
            }
            Swal.fire({
                title: 'Error',
                text: errorMessage,
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });
        }
    });
}

function validateEditForm() {
    removeErrorMessage();
    var hasntErrorForm = true;

    // Validar solo los campos editables
    const transactionAmount = document.getElementById("transactionAmount");
    if(!transactionAmount.value){
        transactionAmount.classList.add('error-message');
        document.getElementById("errorTransactionAmount").innerHTML = 'Este campo es obligatorio';
        scrollToError("transactionAmount");
        hasntErrorForm = false;
    } else if(!esNumeroMayorQueCero(transactionAmount.value)){
        transactionAmount.classList.add('error-message');
        document.getElementById("errorTransactionAmount").innerHTML = 'El monto debe ser un número válido y mayor que 0';
        scrollToError("transactionAmount");
        hasntErrorForm = false;
    }

    const selectPaymentMethod = document.getElementById("selectPaymentMethod");
    if(!selectPaymentMethod.value){
        selectPaymentMethod.classList.add('error-message');
        document.getElementById("errorSelectPaymentMethod").innerHTML = 'Este campo es obligatorio';
        scrollToError("selectPaymentMethod");
        hasntErrorForm = false;
    }

    const selectPaymentState = document.getElementById("selectPaymentState");
    if(!selectPaymentState.value){
        selectPaymentState.classList.add('error-message');
        document.getElementById("errorSelectPaymentState").innerHTML = 'Este campo es obligatorio';
        scrollToError("selectPaymentState");
        hasntErrorForm = false;
    }

    return hasntErrorForm;
}

function confirmarCancelar() {
    Swal.fire({
        title: '¿Está seguro?',
        text: "¿Desea cancelar el registro actual?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#dc3545',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, cancelar',
        cancelButtonText: 'No, mantener'
    }).then((result) => {
        if (result.isConfirmed) {
            resetForm();
            Swal.fire({
                title: 'Cancelado',
                text: 'El registro ha sido cancelado',
                icon: 'success',
                confirmButtonColor: '#a2a26e'
            });
        }
    });
}

function resetForm() {
    // Limpiar todos los campos del formulario
    document.getElementById("transactionAmount").value = '';
    document.getElementById("dateTransaction").value = new Date().toISOString().split('T')[0];
    document.getElementById("referenceNumber").value = '';
    document.getElementById("clientName").value = '';
    document.getElementById("fiscalIdentification").value = '';
    document.getElementById("address").value = '';
    document.getElementById("selectPaymentMethod").selectedIndex = 0;
    document.getElementById("selectPaymentState").selectedIndex = 0;
    document.getElementById("proof").value = '';

    // Habilitar todos los campos
    document.getElementById("dateTransaction").disabled = false;
    document.getElementById("referenceNumber").disabled = false;
    document.getElementById("clientName").disabled = false;
    document.getElementById("fiscalIdentification").disabled = false;
    document.getElementById("address").disabled = false;

    // Restaurar el botón de guardar
    const saveButton = document.querySelector('.btn-save');
    if (saveButton) {
        saveButton.textContent = 'Guardar';
        saveButton.onclick = function() { savePayment(); };
    }

    // Limpiar mensajes de error
    removeErrorMessage();

    // Obtener el siguiente número de referencia
    fetch("/payment/nextReference")
        .then(response => response.text())
        .then(referenceNumber => {
            document.getElementById("referenceNumber").value = referenceNumber;
        })
        .catch(error => {
            console.error("Error al obtener el número de referencia:", error);
        });
}

