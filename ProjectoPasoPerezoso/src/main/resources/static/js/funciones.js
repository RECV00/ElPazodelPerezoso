
function mostrarVentanaDetalles(event) {
    event.preventDefault();  

    const contenedor = document.getElementById("contenedor-edit");
    contenedor.innerHTML = '<div class="cargando">Cargando detalles...</div>';
    contenedor.style.display = 'block';  

    
    const maintenanceId = event.currentTarget.getAttribute('data-id');

    if (!maintenanceId) {
        contenedor.innerHTML = '<p>Error: No se pudo obtener el ID</p>';
        return;
    }

   
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", `/detalleMantenimiento?id=${maintenanceId}`, true);
    xhttp.send();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4) {
            if (this.status === 200) {
                contenedor.innerHTML = this.responseText;  

               
                const btnRegresar = contenedor.querySelector(".btn-regresar");
                if (btnRegresar) {
                    btnRegresar.addEventListener("click", function(e) {
                        e.preventDefault();
                        contenedor.style.display = 'none';  
                        contenedor.innerHTML = '';  
                    });
                }
            } else {
                contenedor.innerHTML = `<p>Error al cargar los detalles</p>`;
            }
        }
    };
}



