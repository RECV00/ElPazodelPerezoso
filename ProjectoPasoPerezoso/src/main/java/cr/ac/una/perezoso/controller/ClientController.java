/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Cabin;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.service.CabinService;
import cr.ac.una.perezoso.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/client")
public class ClientController {
   @Autowired
    private UserService userService;
    

   @Autowired
    private CabinService cabinService;
    
    // Perfil del cliente
    @GetMapping("/detalle")
    public String clientDetail(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            Client client = userService.getClientByIdentification(username);            
            List<Booking> bookings = userService.getClientBookings(client.getId_user());           
            model.addAttribute("user", client);
            model.addAttribute("bookings", bookings);
            return "/client/client_detalle";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al cargar los datos: " + e.getMessage());
            return "error";
        }
    }
    
    // Listado de cabañas
    @GetMapping("/cabins")
    public String listCabins(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Double maxPrice,
            Model model) {
        
        List<Cabin> cabins;
        
        if (location != null && !location.isEmpty()) {
            cabins = cabinService.findByLocation(location);
        } else if (capacity != null) {
            cabins = cabinService.findByCapacityGreaterThanEqual(capacity);
        } else if (maxPrice != null) {
            cabins = cabinService.findByPricePerNightBetween(0.0, maxPrice);
        } else {
            cabins = cabinService.getAll();
        }
        
        model.addAttribute("cabins", cabins);
        model.addAttribute("reservationMode", false);
        return "/client/cliente_cabins";
    }
    
    // Formulario de reserva
    @GetMapping("/cabins/reserve/{id}")
    public String showReservationForm(
            @PathVariable Integer id,
            Model model,
            Authentication authentication) {
        
        try {
            Cabin cabin = cabinService.getById(id);
            if (cabin == null) {
                return "redirect:/client/cabins";
            }
            
            // Obtener cliente autenticado
            String username = authentication.getName();
            Client client = userService.getClientByIdentification(username);
            
            model.addAttribute("cabin", cabin);
            model.addAttribute("client", client);
            model.addAttribute("reservationMode", true);
            
            return "/client/cliente_cabins";
            
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al cargar la cabaña: " + e.getMessage());
            return "error";
        }
    }
    
    // Procesar reserva
    @PostMapping("/cabins/book")
    public String processReservation(
            @RequestParam Integer cabinId,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate,
            @RequestParam Integer guests,
            @RequestParam(required = false) String specialRequests,
            Authentication authentication,
            Model model) {
        
        try {
            // Obtener cliente autenticado
            String username = authentication.getName();
            Client client = userService.getClientByIdentification(username);
            
            // Obtener cabaña
            Cabin cabin = cabinService.getById(cabinId);
            
            // Aquí iría la lógica para crear la reserva
            // Booking booking = new Booking(...);
            // userService.saveBooking(booking);
            
            return "redirect:/client/detalle";
            
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al procesar la reserva: " + e.getMessage());
            return "error";
        }
    }
    
    
}
