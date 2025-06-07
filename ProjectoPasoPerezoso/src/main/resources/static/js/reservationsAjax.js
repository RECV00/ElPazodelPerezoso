function searchClient() {
    const identification = document.getElementById('clientIdentification').value.trim();
    const errorDiv = document.getElementById('clientError');
    const infoDiv = document.getElementById('clientInfo');
    
    // Reset UI
    errorDiv.style.display = 'none';
    infoDiv.style.display = 'none';
    
    if (!identification) {
        showError('Por favor ingrese una identificación');
        return;
    }

    fetch(`/booking/clients/findByIdentification?identification=${encodeURIComponent(identification)}`)
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            if (data.status === 'success') {
                // Asegúrate que estos IDs existen en tu HTML
                document.getElementById('clientName').textContent = `${data.name} ${data.lastName}`;
                document.getElementById('clientPhone').textContent = data.phone;
                document.getElementById('clientEmail').textContent = data.email;
                infoDiv.style.display = 'block';
            } else {
                throw new Error(data.message || 'Error desconocido');
            }
        })
        .catch(error => {
            showError(error.message);
        });
}
function validateForm() {
    // Validar que si el servicio de tour está seleccionado, se haya elegido un tour
    const tourServiceChecked = document.getElementById('tourServiceCheckbox').checked;
    const tourSelected = document.querySelector('input[name="selectedTourId"]:checked') !== null;
    
//    if (tourServiceChecked && !tourSelected) {
//        alert('Por favor seleccione un tour para continuar');
//        return false;
//    }
    
    // Depuración: Mostrar datos que se enviarán
    const formData = {
        clientIdentification: document.getElementById('clientIdentification').value,
        services: Array.from(document.querySelectorAll('input[name="services"]:checked')).map(cb => cb.value),
        selectedTourId: document.querySelector('input[name="selectedTourId"]:checked')?.value
    };
    console.log("Datos a enviar:", formData);
    
    return confirm("¿Estás seguro de que deseas agregar esta booking?");
}
function showError(message) {
    const errorDiv = document.getElementById('clientError');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
//SELECCIONAR TOURS-----------------------------------------------------------------------------------

//// Variables globales
//let selectedTours = [];
//
//// Función para abrir ventana de selección de tours
//function openTourSelectionWindow() {
//    const tourWindow = window.open('/booking/tours/listForReservation', 'TourSelection', 
//        'width=900,height=600,scrollbars=yes,resizable=yes');
//    
//    window.addEventListener('message', function(event) {
//        if (event.data.type === 'tourSelection') {
//            handleIncomingTourSelection(event.data.tourIds);
//        }
//    });
//}
//
//// Función para manejar la selección de tours desde la ventana
//function handleIncomingTourSelection(tourIds) {
//    selectedTours = tourIds || [];
//    updateSelectedToursList();
//    updateHiddenTourIds();
//    
//    // Opcional: Mostrar detalles de los tours seleccionados
//    if (selectedTours.length > 0) {
//        fetch(`/booking/tours/getMultiple?ids=${selectedTours.join(',')}`)
//            .then(response => response.json())
//            .then(tours => {
//                displayTourDetails(tours);
//            });
//    }
//}
//
//// Función para mostrar detalles de tours
//function displayTourDetails(tours) {
//    const container = document.getElementById('selectedToursDisplay');
//    const list = document.getElementById('selectedToursList');
//    
//    list.innerHTML = '';
//    tours.forEach(tour => {
//        const li = document.createElement('li');
//        li.style.padding = '8px';
//        li.style.borderBottom = '1px solid #eee';
//        li.style.display = 'flex';
//        li.style.justifyContent = 'space-between';
//        li.innerHTML = `
//            <span>${tour.nameTour} ($${tour.price.toFixed(2)})</span>
//            <button onclick="removeTour(${tour.id_tour})" 
//                    style="background-color: #f44336; color: white; 
//                           border: none; padding: 2px 8px; border-radius: 3px;">
//                Quitar
//            </button>
//        `;
//        list.appendChild(li);
//    });
//    
//    container.style.display = 'block';
//}
//
//// Función para eliminar un tour
//function removeTour(tourId) {
//    selectedTours = selectedTours.filter(id => id !== tourId);
//    updateHiddenTourIds();
//    if (selectedTours.length === 0) {
//        document.getElementById('selectedToursDisplay').style.display = 'none';
//    } else {
//        fetch(`/booking/tours/getMultiple?ids=${selectedTours.join(',')}`)
//            .then(response => response.json())
//            .then(tours => {
//                displayTourDetails(tours);
//            });
//    }
//}
//
//// Actualizar campo oculto con IDs
//function updateHiddenTourIds() {
//    document.getElementById('selectedTourIds').value = selectedTours.join(',');
//}
//
//// Función de búsqueda de tours
//function searchTours(searchTerm) {
//    if (searchTerm.length < 2) {
//        document.getElementById('tourResults').innerHTML = '<p>Ingrese al menos 2 caracteres</p>';
//        return;
//    }
//    
//    fetch(`/booking/tours/search?name=${encodeURIComponent(searchTerm)}`)
//        .then(response => response.json())
//        .then(tours => {
//            const resultsDiv = document.getElementById('tourResults');
//            resultsDiv.innerHTML = '';
//            
//            if (tours.length === 0) {
//                resultsDiv.innerHTML = '<p>No se encontraron tours</p>';
//                return;
//            }
//            
//            const table = document.createElement('table');
//            table.style.width = '100%';
//            table.style.borderCollapse = 'collapse';
//            
//            // Cabecera
//            const thead = document.createElement('thead');
//            thead.innerHTML = `
//                <tr>
//                    <th style="text-align: left; padding: 8px; border-bottom: 1px solid #ddd;">Nombre</th>
//                    <th style="text-align: left; padding: 8px; border-bottom: 1px solid #ddd;">Precio</th>
//                    <th style="text-align: left; padding: 8px; border-bottom: 1px solid #ddd;">Duración</th>
//                    <th style="text-align: left; padding: 8px; border-bottom: 1px solid #ddd;">Acción</th>
//                </tr>
//            `;
//            table.appendChild(thead);
//            
//            // Cuerpo
//            const tbody = document.createElement('tbody');
//            tours.forEach(tour => {
//                const isSelected = selectedTours.includes(tour.id_tour);
//                const row = document.createElement('tr');
//                row.style.borderBottom = '1px solid #eee';
//                row.innerHTML = `
//                    <td style="padding: 8px;">${tour.nameTour}</td>
//                    <td style="padding: 8px;">$${tour.price.toFixed(2)}</td>
//                    <td style="padding: 8px;">${tour.duration} horas</td>
//                    <td style="padding: 8px;">
//                        <button onclick="addTourToSelection(${tour.id_tour}, '${tour.nameTour}', ${tour.price})"
//                                style="padding: 4px 8px; background-color: ${isSelected ? '#ccc' : '#4CAF50'}; 
//                                       color: white; border: none; border-radius: 3px;"
//                                ${isSelected ? 'disabled' : ''}>
//                            ${isSelected ? 'Agregado' : 'Agregar'}
//                        </button>
//                    </td>
//                `;
//                tbody.appendChild(row);
//            });
//            
//            table.appendChild(tbody);
//            resultsDiv.appendChild(table);
//        });
//}
//
//// Función para agregar un tour desde la búsqueda
//function addTourToSelection(tourId, tourName, price) {
//    if (!selectedTours.includes(tourId)) {
//        selectedTours.push(tourId);
//        updateHiddenTourIds();
//        
//        const displayDiv = document.getElementById('selectedToursDisplay');
//        const list = document.getElementById('selectedToursList');
//        
//        if (displayDiv.style.display === 'none') {
//            displayDiv.style.display = 'block';
//        }
//        
//        const li = document.createElement('li');
//        li.style.padding = '8px';
//        li.style.borderBottom = '1px solid #eee';
//        li.style.display = 'flex';
//        li.style.justifyContent = 'space-between';
//        li.innerHTML = `
//            <span>${tourName} ($${price.toFixed(2)})</span>
//            <button onclick="removeTour(${tourId})" 
//                    style="background-color: #f44336; color: white; 
//                           border: none; padding: 2px 8px; border-radius: 3px;">
//                Quitar
//            </button>
//        `;
//        list.appendChild(li);
//    }
//}

//
//
////// Variables globales para manejar el debounce
//let timeoutId;
//
//// Función para retrasar la ejecución de filtros (debounce)
//function ejecutarConRetraso(func, delay) {
//    clearTimeout(timeoutId);
//    timeoutId = setTimeout(func, delay);
//}
//
//// Funciones de filtrado en tiempo real
//function filtrarPorCliente() {
//    const elementoCargando = document.getElementById('client-filter-loading');
//    elementoCargando.style.display = 'inline-block';
//    
//    ejecutarConRetraso(() => {
//        const cedulaCliente = document.getElementById('clientId').value;
//        let url = '/booking/listaReservas?page=0';
//        
//        if (cedulaCliente) {
//            url = `/booking/search?clientId=${encodeURIComponent(cedulaCliente)}&page=0`;
//        }
//        
//        obtenerReservaciones(url, elementoCargando);
//    }, 500); // 500ms de retraso después de la última tecla
//}
//
//function filtrarPorEstado() {
//    const elementoCargando = document.getElementById('status-filter-loading');
//    elementoCargando.style.display = 'inline-block';
//    
//    ejecutarConRetraso(() => {
//        const estado = document.getElementById('status').value;
//        let url = '/booking/listaReservas?page=0';
//        
//        if (estado) {
//            url = `/booking/search?status=${encodeURIComponent(estado)}&page=0`;
//        }
//        
//        obtenerReservaciones(url, elementoCargando);
//    }, 300); // 300ms de retraso después del cambio
//}
//
//function validarRangoFechas() {
//    const fechaInicio = document.getElementById('startDate').value;
//    const fechaFin = document.getElementById('endDate').value;
//    const elementoError = document.getElementById('date-error');
//    const elementoCargando = document.getElementById('date-filter-loading');
//    
//    // Validaciones
//    if ((fechaInicio && !fechaFin) || (!fechaInicio && fechaFin)) {
//        elementoError.textContent = 'Debe seleccionar ambas fechas para filtrar';
//        elementoError.style.display = 'inline-block';
//        return false;
//    }
//    
//    if (fechaInicio && fechaFin && fechaInicio > fechaFin) {
//        elementoError.textContent = 'La fecha de inicio no puede ser mayor que la fecha final';
//        elementoError.style.display = 'inline-block';
//        return false;
//    }
//    
//    elementoError.style.display = 'none';
//    elementoCargando.style.display = 'inline-block';
//    
//    ejecutarConRetraso(() => {
//        let url = '/booking/listaReservas?page=0';
//        
//        if (fechaInicio && fechaFin) {
//            url = `/booking/search?startDate=${fechaInicio}&endDate=${fechaFin}&page=0`;
//        }
//        
//        obtenerReservaciones(url, elementoCargando);
//    }, 500); // 500ms de retraso después del cambio
//}

// Función mejorada para obtener reservaciones
//function obtenerReservaciones(url, elementoCargando = null) {
//    fetch(url, {
//        headers: {
//            'Accept': 'text/html',
//            'X-Requested-With': 'XMLHttpRequest'
//        }
//    })
//    .then(respuesta => {
//        if (!respuesta.ok) {
//            throw new Error(`Error ${respuesta.status}: ${respuesta.statusText}`);
//        }
//        return respuesta.text();
//    })
//    .then(html => {
//        const parser = new DOMParser();
//        const doc = parser.parseFromString(html, 'text/html');
//        
//        // Reemplazar el contenedor completo de reservaciones
//        const nuevoContenedor = doc.getElementById('bookings-container');
//        if (nuevoContenedor) {
//            const contenedorActual = document.getElementById('bookings-container');
//            contenedorActual.innerHTML = nuevoContenedor.innerHTML;
//        }
//        
//        if (elementoCargando) {
//            elementoCargando.style.display = 'none';
//        }
//    })
//    .catch(error => {
//        console.error('Error al cargar reservaciones:', error);
//        if (elementoCargando) {
//            elementoCargando.style.display = 'none';
//        }
//        
//        Swal.fire({
//            title: 'Error',
//            text: 'Error al cargar las reservaciones. Por favor, intente nuevamente.',
//            icon: 'error'
//        });
//    });
//}

// Función para reiniciar todos los filtros
function reiniciarFiltros() {
    document.getElementById('clientId').value = '';
    document.getElementById('status').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    document.getElementById('date-error').style.display = 'none';
    
    obtenerReservaciones('/booking/listaReservas?page=0');
}
//
////// Función para filtrar por ID de cliente
//function filterByClient() {
//    const clientId = document.getElementById('id_client').value;
//    const loadingElement = document.getElementById('client-filter-loading');
//    
//    loadingElement.style.display = 'inline-block';
//    
//    if (!clientId) {
//        fetchReservations('/booking/listaReservas');
//        loadingElement.style.display = 'none';
//        return;
//    }
//    
//    const url = `/booking/search?clientId=${clientId}`;
//    fetchReservations(url, loadingElement);
//}
//
//// Función para filtrar por estado
//function filterByStatus() {
//    const status = document.getElementById('reservationStatus').value;
//    const loadingElement = document.getElementById('status-filter-loading');
//    
//    loadingElement.style.display = 'inline-block';
//    
//    let url = '/booking/listaReservas';
//    if (status) {
//        url = `/booking/search?status=${encodeURIComponent(status)}`;
//    }
//    
//    fetchReservations(url, loadingElement);
//}
//
//// Función para filtrar por rango de fechas
//function filterByDateRange() {
//    const startDate = document.getElementById('startDate').value;
//    const endDate = document.getElementById('endDate').value;
//    const loadingElement = document.getElementById('date-filter-loading');
//    const errorElement = document.getElementById('date-error');
//    
//    if ((startDate && !endDate) || (!startDate && endDate)) {
//        errorElement.textContent = 'Debe seleccionar ambas fechas para filtrar';
//        errorElement.style.display = 'inline-block';
//        return;
//    }
//    
//    errorElement.style.display = 'none';
//    loadingElement.style.display = 'inline-block';
//    
//    if (!startDate && !endDate) {
//        fetchReservations('/booking/listaReservas', loadingElement);
//        return;
//    }
//    
//    const url = `/bookings/search?startDate=${startDate}&endDate=${endDate}`;
//    fetchReservations(url, loadingElement);
//}
//
//// Función genérica para obtener reservaciones via AJAX
//function fetchReservations(url, loadingElement = null) {
//    fetch(url, {
//        headers: {
//            'X-Requested-With': 'XMLHttpRequest'
//        }
//    })
//    .then(response => {
//        if (!response.ok) {
//            throw new Error('Error en la respuesta del servidor');
//        }
//        return response.text();
//    })
//    .then(html => {
//        const parser = new DOMParser();
//        const doc = parser.parseFromString(html, 'text/html');
//        const newTable = doc.querySelector('.bookings-table');
//        
//        if (newTable) {
//            const currentTable = document.querySelector('.bookings-table');
//            currentTable.parentNode.replaceChild(newTable, currentTable);
//        }
//        
//        if (loadingElement) {
//            loadingElement.style.display = 'none';
//        }
//    })
//    .catch(error => {
//        console.error('Error al cargar reservaciones:', error);
//        if (loadingElement) {
//            loadingElement.style.display = 'none';
//        }
//        
//        const errorContainer = document.querySelector('.error-message');
//        if (errorContainer) {
//            errorContainer.textContent = 'Error al cargar las reservaciones. Por favor, intente nuevamente.';
//            errorContainer.style.display = 'block';
//        }
//    });
//}
//
//// Función para mostrar todas las reservaciones
//function showAllReservations() {
//    fetchReservations('/booking/listaReservas');
//    
//    document.getElementById('id_client').value = '';
//    document.getElementById('reservationStatus').value = '';
//    document.getElementById('startDate').value = '';
//    document.getElementById('endDate').value = '';
//    document.getElementById('date-error').style.display = 'none';
//}
//
//// Función para cambiar el tamaño de página
//function changePageSize(size) {
//    const url = new URL(window.location.href);
//    url.searchParams.set('size', size);
//    url.searchParams.set('page', 0);
//    window.location.href = url.toString();
//}
//
//// Array global para almacenar los tours seleccionados
//let selectedTours = [];
//
//// Función para confirmar la selección de tours
//function confirmTourSelection() {
//    if (selectedTours.length === 0) {
//        Swal.fire({
//            title: 'Advertencia',
//            text: 'Por favor seleccione al menos un tour antes de confirmar.',
//            icon: 'warning'
//        });
//        return;
//    }
//    
//    document.getElementById('selectedTourIds').value = selectedTours.map(t => t.id).join(',');
//    updateSelectedToursList();
//    
//    document.getElementById('tourSelectorContainer').style.display = 'none';
//    
//    Swal.fire({
//        title: 'Éxito',
//        text: `Ha confirmado ${selectedTours.length} tour(s). Puede continuar con el resto del formulario.`,
//        icon: 'success'
//    });
//}
//
//// Función para actualizar la lista visual de tours seleccionados
//function updateSelectedToursList() {
//    const listContainer = document.getElementById('selectedToursList');
//    const displayContainer = document.getElementById('selectedToursDisplay');
//    
//    listContainer.innerHTML = '';
//    
//    if (selectedTours.length > 0) {
//        displayContainer.style.display = 'block';
//        
//        selectedTours.forEach(tour => {
//            const li = document.createElement('li');
//            li.className = 'selected-tour-item';
//            li.innerHTML = `
//                ${tour.name} ($${tour.price})
//                <button onclick="removeTour(${tour.id})" class="remove-tour-btn">
//                    Quitar
//                </button>
//            `;
//            listContainer.appendChild(li);
//        });
//    } else {
//        displayContainer.style.display = 'none';
//    }
//}
//
//// Función para remover un tour de la selección
//function removeTour(tourId) {
//    selectedTours = selectedTours.filter(t => t.id !== tourId);
//    updateSelectedToursList();
//    document.getElementById('selectedTourIds').value = selectedTours.map(t => t.id).join(',');
//    
//    const checkbox = document.querySelector(`input[type="checkbox"][value="${tourId}"]`);
//    if (checkbox) {
//        checkbox.checked = false;
//    }
//}
//
//// Función para validar antes de enviar el formulario
//function validateBeforeSubmit() {
//    const tourServiceChecked = document.getElementById('tourServiceCheckbox')?.checked;
//    const hasSelectedTours = selectedTours.length > 0;
//    
//    if (tourServiceChecked && !hasSelectedTours) {
//        Swal.fire({
//            title: 'Error',
//            text: 'Por favor seleccione al menos un tour o desactive el servicio de tours.',
//            icon: 'error'
//        });
//        return false;
//    }
//    
//    return validateForm();
//}
//
//// Función para validar el formulario completo
//function validateForm() {
//    if (!validateClient() || !validateDates() || !validateGuests()) {
//        return false;
//    }
//    
//    return new Promise((resolve) => {
//        Swal.fire({
//            title: 'Confirmar reservación',
//            text: "¿Está seguro de que desea guardar la reserva?",
//            icon: 'question',
//            showCancelButton: true,
//            confirmButtonColor: '#3085d6',
//            cancelButtonColor: '#d33',
//            confirmButtonText: 'Sí, confirmar',
//            cancelButtonText: 'Cancelar'
//        }).then((result) => {
//            resolve(result.isConfirmed);
//        });
//    });
//}
//
//// Validaciones individuales
//function validateClient() {
//    const clientSelect = document.getElementById('client');
//    if (!clientSelect.value || clientSelect.value <= 0) {
//        Swal.fire({
//            title: 'Error',
//            text: 'Seleccione un cliente válido',
//            icon: 'error'
//        });
//        clientSelect.focus();
//        return false;
//    }
//    return true;
//}
//
//function validateDates() {
//    const checkIn = document.getElementById('checkInDate').value;
//    const checkOut = document.getElementById('checkOutDate').value;
//    
//    if (!checkIn || !checkOut) {
//        Swal.fire({
//            title: 'Error',
//            text: 'Complete ambas fechas',
//            icon: 'error'
//        });
//        return false;
//    }
//    
//    if (new Date(checkOut) <= new Date(checkIn)) {
//        Swal.fire({
//            title: 'Error',
//            text: 'La fecha de salida debe ser posterior a la de entrada',
//            icon: 'error'
//        });
//        return false;
//    }
//    
//    return true;
//}
//
//function validateGuests() {
//    const guestsInput = document.getElementById('numberGuests');
//    if (guestsInput.value < 1 || guestsInput.value > 20) {
//        Swal.fire({
//            title: 'Error',
//            text: 'Número de huéspedes debe ser entre 1 y 20',
//            icon: 'error'
//        });
//        guestsInput.focus();
//        return false;
//    }
//    return true;
//}
//
//// Asignar el validador al formulario
//document.addEventListener('DOMContentLoaded', function() {
//    const form = document.querySelector('form');
//    if (form) {
//        form.onsubmit = async function(e) {
//            e.preventDefault();
//            const isValid = await validateBeforeSubmit();
//            if (isValid) {
//                this.submit();
//            }
//        };
//    }
//});
//
//// Función para mostrar/ocultar el formulario de transporte
//function toggleTransportForm(show) {
//    const transportContainer = document.getElementById('transport-form-container');
//    
//    if (show) {
//        transportContainer.innerHTML = `
//            <div style="text-align: center; padding: 20px;">
//                <div class="loading-spinner" style="margin: 0 auto;"></div>
//                <p>Cargando formulario de transporte...</p>
//            </div>`;
//        transportContainer.style.display = 'block';
//        
//        const reservationId = document.querySelector('input[name="id_reservation"]')?.value || 'new';
//        
//        fetch(`/transportation/form?reservationId=${reservationId}`, {
//            headers: {
//                'X-Requested-With': 'XMLHttpRequest'
//            }
//        })
//        .then(response => {
//            if (!response.ok) {
//                throw new Error('Error al cargar el formulario de transporte');
//            }
//            return response.text();
//        })
//        .then(html => {
//            transportContainer.innerHTML = html;
//            
//            const now = new Date();
//            const today = now.toISOString().slice(0, 16);
//            const dateTimeInput = document.getElementById('dataTimeService');
//            if (dateTimeInput) {
//                dateTimeInput.setAttribute('min', today);
//            }
//        })
//        .catch(error => {
//            transportContainer.innerHTML = `
//                <div class="error-message">
//                    ${error.message}
//                    <button onclick="toggleTransportForm(true)" class="button primary" style="margin-top: 10px;">
//                        Reintentar
//                    </button>
//                </div>`;
//            console.error('Error:', error);
//        });
//    } else {
//        transportContainer.style.display = 'none';
//        transportContainer.innerHTML = '';
//    }
//}
//
//// Función para enviar el formulario de transporte
//function submitTransportForm(event) {
//    event.preventDefault();
//    const form = event.target;
//    const formData = new FormData(form);
//    
//    const serviceDateTime = new Date(formData.get('dataTimeService'));
//    const now = new Date();
//    if (serviceDateTime < now) {
//        Swal.fire({
//            title: 'Error',
//            text: 'La fecha y hora del servicio no puede ser en el pasado',
//            icon: 'error'
//        });
//        return false;
//    }
//    
//    const submitButton = form.querySelector('button[type="submit"]');
//    const originalText = submitButton.textContent;
//    submitButton.disabled = true;
//    submitButton.innerHTML = '<span class="loading-spinner small"></span> Guardando...';
//    
//    fetch('/transportation/save', {
//        method: 'POST',
//        body: formData
//    })
//    .then(response => {
//        if (!response.ok) throw new Error('Error en la respuesta del servidor');
//        return response.json();
//    })
//    .then(data => {
//        if (data.success) {
//            Swal.fire({
//                title: 'Éxito',
//                text: 'Transporte guardado correctamente',
//                icon: 'success'
//            });
//            toggleTransportForm(false);
//            document.getElementById('transportCheckbox').checked = true;
//        } else {
//            throw new Error(data.message || 'Error al guardar el transporte');
//        }
//    })
//    .catch(error => {
//        console.error('Error:', error);
//        Swal.fire({
//            title: 'Error',
//            text: error.message,
//            icon: 'error'
//        });
//    })
//    .finally(() => {
//        submitButton.textContent = originalText;
//        submitButton.disabled = false;
//    });
//    
//    return false;
//}