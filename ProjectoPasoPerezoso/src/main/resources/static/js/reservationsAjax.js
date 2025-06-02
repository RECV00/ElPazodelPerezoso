// Función para filtrar por ID de cliente
function filterByClient() {
    const clientId = document.getElementById('id_client').value;
    const loadingElement = document.getElementById('client-filter-loading');
    
    loadingElement.style.display = 'inline-block';
    
    if (!clientId) {
        fetchReservations('/booking/listaReservas');
        loadingElement.style.display = 'none';
        return;
    }
    
    const url = `/booking/search?clientId=${clientId}`;
    fetchReservations(url, loadingElement);
}

// Función para filtrar por estado
function filterByStatus() {
    const status = document.getElementById('reservationStatus').value;
    const loadingElement = document.getElementById('status-filter-loading');
    
    loadingElement.style.display = 'inline-block';
    
    let url = '/booking/listaReservas';
    if (status) {
        url = `/booking/search?status=${encodeURIComponent(status)}`;
    }
    
    fetchReservations(url, loadingElement);
}

// Función para filtrar por rango de fechas
function filterByDateRange() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const loadingElement = document.getElementById('date-filter-loading');
    const errorElement = document.getElementById('date-error');
    
    if ((startDate && !endDate) || (!startDate && endDate)) {
        errorElement.textContent = 'Debe seleccionar ambas fechas para filtrar';
        errorElement.style.display = 'inline-block';
        return;
    }
    
    errorElement.style.display = 'none';
    loadingElement.style.display = 'inline-block';
    
    if (!startDate && !endDate) {
        fetchReservations('/booking/listaReservas', loadingElement);
        return;
    }
    
    const url = `/bookings/search?startDate=${startDate}&endDate=${endDate}`;
    fetchReservations(url, loadingElement);
}

// Función genérica para obtener reservaciones via AJAX
function fetchReservations(url, loadingElement = null) {
    fetch(url, {
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.text();
    })
    .then(html => {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        const newTable = doc.querySelector('.bookings-table');
        
        if (newTable) {
            const currentTable = document.querySelector('.bookings-table');
            currentTable.parentNode.replaceChild(newTable, currentTable);
        }
        
        if (loadingElement) {
            loadingElement.style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error al cargar reservaciones:', error);
        if (loadingElement) {
            loadingElement.style.display = 'none';
        }
        
        const errorContainer = document.querySelector('.error-message');
        if (errorContainer) {
            errorContainer.textContent = 'Error al cargar las reservaciones. Por favor, intente nuevamente.';
            errorContainer.style.display = 'block';
        }
    });
}

// Función para mostrar todas las reservaciones
function showAllReservations() {
    fetchReservations('/booking/listaReservas');
    
    document.getElementById('id_client').value = '';
    document.getElementById('reservationStatus').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    document.getElementById('date-error').style.display = 'none';
}

// Función para cambiar el tamaño de página
function changePageSize(size) {
    const url = new URL(window.location.href);
    url.searchParams.set('size', size);
    url.searchParams.set('page', 0);
    window.location.href = url.toString();
}

// Array global para almacenar los tours seleccionados
let selectedTours = [];

// Función para confirmar la selección de tours
function confirmTourSelection() {
    if (selectedTours.length === 0) {
        Swal.fire({
            title: 'Advertencia',
            text: 'Por favor seleccione al menos un tour antes de confirmar.',
            icon: 'warning'
        });
        return;
    }
    
    document.getElementById('selectedTourIds').value = selectedTours.map(t => t.id).join(',');
    updateSelectedToursList();
    
    document.getElementById('tourSelectorContainer').style.display = 'none';
    
    Swal.fire({
        title: 'Éxito',
        text: `Ha confirmado ${selectedTours.length} tour(s). Puede continuar con el resto del formulario.`,
        icon: 'success'
    });
}

// Función para actualizar la lista visual de tours seleccionados
function updateSelectedToursList() {
    const listContainer = document.getElementById('selectedToursList');
    const displayContainer = document.getElementById('selectedToursDisplay');
    
    listContainer.innerHTML = '';
    
    if (selectedTours.length > 0) {
        displayContainer.style.display = 'block';
        
        selectedTours.forEach(tour => {
            const li = document.createElement('li');
            li.className = 'selected-tour-item';
            li.innerHTML = `
                ${tour.name} ($${tour.price})
                <button onclick="removeTour(${tour.id})" class="remove-tour-btn">
                    Quitar
                </button>
            `;
            listContainer.appendChild(li);
        });
    } else {
        displayContainer.style.display = 'none';
    }
}

// Función para remover un tour de la selección
function removeTour(tourId) {
    selectedTours = selectedTours.filter(t => t.id !== tourId);
    updateSelectedToursList();
    document.getElementById('selectedTourIds').value = selectedTours.map(t => t.id).join(',');
    
    const checkbox = document.querySelector(`input[type="checkbox"][value="${tourId}"]`);
    if (checkbox) {
        checkbox.checked = false;
    }
}

// Función para validar antes de enviar el formulario
function validateBeforeSubmit() {
    const tourServiceChecked = document.getElementById('tourServiceCheckbox')?.checked;
    const hasSelectedTours = selectedTours.length > 0;
    
    if (tourServiceChecked && !hasSelectedTours) {
        Swal.fire({
            title: 'Error',
            text: 'Por favor seleccione al menos un tour o desactive el servicio de tours.',
            icon: 'error'
        });
        return false;
    }
    
    return validateForm();
}

// Función para validar el formulario completo
function validateForm() {
    if (!validateClient() || !validateDates() || !validateGuests()) {
        return false;
    }
    
    return new Promise((resolve) => {
        Swal.fire({
            title: 'Confirmar reservación',
            text: "¿Está seguro de que desea guardar la reserva?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, confirmar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            resolve(result.isConfirmed);
        });
    });
}

// Validaciones individuales
function validateClient() {
    const clientSelect = document.getElementById('client');
    if (!clientSelect.value || clientSelect.value <= 0) {
        Swal.fire({
            title: 'Error',
            text: 'Seleccione un cliente válido',
            icon: 'error'
        });
        clientSelect.focus();
        return false;
    }
    return true;
}

function validateDates() {
    const checkIn = document.getElementById('checkInDate').value;
    const checkOut = document.getElementById('checkOutDate').value;
    
    if (!checkIn || !checkOut) {
        Swal.fire({
            title: 'Error',
            text: 'Complete ambas fechas',
            icon: 'error'
        });
        return false;
    }
    
    if (new Date(checkOut) <= new Date(checkIn)) {
        Swal.fire({
            title: 'Error',
            text: 'La fecha de salida debe ser posterior a la de entrada',
            icon: 'error'
        });
        return false;
    }
    
    return true;
}

function validateGuests() {
    const guestsInput = document.getElementById('numberGuests');
    if (guestsInput.value < 1 || guestsInput.value > 20) {
        Swal.fire({
            title: 'Error',
            text: 'Número de huéspedes debe ser entre 1 y 20',
            icon: 'error'
        });
        guestsInput.focus();
        return false;
    }
    return true;
}

// Asignar el validador al formulario
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    if (form) {
        form.onsubmit = async function(e) {
            e.preventDefault();
            const isValid = await validateBeforeSubmit();
            if (isValid) {
                this.submit();
            }
        };
    }
});

