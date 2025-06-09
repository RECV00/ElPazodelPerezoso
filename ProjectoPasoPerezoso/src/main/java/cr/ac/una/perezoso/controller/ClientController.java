/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Cabin;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.service.BookingService;
import cr.ac.una.perezoso.service.CabinService;
import cr.ac.una.perezoso.service.UserService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    private BookingService bookingService;
   
    
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
    
     // Listado de cabañas con filtros y verificación de disponibilidad
    @GetMapping("/cabins")
    public String listCabins(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            Model model) {
        
        List<Cabin> cabins;
        
        // Filtrado básico
        if (location != null && !location.isEmpty()) {
            cabins = cabinService.findByLocation(location);
        } else if (capacity != null) {
            cabins = cabinService.findByCapacityGreaterThanEqual(capacity);
        } else if (maxPrice != null) {
            cabins = cabinService.findByPricePerNightBetween(0.0, maxPrice);
        } else {
            cabins = cabinService.getAll();
        }
        
        // Nuevo: Crear mapa de disponibilidad
    Map<Integer, Boolean> availabilityMap = new HashMap<>();
    
    if (checkIn != null && checkOut != null) {
        for (Cabin cabin : cabins) {
            boolean isAvailable = bookingService.isCabinAvailable(cabin.getCabinID(), checkIn, checkOut);
            availabilityMap.put(cabin.getCabinID(), isAvailable);
        }
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
    } else {
        // Si no hay fechas, todas están disponibles
        cabins.forEach(cabin -> availabilityMap.put(cabin.getCabinID(), true));
    }
    
    model.addAttribute("cabins", cabins);
    model.addAttribute("availabilityMap", availabilityMap);
    model.addAttribute("reservationMode", false);
    return "/client/cliente_cabins";
    
    }  
     // Formulario de reserva
    @GetMapping("/cabins/reserve/{id}")
public String showReservationForm(
        @PathVariable Integer id,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
        Model model,
        Authentication authentication) {
    
    try {
        Cabin cabin = cabinService.getById(id);
        if (cabin == null) {
            return "redirect:/client/cabins";
        }
        
        if (checkIn != null && checkOut != null) {
            boolean isAvailable = bookingService.isCabinAvailable(cabin.getCabinID(), checkIn, checkOut);
            if (!isAvailable) {
                model.addAttribute("error", "La cabaña no está disponible en las fechas seleccionadas");
                return "redirect:/client/cabins?checkIn=" + checkIn + "&checkOut=" + checkOut;
            }
            model.addAttribute("checkIn", checkIn);
            model.addAttribute("checkOut", checkOut);
        }
        
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
    
    // Procesar reserva con validación de disponibilidad
    @PostMapping("/cabins/book")
    public String processReservation(
            @RequestParam Integer cabinId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam Integer guests,
            @RequestParam(required = false) String specialRequests,
            Authentication authentication,
            Model model) {
        
        try {
            // Verificar disponibilidad primero
            if (!bookingService.isCabinAvailable(cabinId, checkInDate, checkOutDate)) {
                model.addAttribute("error", "La cabaña ya no está disponible en las fechas seleccionadas");
                Cabin cabin = cabinService.getById(cabinId);
                model.addAttribute("cabin", cabin);
                model.addAttribute("reservationMode", true);
                return "/client/cliente_cabins";
            }
            
            // Obtener cliente autenticado
            String username = authentication.getName();
            Client client = userService.getClientByIdentification(username);
            
            // Obtener cabaña
            Cabin cabin = cabinService.getById(cabinId);
          
            return "redirect:/client/detalle";
            
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al procesar la reserva: " + e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/bookings")
    public String showClientBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Asumiendo que el username es el email
        
         Client client = userService.getClientByIdentification(email);  
        List<Booking> bookings = bookingService.findByClient(client);
    
        model.addAttribute("bookings", bookings);
        model.addAttribute("client", client);
        
        return "client/bookings";
    }
}

