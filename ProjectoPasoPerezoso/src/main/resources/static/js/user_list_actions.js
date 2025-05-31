document.addEventListener('DOMContentLoaded', function() {
    // Configuración
    const delay = 500; // Tiempo de debounce para el filtrado
    let filterTimeout;
    
    // Referencias a elementos del DOM
    const elements = {
        filterId: document.getElementById('filterIdentification'),
        filterType: document.getElementById('filterType'),
        clearBtn: document.getElementById('clearFiltersBtn'),
        pageSizeSelect: document.getElementById('pageSizeSelect'),
        usersTableBody: document.getElementById('usersTableBody'),
        paginationControls: document.getElementById('paginationControls'),
        showingCount: document.getElementById('showingCount'),
        totalCount: document.getElementById('totalCount'),
        modalContainer: document.getElementById('modalContainer'),
        modalContent: document.getElementById('modalContent'),
        closeModal: document.getElementById('closeModal'),
        newUserBtn: document.getElementById('newUserBtn')
    };

    // Estado de la aplicación
    let currentPage = 0;
    let currentFilters = {
        identification: '',
        type: ''
    };

    // Inicialización
    init();

    function init() {
        setupEventListeners();
      loadUsers(0); // Cargar primera página al inicio
    }

    
    
    function setupEventListeners() {
        // Eventos de filtrado
        if (elements.filterId) {
            elements.filterId.addEventListener('input', handleFilterChange);
            elements.filterId.addEventListener('keyup', function(e) {
                if (e.key === 'Enter') applyFilters();
            });
        }
        
        if (elements.filterType) {
            elements.filterType.addEventListener('change', handleFilterChange);
        }
        
        if (elements.clearBtn) {
            elements.clearBtn.addEventListener('click', clearFilters);
        }
        
        // Eventos de paginación
        if (elements.pageSizeSelect) {
            elements.pageSizeSelect.addEventListener('change', handlePageSizeChange);
        }
        
        // Eventos de modal
        if (elements.newUserBtn) {
            elements.newUserBtn.addEventListener('click', handleNewUserClick);
        }
        
        if (elements.modalContainer) {
            elements.modalContainer.addEventListener('click', handleModalOutsideClick);
        }
        
        if (elements.closeModal) {
            elements.closeModal.addEventListener('click', closeModalHandler);
        }
        
        // Delegación de eventos para elementos dinámicos
        document.addEventListener('click', function(e) {
            // Manejar clic en paginación
            if (e.target.closest('.page-link')) {
                e.preventDefault();
                const page = parseInt(e.target.closest('.page-link').dataset.page);
                loadUsers(page);
            }
            
            // Mantener los otros handlers originales
            if (e.target.closest('.edit-btn')) {
                e.preventDefault();
                loadFormInModal(e.target.closest('a').href);
            }
            
            if (e.target.closest('.delete-btn')) {
                e.preventDefault();
                confirmDelete(e, e.target.closest('.delete-btn'));
            }
        });
    }

    // **********************
    // Funciones de Filtrado y Paginación
    // **********************
    
    function handleFilterChange() {
        clearTimeout(filterTimeout);
        filterTimeout = setTimeout(applyFilters, delay);
    }

    function applyFilters() {
        const newFilters = {
            identification: elements.filterId ? elements.filterId.value.trim() : '',
            type: elements.filterType ? elements.filterType.value : ''
        };
        
        // Solo recargar si los filtros cambiaron
        if (JSON.stringify(newFilters) !== JSON.stringify(currentFilters)) {
            currentFilters = newFilters;
            loadUsers(0);
        }
    }

    function clearFilters() {
        if (elements.filterId) elements.filterId.value = '';
        if (elements.filterType) elements.filterType.value = '';
        currentFilters = { identification: '', type: '' };
        loadUsers(0);
    }

    function handlePageSizeChange() {
        loadUsers(0);
    }

    function loadUsers(page = 0) {
        showLoadingState();
        currentPage = page;

        const params = {
            page: page,
            size: elements.pageSizeSelect.value,
            identification: currentFilters.identification,
            type: currentFilters.type
        };

        fetch(buildUrl('/admin/api/list', params), {
            headers: { 
                'X-Requested-With': 'XMLHttpRequest',
                'Accept': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text || `Error ${response.status}: ${response.statusText}`);
                });
            }
            return response.json();
        })
        .then(data => {
            if (!data.content) {
                throw new Error('Formato de respuesta inválido');
            }
            updateTable(data.content);
            updatePagination(data.totalPages, page, data.totalElements);
        })
        .catch(error => {
            console.error('Error:', error);
            showErrorState(error.message);
            
            Swal.fire({
                title: 'Error',
                text: 'No se pudieron cargar los usuarios. Por favor, intente nuevamente.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: true
            });
        });
    }

    function buildUrl(path, params) {
        const url = new URL(path, window.location.origin);
        Object.entries(params).forEach(([key, value]) => {
            if (value !== undefined && value !== '' && value !== null) {
                url.searchParams.append(key, value);
            }
        });
        return url.toString();
    }

    function updateTable(users) {
    const tbody = elements.usersTableBody;
    
    if (!users) {
        showErrorState("Error al obtener datos del servidor");
        return;
    }
    
    if (users.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center no-users">
                    <i class="fas fa-info-circle"></i> No se encontraron usuarios
                </td>
            </tr>
        `;
        return;
    }
    
        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.id_user}</td>
                <td>
                    <span class="badge ${getBadgeClass(user.userType)}">
                        ${user.userType}
                    </span>
                </td>
                <td>${user.name} ${user.last_name}</td>
                <td>${user.identification}</td>
                <td>
                    <a href="mailto:${user.email}">
                        <i class="fas fa-envelope"></i> ${user.email}
                    </a>
                </td>
                <td>
                    <a href="tel:${user.phone}">${user.phone}</a>
                </td>
                <td class="user-actions">
                    <a href="/user/detalle/${user.id_user}" class="action-btn view-btn" title="Ver detalles">
                        <i class="fas fa-eye"></i> <span class="sr-only">Ver</span>
                    </a>
                    <a href="/user/editar/${user.id_user}" class="action-btn edit-btn" title="Editar">
                        <i class="fas fa-edit"></i> <span class="sr-only">Editar</span>
                    </a>
                    <form action="/user/eliminar/${user.id_user}" method="post" class="delete-form">
                        <button type="submit" class="action-btn delete-btn" title="Eliminar">
                            <i class="fas fa-trash"></i> <span class="sr-only">Eliminar</span>
                        </button>
                    </form>
                </td>
            `;
            tbody.appendChild(row);
        });
    }
    
    function getBadgeClass(userType) {
        switch(userType) {
            case 'ADMIN': return 'badge-admin';
            case 'EMPLOYEE': return 'badge-employee';
            case 'CLIENT': return 'badge-client';
            default: return '';
        }
    }

    function updatePagination(totalPages, currentPage, totalItems) {
    const pagination = elements.paginationControls;
    pagination.innerHTML = '';
    
    // Actualizar contadores
    if (elements.showingCount && elements.totalCount) {
        const pageSize = parseInt(elements.pageSizeSelect.value);
        const startItem = (currentPage * pageSize) + 1;
        const endItem = Math.min((currentPage + 1) * pageSize, totalItems);
        
        elements.showingCount.textContent = `${startItem}-${endItem}`;
        elements.totalCount.textContent = totalItems;
    }
    
    if (totalPages <= 1) return;
    
    // Botón Anterior
    if (currentPage > 0) {
        const prevBtn = document.createElement('button');
        prevBtn.textContent = '« Anterior';
        prevBtn.addEventListener('click', () => loadUsers(currentPage - 1));
        pagination.appendChild(prevBtn);
    }
    
    // Números de página
    const startPage = Math.max(0, currentPage - 2);
    const endPage = Math.min(totalPages - 1, currentPage + 2);
    
    // Mostrar elipsis al inicio si es necesario
    if (startPage > 0) {
        const firstBtn = document.createElement('button');
        firstBtn.textContent = '1';
        firstBtn.addEventListener('click', () => loadUsers(0));
        pagination.appendChild(firstBtn);
        
        if (startPage > 1) {
            const ellipsis = document.createElement('span');
            ellipsis.textContent = '...';
            pagination.appendChild(ellipsis);
        }
    }
    
    // Botones de páginas
    for (let i = startPage; i <= endPage; i++) {
        const pageBtn = document.createElement('button');
        pageBtn.textContent = i + 1;
        if (i === currentPage) {
            pageBtn.classList.add('active');
        }
        pageBtn.addEventListener('click', () => loadUsers(i));
        pagination.appendChild(pageBtn);
    }
    
    // Mostrar elipsis al final si es necesario
    if (endPage < totalPages - 1) {
        if (endPage < totalPages - 2) {
            const ellipsis = document.createElement('span');
            ellipsis.textContent = '...';
            pagination.appendChild(ellipsis);
        }
        
        const lastBtn = document.createElement('button');
        lastBtn.textContent = totalPages;
        lastBtn.addEventListener('click', () => loadUsers(totalPages - 1));
        pagination.appendChild(lastBtn);
    }
    
    // Botón Siguiente
    if (currentPage < totalPages - 1) {
        const nextBtn = document.createElement('button');
        nextBtn.textContent = 'Siguiente »';
        nextBtn.addEventListener('click', () => loadUsers(currentPage + 1));
        pagination.appendChild(nextBtn);
    }
}
    
