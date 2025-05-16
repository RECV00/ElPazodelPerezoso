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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
       // Mostrar todos los transportes
    @GetMapping("/listTransport")
    public String showTransportations(Model model) {
        List<Transportation> transportations = transportationService.getAll();
        model.addAttribute("transportations", transportations);
        return "/transportation/listTransportations"; 
    }
//---------------------------------FILTRO-------------------------------------------------------------------------
    
    // Filtrar transportes por conductor o ID de vehículo
    @GetMapping("/filter")
    public String filterTransportations(@RequestParam(required = false) String filter, Model model) {
        List<Transportation> transportations;
        if (filter != null && !filter.isEmpty()) {
            transportations = transportations = transportationService.searchByPlateOrDriver(filter);
        } else {
            transportations = transportationService.getAll();
        }
        model.addAttribute("transportations", transportations);
        return "/transportation/listTransportations";
    }
//--------------------------------ANADIR------------------------------------------------------------------------
    // Mostrar formulario para agregar un nuevo transporte
    @GetMapping("/add")
    public String showAddTransportationForm(Model model) {
        model.addAttribute("transportation", new Transportation());
        return "/transportation/addTransportation";
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

//    @GetMapping("/edit/{id}")
//    public String showEditTransportationForm1(@PathVariable Integer id, Model model) {
//        Transportation transportation = transportationService.getById(id);
//        if (transportation == null) {
//            return "redirect:/transportation/listTransport?error=Transporte no encontrado";
//        }
//        model.addAttribute("transportation", transportation);
//        return "/transportation/editTransportation";
//    }
//    
    
//--------------------------------------------EDITAR--------------------------------------------------------------------
    // Mostrar formulario para editar un transporte
    @GetMapping("/edit/{id}")
    public String showEditTransportationForm(@PathVariable Integer id, Model model) {
        Transportation transportation = transportationService.getById(id);
        if (transportation == null) {
            return "redirect:/transportation/listTransport?error=Transporte no encontrado";
        }
        model.addAttribute("transportation", transportation);
        return "/transportation/editTransportation";
    }

    // Procesar el formulario para actualizar un transporte
     @PostMapping("/edit/{id}")
    public String updateTransportation(
        @PathVariable Integer id,
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
            return "redirect:/transportation/edit/" + id;
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
    @GetMapping("/delete/{id}")
    public String deleteTransportation(@PathVariable Integer id) {
        transportationService.delete(id);
        return "redirect:/transportation/listTransport";
    }
}
