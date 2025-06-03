document.addEventListener('DOMContentLoaded', function() {
    // Cache de elementos DOM - VERSIÓN COMPLETA
    const domElements = {
        newUserBtn: document.getElementById('newUserBtn'),
        filterForm: document.getElementById('filterForm'),
        pageSizeSelect: document.getElementById('pageSizeSelect'),
        clearFiltersBtn: document.getElementById('clearFiltersBtn'),
        usersTableBody: document.getElementById('usersTableBody'),
        tableSection: document.querySelector('.table-section'), // Añadido
        tableContent: document.querySelector('.table-responsive'), // Añadido
        currentPageInput: document.querySelector('input[name="page"]'),
        sizeInput: document.querySelector('input[name="size"]'),
        totalUsersSpan: document.getElementById('totalUsers'), // Añadido
        filterIdentification: document.getElementById('filterIdentification'), // Añadido
        filterType: document.getElementById('filterType') // Añadido
    };

    // Manejar clic en "Nuevo Usuario"
    domElements.newUserBtn?.addEventListener('click', function(e) {
        e.preventDefault();
        loadFormInModal(this.href);
    });
    
    // Delegación de eventos centralizada
    document.addEventListener('click', function(e) {
        const editBtn = e.target.closest('.edit-btn');
        const deleteBtn = e.target.closest('.delete-btn');
        const paginationLink = e.target.closest('.pagination a');
        const viewBtn = e.target.closest('.view-btn');
        
        // Editar usuario
        if (editBtn) {
            e.preventDefault();
            loadFormInModal(editBtn.closest('a').href);
        }
        
        // Eliminar usuario
        if (deleteBtn) {
            e.preventDefault();
            confirmDelete(e, deleteBtn);
        }
        
        // Paginación
        if (paginationLink) {
            e.preventDefault();
            const url = new URL(paginationLink.href);
            const page = url.searchParams.get('page');
            loadUsersWithFilters(page);
        }
        
//        // Ver detalles (evita que se recargue la página)
//        if (viewBtn) {
//            e.preventDefault();
//            // loadFormInModal(viewBtn.closest('a').href); // Descomenta si quieres cargar en modal
//        }
    });
    
    async function loadUsersWithFilters(page = 0) {
        try {
            // Actualizar página actual en el formulario
            if (domElements.currentPageInput) {
                domElements.currentPageInput.value = page;
            }
            
            // Mostrar loader
            if (domElements.tableSection) {
                domElements.tableSection.innerHTML = `
                    <div class="loader">Cargando usuarios...</div>
                `;
            }
            
            // Construir parámetros
            const params = new URLSearchParams();
            params.append('page', page);
            params.append('size', domElements.sizeInput?.value || '4');
            
            // Agregar filtros si existen
            if (domElements.filterIdentification?.value) {
                params.append('identification', domElements.filterIdentification.value);
            }
            if (domElements.filterType?.value) {
                params.append('type', domElements.filterType.value);
            }
            
            console.log('Enviando parámetros:', params.toString()); // Para depuración
            
            // Configurar headers para AJAX
            const headers = { 'X-Requested-With': 'XMLHttpRequest' };
            
            // Realizar petición
            const response = await fetch(`/admin/list?${params.toString()}`, { headers });
            
            if (!response.ok) {
                throw new Error(`Error ${response.status}: ${response.statusText}`);
            }
            
            const html = await response.text();
            updateUIWithResponse(html);
        } catch (error) {
            console.error('Error al cargar usuarios:', error);
            showErrorMessage(error.message);
        }
    }
    
    function updateUIWithResponse(html) {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        
        // Opción 1: Actualizar solo el cuerpo de la tabla
        const newTableBody = doc.getElementById('usersTableBody');
        if (newTableBody && domElements.usersTableBody) {
            domElements.usersTableBody.innerHTML = newTableBody.innerHTML;
        }
        
        // Opción 2: Actualizar toda la sección de tabla (incluyendo paginación)
        const newTableSection = doc.querySelector('.table-section');
        if (newTableSection && domElements.tableSection) {
            domElements.tableSection.innerHTML = newTableSection.innerHTML;
        }
        
        // Actualizar el contador total
        const totalUsers = doc.getElementById('totalUsers');
        if (totalUsers && domElements.totalUsersSpan) {
            domElements.totalUsersSpan.textContent = totalUsers.textContent;
        }
        
        // Actualizar los inputs ocultos
        const currentPage = doc.querySelector('input[name="page"]');
        const size = doc.querySelector('input[name="size"]');
        if (currentPage && domElements.currentPageInput) {
            domElements.currentPageInput.value = currentPage.value;
        }
        if (size && domElements.sizeInput) {
            domElements.sizeInput.value = size.value;
        }
    }
    
    function showErrorMessage(message) {
        if (domElements.tableSection) {
            domElements.tableSection.innerHTML = `
                <div class="error-message">
                    <i class="fas fa-exclamation-triangle"></i>
                    ${message || 'Error al cargar los usuarios'}
                </div>
            `;
        }
    }
    
    // Manejador de envío del formulario de filtrado
    domElements.filterForm?.addEventListener('submit', function(e) {
        e.preventDefault();
        loadUsersWithFilters(0);
    });
    
    // Manejador de cambio en el tamaño de página
    domElements.pageSizeSelect?.addEventListener('change', function() {
        if (domElements.sizeInput) {
            domElements.sizeInput.value = this.value;
        }
        loadUsersWithFilters(0);
    });
    
    // Manejador de limpieza de filtros
    domElements.clearFiltersBtn?.addEventListener('click', function() {
        if (domElements.filterIdentification) {
            domElements.filterIdentification.value = '';
        }
        if (domElements.filterType) {
            domElements.filterType.value = '';
        }
        if (domElements.currentPageInput) {
            domElements.currentPageInput.value = '0';
        }
        loadUsersWithFilters(0);
    });
    
    // Función de confirmación para eliminar usuario
    async function confirmDelete(event, button) {
        event.preventDefault();
        const form = button.closest('form');
        
        const { isConfirmed } = await Swal.fire({
            title: '¿Eliminar usuario?',
            text: "Esta acción no se puede deshacer",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            reverseButtons: true
        });
        
        if (!isConfirmed) return;
        
        try {
            const response = await fetch(form.action, {
                method: 'POST',
                headers: { 
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            
            if (!response.ok) throw new Error('Error en la respuesta del servidor');
            
            const data = await response.json();
            
            if (data.success) {
                await Swal.fire({
                    title: 'Eliminado!',
                    text: 'El usuario ha sido eliminado.',
                    icon: 'success'
                });
                // Recargar tabla manteniendo la página actual
                const currentPage = domElements.currentPageInput?.value || '0';
                loadUsersWithFilters(parseInt(currentPage));
            } else {
                throw new Error(data.message || 'No se pudo eliminar el usuario');
            }
        } catch (error) {
            console.error('Error:', error);
            Swal.fire({
                title: 'Error!',
                text: error.message || 'Ocurrió un error al eliminar el usuario.',
                icon: 'error'
            });
        }
    }
    

});