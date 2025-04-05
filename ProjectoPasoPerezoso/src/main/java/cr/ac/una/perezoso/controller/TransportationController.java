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
import cr.ac.una.perezoso.data.TransportationData;
import cr.ac.una.perezoso.domain.Transportation;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
       // Mostrar todos los transportes
    @GetMapping("/listTransport")
    public String showTransportations(Model model) {
        List<Transportation> transportations = TransportationData.getTransportations();
        model.addAttribute("transportations", transportations);
        return "transportation/listTransportations"; 
    }
//---------------------------------FILTRO-------------------------------------------------------------------------
    
    // Filtrar transportes por conductor o ID de vehículo
    @GetMapping("/filter")
    public String filterTransportations(@RequestParam(required = false) String filter, Model model) {
        try {
            List<Transportation> transportations;
            if (filter != null && !filter.isEmpty()) {
                transportations = TransportationData.searchTransportations(filter);
            } else {
                transportations = TransportationData.getTransportations();
            }
            model.addAttribute("transportations", transportations);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "transportation/listTransportations";
    }
//--------------------------------ANADIR------------------------------------------------------------------------
    // Mostrar formulario para agregar un nuevo transporte
    @GetMapping("/add")
    public String showAddTransportationForm(Model model) {
        model.addAttribute("transportation", new Transportation());
        return "transportation/addTransportation";
    }

    // Procesar el formulario para agregar un nuevo transporte
    @PostMapping("/add")
    public String addTransportation(
        @RequestParam("idVehicle") String idVehicle,
        @RequestParam("driver") String driver,
        @RequestParam("dataTimeService") LocalDateTime dataTimeService,
        @RequestParam("initialLocation") String initialLocation,
        @RequestParam("finalLocation") String finalLocation,
        @RequestParam("serviceStatus") String serviceStatus,
        @RequestParam("serviceDuration") int serviceDuration,
        RedirectAttributes redirectAttributes
    ) {
        Transportation transportation = new Transportation(
            idVehicle, driver, dataTimeService, 
            initialLocation, finalLocation, 
            serviceStatus, serviceDuration
        );
        
        TransportationData.createTransportation(transportation);
        redirectAttributes.addFlashAttribute("successMessage", "Transporte agregado correctamente.");
        return "redirect:/transportation/listTransport";
    }

//--------------------------------------------EDITAR--------------------------------------------------------------------
    // Mostrar formulario para editar un transporte
    @GetMapping("/edit/{id}")
    public String showEditTransportationForm(@PathVariable int id, Model model) {
        try {
            Transportation transportation = TransportationData.getTransportationById(id);
            model.addAttribute("transportation", transportation);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "transportation/editTransportation"; 
    }

    // Procesar el formulario para actualizar un transporte
    @PostMapping("/edit/{id}")
    public String updateTransportation(
        @RequestParam("id") int id,
        @RequestParam("idVehicle") String idVehicle,
        @RequestParam("driver") String driver,
        @RequestParam("dataTimeService") LocalDateTime dataTimeService,
        @RequestParam("initialLocation") String initialLocation,
        @RequestParam("finalLocation") String finalLocation,
        @RequestParam("serviceStatus") String serviceStatus,
        @RequestParam("serviceDuration") int serviceDuration,
        RedirectAttributes redirectAttributes
    ) throws SQLException, ClassNotFoundException {
        
        Transportation transportation = TransportationData.getTransportationById(id);
        transportation.setIdVehicle(idVehicle);
        transportation.setDriver(driver);
        transportation.setDataTimeService(dataTimeService);
        transportation.setInitialLocation(initialLocation);
        transportation.setFinalLocation(finalLocation);
        transportation.setServiceStatus(serviceStatus);
        transportation.setServiceDuration(serviceDuration);
        
        TransportationData.updateTransportation(transportation);
        redirectAttributes.addFlashAttribute("successMessage", "Transporte actualizado correctamente.");
        return "redirect:/transportation/listTransport";
    }
//-----------------------------------ELIMINAR-------------------------------------------------------------------------
    // Eliminar un transporte
    @GetMapping("/delete/{id}")
    public String deleteTransportation(@PathVariable int id) {
        TransportationData.deleteTransportation(id);
        return "redirect:/transportation/listTransport";
    }
}