//    function createPageItem(text, page, isActive = false) {
//        const li = document.createElement('li');
//        li.className = 'page-item' + (isActive ? ' active' : '');
//        
//        const a = document.createElement('a');
//        a.className = 'page-link';
//        a.href = '#';
//        a.textContent = text;
//        a.dataset.page = page;
//        
//        li.appendChild(a);
//        return li;
//    }
    
//    function createEllipsis() {
//        const li = document.createElement('li');
//        li.className = 'page-item disabled';
//        li.innerHTML = '<span class="page-link">...</span>';
//        return li;
//    }

    // **********************
    // Funciones de Interfaz
    // **********************
    
    function showLoadingState() {
        if (elements.usersTableBody) {
            elements.usersTableBody.innerHTML = `
                <tr>
                    <td colspan="7" class="text-center no-users" aria-live="polite">
                        <i class="fas fa-spinner fa-spin" aria-hidden="true"></i> 
                        <span class="sr-only">Cargando</span> Cargando usuarios...
                    </td>
                </tr>
            `;
        }
    }

    function showErrorState(message = '') {
        if (elements.usersTableBody) {
            elements.usersTableBody.innerHTML = `
                <tr>
                    <td colspan="7" class="text-center no-users" aria-live="assertive">
                        <i class="fas fa-exclamation-triangle" aria-hidden="true"></i> 
                        ${message || 'Error al cargar los datos'}
                        <button class="retry-btn">Reintentar</button>
                    </td>
                </tr>
            `;
            
            const retryBtn = document.querySelector('.retry-btn');
            if (retryBtn) {
                retryBtn.addEventListener('click', () => loadUsers(currentPage));
                retryBtn.focus();
            }
        }
    }

    // **********************
    // Funciones de Modal (mantenidas de tu código original)
    // **********************
    
    function handleNewUserClick(e) {
        e.preventDefault();
        loadFormInModal(this.href);
    }

    function loadFormInModal(url) {
        showModalWithContent(`
            <div class="modal-loading">
                <i class="fas fa-spinner fa-spin" aria-hidden="true"></i>
                <span class="sr-only">Cargando</span> Cargando formulario...
            </div>
        `);

        fetch(url, {
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(response => {
            if (!response.ok) throw new Error(`Error ${response.status}: ${response.statusText}`);
            return response.text();
        })
        .then(html => {
            showModalWithContent(html);
            initFormScripts();
            setupFormSubmitHandler();
        })
        .catch(error => {
            console.error('Error:', error);
            showModalWithContent(`
                <div class="modal-error">
                    <i class="fas fa-exclamation-triangle" aria-hidden="true"></i>
                    <p>No se pudo cargar el formulario</p>
                    <button class="retry-btn">Reintentar</button>
                </div>
            `);
            
            document.querySelector('.modal-error .retry-btn')?.addEventListener('click', () => loadFormInModal(url));
        });
    }

    function showModalWithContent(content) {
        if (elements.modalContent) {
            elements.modalContent.innerHTML = content;
            if (elements.modalContainer) {
                elements.modalContainer.style.display = 'flex';
                elements.modalContainer.setAttribute('aria-modal', 'true');
                elements.modalContainer.setAttribute('role', 'dialog');
                handleModalAccessibility();
            }
        }
    }

    function handleModalAccessibility() {
        const previousActiveElement = document.activeElement;
        elements.modalContainer.dataset.previousActiveElement = 
            previousActiveElement.id || previousActiveElement.className || '';
        
        const focusable = elements.modalContent.querySelector(
            'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
        );
        if (focusable) focusable.focus();
        
        elements.modalContent.addEventListener('keydown', trapTabKey);
    }

    function trapTabKey(e) {
        if (e.key === 'Tab') {
            const focusableElements = elements.modalContent.querySelectorAll(
                'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
            );
            const firstElement = focusableElements[0];
            const lastElement = focusableElements[focusableElements.length - 1];
            
            if (e.shiftKey) {
                if (document.activeElement === firstElement) {
                    lastElement.focus();
                    e.preventDefault();
                }
            } else {
                if (document.activeElement === lastElement) {
                    firstElement.focus();
                    e.preventDefault();
                }
            }
        }
        
        if (e.key === 'Escape') {
            closeModalHandler();
        }
    }

    function initFormScripts() {
        const tipoUsuarioSelect = document.getElementById('tipoUsuario');
        if (tipoUsuarioSelect) {
            tipoUsuarioSelect.addEventListener('change', function() {
                const empleadoFields = document.getElementById('empleadoFields');
                if (empleadoFields) {
                    empleadoFields.style.display = this.value === 'Empleado' ? 'block' : 'none';
                    empleadoFields.setAttribute('aria-hidden', this.value !== 'Empleado');
                }
            });
            
            if (tipoUsuarioSelect.value) {
                tipoUsuarioSelect.dispatchEvent(new Event('change'));
            }
        }

        const fileInput = document.querySelector('input[type="file"]');
        if (fileInput) {
            fileInput.addEventListener('change', handleFileUpload);
        }
    }

    function handleFileUpload(e) {
        const file = e.target.files[0];
        if (!file) return;
        
        const isEditForm = this.id === 'profile_picture';
        const previewId = isEditForm ? 'previewImageNew' : 'previewImage';
        const fileNameId = isEditForm ? 'fileNameEdit' : 'fileName';
        
        const preview = document.getElementById(previewId);
        const fileName = document.getElementById(fileNameId);
        
        if (fileName) {
            fileName.textContent = file.name;
            fileName.setAttribute('aria-label', `Archivo seleccionado: ${file.name}`);
        }
        
        const reader = new FileReader();
        reader.onload = function(event) {
            if (preview) {
                preview.src = event.target.result;
                preview.style.display = 'block';
                preview.alt = 'Vista previa de la imagen';
            }
        };
        reader.readAsDataURL(file);
    }

    function setupFormSubmitHandler() {
        const form = elements.modalContent?.querySelector('form');
        if (!form) return;
        
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const submitButton = form.querySelector('button[type="submit"]');
            if (submitButton) {
                submitButton.disabled = true;
                submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Procesando...';
            }
            
            submitForm(this);
        });
    }

    function submitForm(form) {
        const formData = new FormData(form);
        
        fetch(form.action, {
            method: form.method,
            body: formData,
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text().then(html => {
                    if (html.includes('form')) {
                        if (elements.modalContent) {
                            elements.modalContent.innerHTML = html;
                            initFormScripts();
                            setupFormSubmitHandler();
                            
                            const firstError = elements.modalContent.querySelector('.is-invalid');
                            if (firstError) firstError.focus();
                        }
                    } else {
                        throw new Error('Respuesta inesperada del servidor');
                    }
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            
            const submitButton = form.querySelector('button[type="submit"]');
            if (submitButton) {
                submitButton.disabled = false;
                submitButton.innerHTML = 'Guardar';
            }
            
            Swal.fire({
                title: 'Error',
                text: 'Error al enviar el formulario. Por favor, intente nuevamente.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false
            });
        });
    }

    function handleModalOutsideClick(e) {
        if (e.target === elements.modalContainer) {
            closeModalHandler();
        }
    }

    function closeModalHandler() {
        if (elements.modalContainer) {
            elements.modalContainer.style.display = 'none';
            elements.modalContainer.removeAttribute('aria-modal');
            
            const previousActiveElement = document.querySelector(
                `#${elements.modalContainer.dataset.previousActiveElement}, .${elements.modalContainer.dataset.previousActiveElement}`
            ) || elements.newUserBtn;
            
            if (previousActiveElement) previousActiveElement.focus();
        }
    }

    // **********************
    // Funciones de Confirmación
    // **********************
    
    function confirmDelete(event, button) {
        event.preventDefault();
        const form = button.closest('form');
        if (!form) return;
        
        const row = button.closest('tr');
        const userName = row.querySelector('td:nth-child(3)').textContent;
        const userType = row.querySelector('td:nth-child(2)').textContent;
        
        Swal.fire({
            title: `¿Eliminar a ${userName.trim()}?`,
            html: `
                <p>Estás a punto de eliminar a <strong>${userName.trim()}</strong> (${userType.trim()}).</p>
                <p>Esta acción no se puede deshacer.</p>
            `,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#6f7e14',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            reverseButtons: true,
            focusCancel: true,
            backdrop: `
                rgba(0,0,0,0.7)
                url("/images/icon-trash.png")
                center top
                no-repeat
            `,
            customClass: {
                container: 'swal2-container-accessibility',
                popup: 'swal2-popup-accessibility',
                title: 'swal2-title-accessibility',
                htmlContainer: 'swal2-html-container-accessibility'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: 'Eliminando...',
                    html: 'Por favor espere mientras se procesa la solicitud',
                    allowOutsideClick: false,
                    didOpen: () => {
                        Swal.showLoading();
                    }
                });
                
                form.submit();
            }
        });
    }
});