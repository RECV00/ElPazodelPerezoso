document.addEventListener('DOMContentLoaded', function() {
    // Cache de elementos DOM
    const domElements = {
        newUserBtn: document.getElementById('newUserBtn'),
        filterForm: document.getElementById('filterForm'),
        clearFiltersBtn: document.getElementById('clearFiltersBtn'),
        usersTableBody: document.getElementById('usersTableBody'),
        tableContent: document.getElementById('tableContent'),
        tableLoader: document.getElementById('tableLoader'),
        currentPageInput: document.querySelector('input[name="page"]'),
        sizeInput: document.querySelector('input[name="size"]'),
        totalUsersSpan: document.getElementById('totalUsers'),
        filterIdentification: document.getElementById('filterIdentification'),
        filterType: document.getElementById('filterType'),
        paginationContainer: document.querySelector('.pagination-container')
    };

// Variables para el debounce
    let filterTimeout;
    const debounceTime = 500; // 500ms de espera después de que el usuario deja de escribir
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
    });
    
    // Eventos para el filtrado automático
//    domElements.filterIdentification?.addEventListener('input', function() {
//        clearTimeout(filterTimeout);
//        filterTimeout = setTimeout(() => {
//            loadUsersWithFilters(0); // Resetear a la primera página
//        }, debounceTime);
//    });
    domElements.filterIdentification?.addEventListener('keypress', function(e) {
    if (e.key === 'Enter' || e.keyCode === 13) {
        e.preventDefault(); // Prevenir comportamiento por defecto
        loadUsersWithFilters(0); // Resetear a la primera página
    }
    }); 
    
    domElements.filterType?.addEventListener('change', function() {
        loadUsersWithFilters(0); // Resetear a la primera página
    });
    
    // Manejador de limpieza de filtros
    domElements.clearFiltersBtn?.addEventListener('click', function() {
        if (domElements.filterIdentification) domElements.filterIdentification.value = '';
        if (domElements.filterType) domElements.filterType.value = '';
        loadUsersWithFilters(0);
    });
    
    
    async function loadUsersWithFilters(page = 0) {
        try {
            // Mostrar loader con transición suave
            if (domElements.tableContent && domElements.tableLoader) {
                domElements.tableContent.style.opacity = '0.7';
                domElements.tableContent.style.transition = 'opacity 0.3s ease';
                domElements.tableLoader.style.display = 'flex';
            }
            
            // Construir parámetros
            const params = new URLSearchParams();
            params.append('page', page);
            params.append('size', domElements.sizeInput?.value || '10');
            
            if (domElements.filterIdentification?.value) {
                params.append('identification', domElements.filterIdentification.value);
            }
            if (domElements.filterType?.value) {
                params.append('type', domElements.filterType.value);
            }
            
            // Configurar headers para AJAX
            const headers = { 
                'X-Requested-With': 'XMLHttpRequest',
                'Cache-Control': 'no-cache'
            };
            
            // Realizar petición
            const response = await fetch(`/admin/list?${params.toString()}`, { 
                headers,
                cache: 'no-store'
            });
            
            if (!response.ok) {
                throw new Error(`Error ${response.status}: ${response.statusText}`);
            }
            
            const html = await response.text();
            
            // Ocultar loader y mostrar contenido con transición
            if (domElements.tableContent && domElements.tableLoader) {
                domElements.tableContent.style.opacity = '0';
                setTimeout(() => {
                    updateUIWithResponse(html);
                    domElements.tableContent.style.opacity = '1';
                    domElements.tableLoader.style.display = 'none';
                }, 300);
            } else {
                updateUIWithResponse(html);
            }
        } catch (error) {
            console.error('Error al cargar usuarios:', error);
            if (domElements.tableContent && domElements.tableLoader) {
                domElements.tableContent.style.opacity = '1';
                domElements.tableLoader.style.display = 'none';
            }
            showErrorMessage(error.message);
        }
    }
    
    function updateUIWithResponse(html) {
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        
        // Actualizar el cuerpo de la tabla
        const newTableBody = doc.getElementById('usersTableBody');
        if (newTableBody && domElements.usersTableBody) {
            domElements.usersTableBody.innerHTML = newTableBody.innerHTML;
        }
        
        // Actualizar paginación
        const newPagination = doc.querySelector('.pagination-container');
        if (newPagination && domElements.paginationContainer) {
            domElements.paginationContainer.innerHTML = newPagination.innerHTML;
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
        if (domElements.tableContent) {
            const errorHtml = `
                <div class="error-message">
                    <i class="fas fa-exclamation-triangle"></i>
                    ${message || 'Error al cargar los usuarios'}
                </div>
            `;
            domElements.tableContent.insertAdjacentHTML('afterbegin', errorHtml);
        }
    }
    
//    // Manejador de envío del formulario de filtrado
//    domElements.filterForm?.addEventListener('submit', function(e) {
//        e.preventDefault();
//        loadUsersWithFilters(0);
//    });
//    
//    // Manejador de limpieza de filtros
//    domElements.clearFiltersBtn?.addEventListener('click', function() {
//        if (domElements.filterIdentification) domElements.filterIdentification.value = '';
//        if (domElements.filterType) domElements.filterType.value = '';
//        if (domElements.currentPageInput) domElements.currentPageInput.value = '0';
//        loadUsersWithFilters(0);
//    });
    
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