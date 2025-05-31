document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('modalContainer');
    const modalContent = document.getElementById('modalContent');
    
    // Función para mostrar el modal
    window.showModal = function(content) {
        modalContent.innerHTML = content;
        modal.classList.add('show');
    };
    
    // Función para ocultar el modal
    window.hideModal = function() {
        modal.classList.remove('show');
    };
    
    // Cerrar al hacer clic en la X
    document.getElementById('closeModal').addEventListener('click', hideModal);
    
    // Cerrar al hacer clic fuera del contenido
    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            hideModal();
        }
    });
    
    // Función mejorada para cargar formularios
    window.loadFormInModal = function(url) {
        fetch(url, {
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(response => {
            if (!response.ok) throw new Error('Error en la respuesta');
            return response.text();
        })
        .then(html => {
            // Extraer solo el contenido del formulario
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = html;
            const formContent = tempDiv.querySelector('#formContent');
            
            if (!formContent) throw new Error('No se encontró el formulario');
            
            showModal(formContent.innerHTML);
            initFormScripts(); // Inicializar scripts del formulario
        })
        .catch(error => {
            console.error('Error:', error);
            modalContent.innerHTML = `
                <div class="error-message">
                    <p>Error al cargar el formulario</p>
                    <button onclick="hideModal()">Cerrar</button>
                </div>
            `;
            modal.classList.add('show');
        });
    };
});

// Función para inicializar los scripts del formulario
function initFormScripts() {
    console.log("Inicializando scripts del formulario...");
    
    // 1. Mostrar/ocultar campos de empleado
    const tipoSelect = document.getElementById('tipoUsuario');
    if (tipoSelect) {
        tipoSelect.addEventListener('change', function() {
            const empleadoFields = document.getElementById('empleadoFields');
            if (empleadoFields) {
                empleadoFields.style.display = this.value === 'Empleado' ? 'block' : 'none';
            }
        });
        // Disparar el evento inicial
        tipoSelect.dispatchEvent(new Event('change'));
    }
    
    // 2. Vista previa de imagen
    const fileInput = document.querySelector('input[type="file"]');
    if (fileInput) {
        fileInput.addEventListener('change', function(e) {
            const file = e.target.files[0];
            const preview = document.getElementById('previewImage') || 
                          document.getElementById('previewImageNew');
            const fileName = document.getElementById('fileName') || 
                           document.getElementById('fileNameEdit');
            
            if (file) {
                if (fileName) fileName.textContent = file.name;
                if (preview) {
                    const reader = new FileReader();
                    reader.onload = function(event) {
                        preview.src = event.target.result;
                        preview.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                }
            }
        });
    }
}