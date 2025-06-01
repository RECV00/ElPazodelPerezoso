// user_list_actions.js

document.addEventListener('DOMContentLoaded', function() {
    // Configuración
    const delay = 500; // Tiempo de espera para filtrar después de escribir (ms)
    let filterTimeout;
    
    // Referencias a elementos del DOM
    const elements = {
        filterId: document.getElementById('filterIdentification'),
        filterType: document.getElementById('filterType'),
        clearBtn: document.getElementById('clearFiltersBtn'),
        pageSizeSelect: document.getElementById('pageSizeSelect'),
        newUserBtn: document.getElementById('newUserBtn'),
        usersTableBody: document.getElementById('usersTableBody'),
        modalContainer: document.getElementById('modalContainer'),
        modalContent: document.getElementById('modalContent'),
        closeModal: document.getElementById('closeModal'),
        currentPage: document.getElementById('currentPage'),
        pageSize: document.getElementById('pageSize'),
        totalUsers: document.getElementById('totalUsers')
    };

    // Inicialización - No cargamos datos iniciales aquí, ya vienen del servidor
    setupEventListeners();

    function setupEventListeners() {
        // Solo configuramos listeners si hay elementos de filtro
        if (elements.filterId || elements.filterType) {
            if (elements.filterId) elements.filterId.addEventListener('input', handleFilterChange);
            if (elements.filterType) elements.filterType.addEventListener('change', handleFilterChange);
            if (elements.clearBtn) elements.clearBtn.addEventListener('click', clearFilters);
        }
        
        if (elements.pageSizeSelect) elements.pageSizeSelect.addEventListener('change', handlePageSizeChange);
        if (elements.newUserBtn) elements.newUserBtn.addEventListener('click', handleNewUserClick);
        if (elements.modalContainer) elements.modalContainer.addEventListener('click', handleModalOutsideClick);
        if (elements.closeModal) elements.closeModal.addEventListener('click', closeModalHandler);
        
        // Delegación de eventos para elementos dinámicos
        document.addEventListener('click', function(e) {
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
    // Funciones de Filtrado
    // **********************
    
   function handleFilterChange() {
        clearTimeout(filterTimeout);
        filterTimeout = setTimeout(() => {
            // Solo cargar si hay filtros aplicados
            if (hasFilters()) {
                loadUsers();
            } else {
                // Si no hay filtros, mantener los datos iniciales
                resetToInitialState();
            }
        }, delay);
    }

    function clearFilters() {
        if (elements.filterId) elements.filterId.value = '';
        if (elements.filterType) elements.filterType.value = '';
        resetToInitialState();
    }

    function handlePageSizeChange() {
        if (elements.pageSize) elements.pageSize.value = this.value;
        loadUsers(0);
    }

    function hasFilters() {
        return (elements.filterId && elements.filterId.value.trim() !== '') || 
               (elements.filterType && elements.filterType.value !== '');
    }

    function resetToInitialState() {
        // Recargar la página para volver al estado inicial
        window.location.href = buildUrl('/admin/list', {
            page: 0,
            size: elements.pageSize?.value || 4
        });
    }

    function loadUsers(page = 0) {
        // Solo mostrar loading si hay filtros aplicados
        if (hasFilters()) {
            showLoadingState();
        }

        const params = {
            page: page,
            size: elements.pageSize?.value || 4,
            identification: elements.filterId?.value || '',
            type: elements.filterType?.value || ''
        };

        if (page !== parseInt(elements.currentPage?.value || 0)) {
            if (elements.currentPage) elements.currentPage.value = page;
        }

        fetch(buildUrl('/admin/list', params), {
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
        .then(response => {
            if (!response.ok) throw new Error('Error en la respuesta');
            return response.text();
        })
        .then(html => {
            if (html.includes('usersTableBody')) {
                updateUI(html);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            if (hasFilters()) {
                showErrorState();
            }
        });
    }

    function buildUrl(path, params) {
        const url = new URL(path, window.location.origin);
        Object.entries(params).forEach(([key, value]) => {
            if (value !== undefined && value !== '') {
                url.searchParams.append(key, value);
            }
        });
        return url;
    }

    function updateUI(html) {
        updateTableContent(html);
        updatePagination(html);
        updateUserCount(html);
    }

    function updateTableContent(html) {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;
        const newTableBody = tempDiv.querySelector('#usersTableBody');
        
        if (elements.usersTableBody && newTableBody) {
            elements.usersTableBody.innerHTML = newTableBody.innerHTML;
        }
    }

    function updatePagination(html) {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;
        const newPagination = tempDiv.querySelector('.pagination');
        const currentPagination = document.querySelector('.pagination');
        
        if (currentPagination && newPagination) {
            currentPagination.innerHTML = newPagination.innerHTML;
            
            document.querySelectorAll('.pagination a').forEach(link => {
                link.addEventListener('click', function(e) {
                    e.preventDefault();
                    const page = new URL(this.href).searchParams.get('page');
                    loadUsers(parseInt(page));
                });
            });
        }
    }

    function updateUserCount(html) {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;
        const newTotalUsers = tempDiv.querySelector('#totalUsers');
        
        if (elements.totalUsers && newTotalUsers) {
            elements.totalUsers.textContent = newTotalUsers.textContent;
        }
    }

    // **********************
    // Funciones de Interfaz
    // **********************
    
    function showLoadingState() {
        if (elements.usersTableBody) {
            elements.usersTableBody.innerHTML = `
                <tr>
                    <td colspan="7" class="text-center no-users">
                        <i class="fas fa-spinner fa-spin"></i> Cargando usuarios...
                    </td>
                </tr>
            `;
        }
    }

    function showErrorState() {
        if (elements.usersTableBody) {
            elements.usersTableBody.innerHTML = `
                <tr>
                    <td colspan="7" class="text-center no-users">
                        <i class="fas fa-exclamation-triangle"></i> Error al cargar los datos
                    </td>
                </tr>
            `;
        }
    }

    // **********************
    // Funciones de Modal
    // **********************
    
    function handleNewUserClick(e) {
        e.preventDefault();
        loadFormInModal(this.href);
    }

    function loadFormInModal(url) {
        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error('Error al cargar el formulario');
                return response.text();
            })
            .then(html => {
                if (elements.modalContent) {
                    elements.modalContent.innerHTML = html;
                    if (elements.modalContainer) elements.modalContainer.style.display = 'flex';
                    initFormScripts();
                    setupFormSubmitHandler();
                }
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire('Error', 'No se pudo cargar el formulario', 'error');
            });
    }

    function initFormScripts() {
        const tipoUsuarioSelect = document.getElementById('tipoUsuario');
        if (tipoUsuarioSelect) {
            tipoUsuarioSelect.addEventListener('change', function() {
                const empleadoFields = document.getElementById('empleadoFields');
                if (empleadoFields) {
                    empleadoFields.style.display = this.value === 'Empleado' ? 'block' : 'none';
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
        
        if (fileName) fileName.textContent = file.name;
        
        const reader = new FileReader();
        reader.onload = function(event) {
            if (preview) {
                preview.src = event.target.result;
                preview.style.display = 'block';
            }
        };
        reader.readAsDataURL(file);
    }

    function setupFormSubmitHandler() {
        const form = elements.modalContent?.querySelector('form');
        if (!form) return;
        
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            submitForm(this);
        });
    }

    function submitForm(form) {
        const formData = new FormData(form);
        
        fetch(form.action, {
            method: form.method,
            body: formData
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                return response.text().then(html => {
                    if (elements.modalContent) {
                        elements.modalContent.innerHTML = html;
                        initFormScripts();
                        setupFormSubmitHandler();
                    }
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire('Error', 'Error al enviar el formulario', 'error');
        });
    }

    function handleModalOutsideClick(e) {
        if (e.target === this) {
            closeModalHandler();
        }
    }

    function closeModalHandler() {
        if (elements.modalContainer) elements.modalContainer.style.display = 'none';
    }

    // **********************
    // Funciones de Confirmación
    // **********************
    
   function confirmDelete(event, button) {
        event.preventDefault();
        const form = button.closest('form');
        if (!form) return;
        
        Swal.fire({
            title: '¿Eliminar usuario?',
            text: "Esta acción no se puede deshacer",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#6f7e14',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
        });
    }
});