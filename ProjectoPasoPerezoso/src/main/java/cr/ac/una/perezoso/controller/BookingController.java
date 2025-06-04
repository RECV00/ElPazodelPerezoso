/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.jpa.BookingRepository;
import cr.ac.una.perezoso.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author corra
 */
@Controller
@RequestMapping("/booking")
public class BookingController {
    
    private  BookingService bookingService;
    private  BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }
  
    @GetMapping("/listaReservas")
//    public String showBookings(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            Model model) {
//        
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Booking> bookingPage = bookingService.getAllReservations(pageable);
//        
//        model.addAttribute("bookings", bookingPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", bookingPage.getTotalPages());
//        model.addAttribute("pageSize", size);
//        
//        loadRelatedData(model, bookingPage.getContent());
//        
//        return "booking/listBooking"; 
//    }
public String showBookings(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Integer cabinId,
        @RequestParam(required = false) Integer tourId,
        @RequestParam(required = false) Integer vehicleId,
        @RequestParam(required = false) Integer foodId,
        @RequestParam(required = false) Integer clientId,
        @RequestParam(required = false) String status,
        Model model) {
    
    try {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage;
        
        // Construir la consulta según los parámetros recibidos
        if (cabinId != null) {
            bookingPage = bookingRepository.findByCabinId(cabinId, pageable);
        } else if (tourId != null) {
            bookingPage = bookingRepository.findByTourId(tourId, pageable);
        } else if (vehicleId != null) {
            bookingPage = bookingRepository.findByVehicleId(vehicleId, pageable);
        } else if (foodId != null) {
            bookingPage = bookingRepository.findByFoodId(foodId, pageable);
        } else if (clientId != null) {
            bookingPage = bookingRepository.findByClientId(clientId, pageable);
        } else if (status != null && !status.isEmpty()) {
            bookingPage = bookingRepository.findByReserveStatus(status, pageable);
        } else {
            bookingPage = bookingRepository.findAll(pageable);
        }
        
        // Obtener nombres de las entidades relacionadas
        Map<Integer, String> clientNames = new HashMap<>();
        Map<Integer, String> cabinNames = new HashMap<>();
        Map<Integer, String> tourNames = new HashMap<>();
        Map<Integer, String> vehicleNames = new HashMap<>();
        Map<Integer, String> foodNames = new HashMap<>();
        
        for (Booking booking : bookingPage.getContent()) {
            // Obtener nombre del cliente
            if (booking.getClient() != null && !clientNames.containsKey(booking.getClient().getId_user())) {
                clientNames.put(booking.getClient().getId_user(), 
                    booking.getClient().getName() + " " + booking.getClient().getLast_name());
            }
            
            // Obtener nombre de la cabaña
           if (booking.getCabin() != null && !cabinNames.containsKey(booking.getCabin().getCabinID())) {
                cabinNames.put(booking.getCabin().getCabinID(), booking.getCabin().getName());
            }
            
            // Obtener nombre del tour
            if (booking.getTour() != null && !tourNames.containsKey(booking.getTour().getId_tour())) {
                tourNames.put(booking.getTour().getId_tour(), booking.getTour().getNameTour());
            }
            
            // Obtener nombre del vehículo
            if (booking.getTransportation() != null && !vehicleNames.containsKey(booking.getTransportation().getId_transportation())) {
                vehicleNames.put(booking.getTransportation().getId_transportation(), 
                    booking.getTransportation().getPlate() + " " + booking.getTransportation().getDriver());
            }
            
            // Obtener nombre de la comida
            if (booking.getFood() != null && !foodNames.containsKey(booking.getFood().getId_food())) {
                foodNames.put(booking.getFood().getId_food(), booking.getFood().getSelectedMenu());
            }
        }
        
        // Agregar atributos al modelo
        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("clientNames", clientNames);
        model.addAttribute("cabinNames", cabinNames);
        model.addAttribute("tourNames", tourNames);
        model.addAttribute("vehicleNames", vehicleNames);
        model.addAttribute("foodNames", foodNames);
        
        // Paginación
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        model.addAttribute("pageSize", size);
        
        // Parámetros de búsqueda para mantener en la vista
        model.addAttribute("cabinId", cabinId);
        model.addAttribute("tourId", tourId);
        model.addAttribute("vehicleId", vehicleId);
        model.addAttribute("foodId", foodId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("status", status);
        
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Error al cargar las reservaciones: " + e.getMessage());
    }
    
    return "booking/listBooking";
}
@GetMapping("/search")
public String searchBookings(
        @RequestParam(required = false) String clientId,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model,
        HttpServletRequest request) {
    
    try {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage;
        
        if (clientId != null && !clientId.isEmpty()) {
            // Buscar por cédula del cliente
            bookingPage = bookingRepository.findByClient_IdentificationContainingIgnoreCase(clientId, pageable);
        } else if (status != null && !status.isEmpty()) {
            // Buscar por estado
            bookingPage = bookingRepository.findByReserveStatusContainingIgnoreCase(status, pageable);
        } else if (startDate != null && endDate != null) {
            // Buscar por rango de fechas (usando checkInDate)
            bookingPage = bookingRepository.findByCheckInDateBetween(startDate, endDate, pageable);
        } else {
            // Mostrar todas las reservaciones
            bookingPage = bookingRepository.findAll(pageable);
        }
        
        // Cargar datos relacionados
        loadRelatedData(model, bookingPage.getContent());
        
        // Agregar atributos al modelo
        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        model.addAttribute("pageSize", size);
        
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Error al cargar las reservaciones: " + e.getMessage());
    }
    
    // Devolver fragmento HTML si es AJAX
    if (isAjaxRequest(request)) {
        return "booking/listBooking :: #bookings-container";
    }
    return "booking/listBooking";
}

    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "booking/addBooking";
    }

    @PostMapping("/save")
    public String saveBooking(@ModelAttribute Booking booking,
                            @RequestParam String clientIdentification,
                            RedirectAttributes redirectAttributes) {
        
        try {
            Booking savedBooking = bookingService.createReservation(booking, clientIdentification);
            redirectAttributes.addFlashAttribute("success", "Booking created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating booking: " + e.getMessage());
            return "redirect:/booking/new";
        }
        return "redirect:/booking";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        bookingService.getReservationById(id).ifPresentOrElse(
            booking -> model.addAttribute("booking", booking),
            () -> model.addAttribute("error", "Booking not found")
        );
        return "booking/editBooking";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable int id,
                              @ModelAttribute Booking booking,
                              RedirectAttributes redirectAttributes) {
        
        try {
            booking.setId_booking(id);
            bookingService.updateReservation(id, booking);
            redirectAttributes.addFlashAttribute("success", "Booking updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating booking: " + e.getMessage());
        }
        return "redirect:/booking";
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteReservation(id);
            redirectAttributes.addFlashAttribute("success", "Booking deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting booking: " + e.getMessage());
        }
        return "redirect:/booking/listaReservas";
    }

   private void loadRelatedData(Model model, List<Booking> bookings) {
    Map<Integer, String> clientNames = new HashMap<>();
    Map<Integer, String> cabinNames = new HashMap<>();
    Map<Integer, String> tourNames = new HashMap<>();
    Map<Integer, String> vehicleNames = new HashMap<>();
    Map<Integer, String> foodNames = new HashMap<>();
    Map<Integer, String> clientIdentifications = new HashMap<>();
    
    bookings.forEach(booking -> {
        if (booking.getClient() != null) {
            clientNames.put(booking.getClient().getId_user(), 
                booking.getClient().getName() + " " + booking.getClient().getLast_name());
        }
        if (booking.getCabin() != null) {
            cabinNames.put(booking.getCabin().getCabinID(), booking.getCabin().getName());
        }
        if (booking.getTour() != null) {
            tourNames.put(booking.getTour().getId_tour(), booking.getTour().getNameTour());
        }
        if (booking.getTransportation() != null) {
            vehicleNames.put(booking.getTransportation().getId_transportation(),
                booking.getTransportation().getPlate() + " " + booking.getTransportation().getDriver());
        }
        if (booking.getFood() != null) {
            foodNames.put(booking.getFood().getId_food(), booking.getFood().getSelectedMenu());
        }
         if (booking.getClient() != null) {
            clientNames.put(booking.getClient().getId_user(), 
                booking.getClient().getName() + " " + booking.getClient().getLast_name());
            clientIdentifications.put(booking.getClient().getId_user(), 
                booking.getClient().getIdentification());
        }
    });
    
    model.addAttribute("clientNames", clientNames);
    model.addAttribute("cabinNames", cabinNames);
    model.addAttribute("tourNames", tourNames);
    model.addAttribute("vehicleNames", vehicleNames);
    model.addAttribute("foodNames", foodNames);
    model.addAttribute("clientIdentifications", clientIdentifications);
}

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    
    
    @GetMapping("/details/{id}")
public String showBookingDetails(@PathVariable int id, Model model) {
    try {
        // Obtener la reserva con todos los datos relacionados
        Booking booking = bookingService.getReservationWithDetails(id)
            .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
        
        // Calcular totales
//        double totalPaid = booking.getPayment().stream()
//            .filter(p -> "Completado".equals(p.getStatus()))
//            .mapToDouble(Payment::getAmount)
//            .sum();
        double totalPaid=34000.34;
        double totalPrice = 300000;
//                bookingService.calculateTotalPrice(booking);
        
        // Agregar atributos al modelo
        model.addAttribute("booking", booking);
        model.addAttribute("totalPaid", totalPaid);
        model.addAttribute("totalPrice", totalPrice);
        
    } catch (EntityNotFoundException e) {
        model.addAttribute("error", e.getMessage());
        return "redirect:/booking/listaReservas";
    } catch (Exception e) {
        model.addAttribute("error", "Error al cargar los detalles: " + e.getMessage());
        return "redirect:/booking/listaReservas";
    }
    
    return "booking/detailsBooking";
}
//    return "bookings/listBooking :: #bookings-table"; // For AJAX
//return "bookings/listBooking"; // For normal
}

