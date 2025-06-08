/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cr.ac.una.perezoso.domain.Transportation;
import cr.ac.una.perezoso.service.TransportationService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author corra
 */
@Controller
@RequestMapping("/transportation")
public class TransportationController {
    
    @Autowired
    private TransportationService transportationService;
    
      // Vista completa con paginación
    @GetMapping("/listTransport")
    public String listTransports(
        @RequestParam(value = "plate", required = false) String plate,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "4") int size,
        Model model) {
        
        Page<Transportation> transportPage = getFilteredTransports(plate, status, page, size);
        
        model.addAttribute("transportations", transportPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transportPage.getTotalPages());
        return "/transportation/listTransportations";
    }
    
     // Endpoint para AJAX
    @GetMapping("/api/list")
    @ResponseBody
    public Page<Transportation> listTransportsApi(
        @RequestParam(value = "plate", required = false) String plate,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "4") int size) {
        
        return getFilteredTransports(plate, status, page, size);
    }

    private Page<Transportation> getFilteredTransports(String plate, String status, int page, int size) {
        if (plate != null && !plate.isEmpty()) {
            return transportationService.findByPlateContaining(plate, PageRequest.of(page, size));
        } else if (status != null && !status.isEmpty()) {
            return transportationService.findByStatus(status, PageRequest.of(page, size));
        } else {
            return transportationService.getAll(PageRequest.of(page, size));
        }
    }
  
   
//--------------------------------ANADIR------------------------------------------------------------------------
    // Mostrar formulario para agregar un nuevo transporte
      // Métodos para modales
    @GetMapping("/add-form")
    public String getAddModal(Model model) {
        model.addAttribute("transportation", new Transportation());
        return "/Transportation/add_transportation_modal :: addModal";
    }


    // Procesar el formulario para agregar un nuevo transporte
   @PostMapping("/add")
    public String addTransportation(
        @RequestParam("plate") String plate,
        @RequestParam("driver") String driver,
        @RequestParam("dataTimeService") LocalDateTime dataTimeService,
        @RequestParam("initialLocation") String initialLocation,
        @RequestParam("finalLocation") String finalLocation,
        @RequestParam("serviceStatus") String serviceStatus,
        @RequestParam("serviceDuration") int serviceDuration,
        RedirectAttributes redirectAttributes) {
        
        // Validar si la placa ya existe
        if (transportationService.getByPlate(plate) != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "La placa ya está registrada");
            return "redirect:/transportation/add";
        }
        
        Transportation transportation = new Transportation(
            plate, driver, dataTimeService, 
            initialLocation, finalLocation, 
            serviceStatus, serviceDuration
        );
        
        transportationService.save(transportation);
        redirectAttributes.addFlashAttribute("successMessage", "Transporte agregado correctamente.");
        return "redirect:/transportation/listTransport";
    }

//--------------------------------------------EDITAR--------------------------------------------------------------------
    // Mostrar formulario para editar un transporte
     @GetMapping("/edit-form")
    public String getEditModal(@RequestParam("id") int id, Model model) {
        Transportation transportation = transportationService.getById(id);
        if (transportation == null) {
            return "redirect:/transportation/listTransport?error=Transporte no encontrado";
        }
        model.addAttribute("transportation", transportation);
        return "/Transportation/edit_transportation_modal :: editModal";
    }

    // Procesar el formulario para actualizar un transporte
     @PostMapping("/update")
    public String updateTransportation(
        @RequestParam("id") int id,
        @RequestParam("plate") String plate,
        @RequestParam("driver") String driver,
        @RequestParam("dataTimeService") LocalDateTime dataTimeService,
        @RequestParam("initialLocation") String initialLocation,
        @RequestParam("finalLocation") String finalLocation,
        @RequestParam("serviceStatus") String serviceStatus,
        @RequestParam("serviceDuration") int serviceDuration,
        RedirectAttributes redirectAttributes) {
        
        Transportation existingTransport = transportationService.getById(id);
        
        if (existingTransport == null) {
            return "redirect:/transportation/listTransport?error=Transporte no encontrado";
        }
        
        // Verificar si la nueva placa ya existe (excepto para este mismo vehículo)
        Transportation transportWithSamePlate = transportationService.getByPlate(plate);
        if (transportWithSamePlate != null && !transportWithSamePlate.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "La placa ya está registrada en otro vehículo");
            return "redirect:/transportation/listTransport";
        }
        
        existingTransport.setPlate(plate);
        existingTransport.setDriver(driver);
        existingTransport.setDataTimeService(dataTimeService);
        existingTransport.setInitialLocation(initialLocation);
        existingTransport.setFinalLocation(finalLocation);
        existingTransport.setServiceStatus(serviceStatus);
        existingTransport.setServiceDuration(serviceDuration);
        
        transportationService.save(existingTransport);
        redirectAttributes.addFlashAttribute("successMessage", "Transporte actualizado correctamente.");
        return "redirect:/transportation/listTransport";
    }

//-----------------------------------ELIMINAR-------------------------------------------------------------------------
    // Eliminar un transporte
     @DeleteMapping("/delete/{id}")
    public String deleteTransportation(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        transportationService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Transporte eliminado correctamente.");
        return "redirect:/transportation/listTransport";
    }
}