// Función para mostrar/ocultar el formulario de transporte
function toggleTransportForm(show) {
    const transportContainer = document.getElementById('transport-form-container');
    
    if (show) {
        transportContainer.innerHTML = `
            <div style="text-align: center; padding: 20px;">
                <div class="loading-spinner" style="margin: 0 auto;"></div>
                <p>Cargando formulario de transporte...</p>
            </div>`;
        transportContainer.style.display = 'block';
        
        const reservationId = document.querySelector('input[name="id_reservation"]')?.value || 'new';
        
        fetch(`/transportation/form?reservationId=${reservationId}`, {
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el formulario de transporte');
            }
            return response.text();
        })
        .then(html => {
            transportContainer.innerHTML = html;
            
            const now = new Date();
            const today = now.toISOString().slice(0, 16);
            const dateTimeInput = document.getElementById('dataTimeService');
            if (dateTimeInput) {
                dateTimeInput.setAttribute('min', today);
            }
        })
        .catch(error => {
            transportContainer.innerHTML = `
                <div class="error-message">
                    ${error.message}
                    <button onclick="toggleTransportForm(true)" class="button primary" style="margin-top: 10px;">
                        Reintentar
                    </button>
                </div>`;
            console.error('Error:', error);
        });
    } else {
        transportContainer.style.display = 'none';
        transportContainer.innerHTML = '';
    }
}

// Función para enviar el formulario de transporte
function submitTransportForm(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    
    const serviceDateTime = new Date(formData.get('dataTimeService'));
    const now = new Date();
    if (serviceDateTime < now) {
        Swal.fire({
            title: 'Error',
            text: 'La fecha y hora del servicio no puede ser en el pasado',
            icon: 'error'
        });
        return false;
    }
    
    const submitButton = form.querySelector('button[type="submit"]');
    const originalText = submitButton.textContent;
    submitButton.disabled = true;
    submitButton.innerHTML = '<span class="loading-spinner small"></span> Guardando...';
    
    fetch('/transportation/save', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) throw new Error('Error en la respuesta del servidor');
        return response.json();
    })
    .then(data => {
        if (data.success) {
            Swal.fire({
                title: 'Éxito',
                text: 'Transporte guardado correctamente',
                icon: 'success'
            });
            toggleTransportForm(false);
            document.getElementById('transportCheckbox').checked = true;
        } else {
            throw new Error(data.message || 'Error al guardar el transporte');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            title: 'Error',
            text: error.message,
            icon: 'error'
        });
    })
    .finally(() => {
        submitButton.textContent = originalText;
        submitButton.disabled = false;
    });
    
    return false;
}