/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;


import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Cabin;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.Dishe;
import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.domain.Transportation;
import cr.ac.una.perezoso.jpa.BookingRepository;
import cr.ac.una.perezoso.service.BookingService;
import cr.ac.una.perezoso.service.CabinService;
import cr.ac.una.perezoso.service.DisheService;
import cr.ac.una.perezoso.service.TourService;
import cr.ac.una.perezoso.service.TransportationService;
import cr.ac.una.perezoso.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private TourService tourService;
    private UserService userService; 
    private TransportationService transportationService;
    private DisheService disheService;
    private CabinService cabinService;
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    public BookingController(BookingService bookingService, 
            BookingRepository bookingRepository, 
            TourService tourService,
            UserService  userService,
            TransportationService transportationService,
            DisheService disheService,
            CabinService cabinService) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.tourService = tourService;
        this.userService = userService;
        this.transportationService = transportationService;
        this.disheService = disheService;
        this.cabinService = cabinService;
    }
  
    @GetMapping("/listaReservas")
public String showBookings(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) Integer cabinId,
        @RequestParam(required = false) Integer tourId,
        @RequestParam(required = false) Integer vehicleId,
        @RequestParam(required = false) Integer disheId,
        @RequestParam(required = false) Integer clientId,
        @RequestParam(required = false) String status,
        Model model) {
    
    try {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage;
        
        
        if (cabinId != null) {
            bookingPage = bookingRepository.findByCabinId(cabinId, pageable);
        } else if (tourId != null) {
            bookingPage = bookingRepository.findByTourId(tourId, pageable);
        } else if (vehicleId != null) {
            bookingPage = bookingRepository.findByVehicleId(vehicleId, pageable);
        } else if (disheId != null) {
            bookingPage = bookingRepository.findByDisheId(disheId, pageable);
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
        Map<Integer, String> disheNames = new HashMap<>();
        
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
            if (booking.getDishe() != null && !disheNames.containsKey(booking.getDishe().getDisheID())) {
                disheNames.put(booking.getDishe().getDisheID(), booking.getDishe().getName());
            }
        }
        
        // Agregar atributos al modelo
        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("clientNames", clientNames);
        model.addAttribute("cabinNames", cabinNames);
        model.addAttribute("tourNames", tourNames);
        model.addAttribute("vehicleNames", vehicleNames);
        model.addAttribute("disheNames", disheNames);
        
        // Paginación
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        model.addAttribute("pageSize", size);
        
        // Parámetros de búsqueda para mantener en la vista
        model.addAttribute("cabinId", cabinId);
        model.addAttribute("tourId", tourId);
        model.addAttribute("vehicleId", vehicleId);
        model.addAttribute("disheId", disheId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("status", status);
        
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Error al cargar las reservaciones: " + e.getMessage());
    }
    
    return "/booking/listBooking";
}
//----------------------AGREGAR--------------------------------------------------------------------------------------------------------
 @GetMapping("/new")
    public String showAddForm(Model model) {
        Booking booking = new Booking();
        List<Tour> tours = tourService.getAll();
        List<Transportation> transportations = transportationService.getAll();
        List<Dishe> dishes = disheService.getAll();
        List<Cabin> cabins = cabinService.getAll();
        
        
        model.addAttribute("booking", booking);
        model.addAttribute("tours", tours);
        model.addAttribute("transportations", transportations);
        model.addAttribute("dishes", dishes);
        model.addAttribute("cabins", cabins);
        return "/booking/addBooking";
    }
@PostMapping("/save")
public String saveBooking(@ModelAttribute("booking") Booking booking,
                        BindingResult bindingResult,
                        @RequestParam(value = "clientIdentification", required = true) String identification,
                        @RequestParam(value = "selectedTourId", required = false) Integer tourId,
                        @RequestParam(value = "selectedTransportationId", required = false) Integer transportationId,
                        @RequestParam(value = "selectedDisheId", required = false) Integer disheId,
                        @RequestParam(value = "selectedCabinId", required = false) Integer cabinId,
                        @RequestParam(value = "services", required = false) List<String> services,
                        Model model,
                        RedirectAttributes redirectAttributes) {
    
    // Validar cliente
    Client client = userService.getClientByIdentification(identification);
    if (client == null) {
        bindingResult.rejectValue("client", "client.notFound", "Cliente no encontrado");
    }
    
     // Validar cabaña
    Cabin cabin = cabinService.getById(cabinId);
    if (cabin == null) {
        bindingResult.rejectValue("cabin", "cabin.notFound", "Debe seleccionar una cabaña válida");
    }
    // Validar servicios adicionales
    if (services != null) {
        if (services.contains("tour") && tourId == null) {
            bindingResult.rejectValue("tour", "tour.required", "Debe seleccionar un Tour");
        }
        if (services.contains("transporte") && transportationId == null) {
            bindingResult.rejectValue("transportation", "transportation.required", "Debe seleccionar un Transporte");
        }
        if (services.contains("alimentacion") && disheId == null) {
            bindingResult.rejectValue("dishe", "dishe.required", "Debe seleccionar un Platillo");
        }
    }
    
    if (bindingResult.hasErrors()) {
        model.addAttribute("tours", tourService.getAll());
        model.addAttribute("transportations", transportationService.getAll());
        model.addAttribute("dishes", disheService.getAll());
         
        return "/booking/addBooking";
    }
    
    try {
        booking.setClient(client);
        booking.setCabin(cabin);
        
        //  Servicios adicionales
        if (services != null && !services.isEmpty()) {
            booking.setAdditionalServices(true);
            
            if (services.contains("tour") && tourId != null) {
                Tour tour = tourService.findById(tourId).orElse(null);
                booking.setTour(tour);
            }
            
            if (services.contains("transporte") && transportationId != null) {
                Transportation transportation = transportationService.findById(transportationId).orElse(null);
                booking.setTransportation(transportation);
            }
            if (services.contains("alimentacion") && disheId != null) {
                Dishe dishe = disheService.findById(disheId).orElse(null);
                booking.setDishe(dishe);
            }
        } else {
            booking.setAdditionalServices(false);
        }
        
        bookingService.save(booking);
        redirectAttributes.addFlashAttribute("success", "Reserva guardada exitosamente");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al guardar la reserva: " + e.getMessage());
        return "redirect:/booking/new";
    }
    
    return "redirect:/booking/listaReservas";
}
    @GetMapping("/clients/findByIdentification")
    @ResponseBody
    public Client findClientByIdentification(@RequestParam String identification) {
        return userService.getClientByIdentification(identification);
    }
    
    @GetMapping("/edit/{id}")
public String showEditForm(@PathVariable int id, Model model) {
    Optional<Booking> bookingOpt = bookingService.getReservationById(id);
    
    if (bookingOpt.isEmpty()) {
        model.addAttribute("error", "Reserva no encontrada");
        return "redirect:/booking/listaReservas";
    }
    
    Booking booking = bookingOpt.get();
    
    // Cargar todos los datos 
    List<Tour> tours = tourService.getAll();
    List<Transportation> transportations = transportationService.getAll();
    List<Dishe> dishes = disheService.getAll();
    List<Cabin> cabins = cabinService.getAll();
    
    model.addAttribute("booking", booking);
    model.addAttribute("tours", tours);
    model.addAttribute("transportations", transportations);
    model.addAttribute("dishes", dishes);
    model.addAttribute("cabins", cabins);
    
    return "/booking/editBooking";
}

@PostMapping("/update/{id}")
public String updateBooking(@PathVariable int id,
                          @ModelAttribute("booking") Booking booking,
                          BindingResult bindingResult,
                          @RequestParam(value = "selectedTourId", required = false) Integer tourId,
                          @RequestParam(value = "selectedTransportationId", required = false) Integer transportationId,
                          @RequestParam(value = "selectedDisheId", required = false) Integer disheId,
                          @RequestParam(value = "selectedCabinId", required = true) Integer cabinId,
                          @RequestParam(value = "services", required = false) List<String> services,
                          Model model,
                          RedirectAttributes redirectAttributes) {
    
    // Validar que la reserva existe
    Optional<Booking> existingBookingOpt = bookingService.getReservationById(id);
    if (existingBookingOpt.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "Reserva no encontrada");
        return "redirect:/booking/listaReservas";
    }
    
    // Validar cabaña
    Cabin cabin = cabinService.getById(cabinId);
    if (cabin == null) {
        bindingResult.rejectValue("cabin", "cabin.notFound", "Debe seleccionar una cabaña válida");
    }
    
    // Validar servicios adicionales
    if (services != null) {
        if (services.contains("tour") && tourId == null) {
            bindingResult.rejectValue("tour", "tour.required", "Debe seleccionar un Tour");
        }
        if (services.contains("transporte") && transportationId == null) {
            bindingResult.rejectValue("transportation", "transportation.required", "Debe seleccionar un Transporte");
        }
        if (services.contains("alimentacion") && disheId == null) {
            bindingResult.rejectValue("dishe", "dishe.required", "Debe seleccionar un Platillo");
        }
    }
    
    if (bindingResult.hasErrors()) {
        // Recargar los datos necesarios para mostrar el formulario nuevamente
        model.addAttribute("tours", tourService.getAll());
        model.addAttribute("transportations", transportationService.getAll());
        model.addAttribute("dishes", disheService.getAll());
        model.addAttribute("cabins", cabinService.getAll());
        
        return "/booking/editBooking";
    }
    
    try {
          Booking existingBooking = bookingService.getReservationById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        
        // Actualizar los campos básicos
        existingBooking.setCheckInDate(booking.getCheckInDate());
        existingBooking.setCheckOutDate(booking.getCheckOutDate());
        existingBooking.setNumberGuests(booking.getNumberGuests());
        existingBooking.setBookingType(booking.getBookingType());
        existingBooking.setReserveStatus(booking.getReserveStatus());
        existingBooking.setSpecialRequirements(booking.getSpecialRequirements());
        existingBooking.setPromotionCode(booking.getPromotionCode());
        
        
        
        // Actualizar relaciones
        existingBooking.setCabin(cabin);
        
        // Servicios adicionales

        boolean hasAdditionalServices = services != null && !services.isEmpty();
        existingBooking.setAdditionalServices(hasAdditionalServices);
        
        if (hasAdditionalServices) {
            // Tour
            if (services.contains("tour") && tourId != null) {
                Tour tour = tourService.findById(tourId).orElse(null);
                existingBooking.setTour(tour);
            } else {
                existingBooking.setTour(null);
            }
            
            // Transporte
            if (services.contains("transporte") && transportationId != null) {
                Transportation transportation = transportationService.findById(transportationId).orElse(null);
                existingBooking.setTransportation(transportation);
            } else {
                existingBooking.setTransportation(null);
            }
            
            // Platillo
            if (services.contains("alimentacion") && disheId != null) {
                Dishe dishe = disheService.findById(disheId).orElse(null);
                existingBooking.setDishe(dishe);
            } else {
                existingBooking.setDishe(null);
            }
        } else {
            existingBooking.setTour(null);
            existingBooking.setTransportation(null);
            existingBooking.setDishe(null);
        }
        
        bookingService.save(existingBooking);
        redirectAttributes.addFlashAttribute("success", "Reserva actualizada exitosamente");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al actualizar la reserva: " + e.getMessage());
        return "redirect:/booking/edit/" + id;
    }
    
    return "redirect:/booking/listaReservas";
}
//----------------------------ELIMINAR-----------------------------------------------------------------------------------------------
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteReservation(id);
            redirectAttributes.addFlashAttribute("success", "Booking deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting booking: " + e.getMessage());
        }
        return "redirect:/booking/listaReservas";
    }
//-----------------------DETALLES---------------------------------------------------------------------------------------------------- 
    @GetMapping("/details/{id}")
public String showBookingDetails(@PathVariable int id, Model model) {
    try {
        // Obtener la reserva con todos los datos relacionados
        Booking booking = bookingService.getReservationWithDetails(id)
            .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada"));
        

        double totalPaid=34000.34;
        double totalPrice = 300000;
        
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
    
    return "/booking/detailsBooking";
}
}

