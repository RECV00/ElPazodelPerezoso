document.addEventListener('DOMContentLoaded', function() {
    // Obtener elementos del DOM
    const userTypeSelect = document.getElementById('userType');
    const empleadoFields = document.getElementById('empleadoFields');

    // Escuchar cambios en el select de tipo de usuario
    userTypeSelect.addEventListener('change', function() {
        // Mostrar campos de empleado solo si se selecciona "Empleado"
        if (this.value === 'EMPLOYEE') {
            empleadoFields.style.display = 'block';
        } else {
            empleadoFields.style.display = 'none';
            
            // Opcional: Limpiar campos cuando se ocultan
            document.getElementById('puesto').value = '';
            document.getElementById('numeroCuenta').value = '';
            document.getElementById('salario').value = '';
        }
    });

    // Validar campos de empleado si son visibles al enviar el formulario
    document.getElementById('userForm').addEventListener('submit', function(e) {
        if (userTypeSelect.value === 'EMPLOYEE') {
            const puesto = document.getElementById('puesto').value;
            const salario = document.getElementById('salario').value;
            
            if (!puesto || !salario) {
                e.preventDefault();
                alert('Por favor complete todos los campos requeridos para empleado');
            }
        }
    });
});