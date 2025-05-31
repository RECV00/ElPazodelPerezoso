// form_scripts.js
document.addEventListener('DOMContentLoaded', function() {
    // Solo ejecutar si no estamos en un modal
    if (!window.parent || window.parent === window) {
        // Mostrar/ocultar campos de empleado
        const tipoUsuarioSelect = document.getElementById('tipoUsuario');
        if (tipoUsuarioSelect) {
            tipoUsuarioSelect.addEventListener('change', function() {
                const empleadoFields = document.getElementById('empleadoFields');
                if (empleadoFields) {
                    empleadoFields.style.display = this.value === 'Empleado' ? 'block' : 'none';
                }
            });
            
            // Disparar el evento change al cargar
            tipoUsuarioSelect.dispatchEvent(new Event('change'));
        }
        
        // Vista previa de imagen
        const fileInput = document.querySelector('input[type="file"]');
        if (fileInput) {
            fileInput.addEventListener('change', function(e) {
                const file = e.target.files[0];
                const preview = document.getElementById('previewImage');
                const fileName = document.getElementById('fileName');
                
                if (file) {
                    fileName.textContent = file.name;
                    
                    const reader = new FileReader();
                    reader.onload = function(event) {
                        preview.src = event.target.result;
                        preview.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                } else {
                    preview.style.display = 'none';
                    fileName.textContent = 'No se ha seleccionado ninguna imagen';
                }
            });
        }
        
        // Validación del formulario
        const form = document.querySelector('form');
        if (form) {
            form.addEventListener('submit', function(e) {
                const tipoUsuario = document.getElementById('tipoUsuario')?.value;
                const nombre = document.getElementById('nombre')?.value;
                const apellido = document.getElementById('apellido')?.value;
                
                if (!tipoUsuario || !nombre || !apellido) {
                    e.preventDefault();
                    alert('Por favor complete los campos obligatorios');
                    return;
                }
                
                if (tipoUsuario === 'Empleado') {
                    const puesto = document.getElementById('puesto')?.value;
                    const salario = document.getElementById('salario')?.value;
                    
                    if (!puesto || !salario || salario <= 0) {
                        e.preventDefault();
                        alert('Para empleados, el puesto y salario son obligatorios');
                        return;
                    }
                }
            });
        }
    }
});
