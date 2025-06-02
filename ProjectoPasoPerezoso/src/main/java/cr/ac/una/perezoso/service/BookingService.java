/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

//import cr.ac.una.perezoso.domain.Admin;
//import cr.ac.una.perezoso.domain.Booking;
//import cr.ac.una.perezoso.domain.Client;
//import cr.ac.una.perezoso.domain.Employee;
import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.jpa.BookingRepository;
import cr.ac.una.perezoso.jpa.UserRepository;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
/**
 *
 * @author keyna
 */
@Service
public class BookingService {
   
 private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CabinService cabinService;
    private final TourService tourService;
    private final TransportationService transportationService;
    private final FoodService foodService;
//    private final PaymentService paymentService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                        UserRepository userRepository,
                        CabinService cabinService,
                        TourService tourService,
                        TransportationService transportationService,
                        FoodService foodService){
    
//                        ,PaymentService paymentService
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.cabinService = cabinService;
        this.tourService = tourService;
        this.transportationService = transportationService;
        this.foodService = foodService;
//        this.paymentService = paymentService;
    }

    @Transactional(readOnly = true)
    public Page<Booking> getAllReservations(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Booking> getReservationById(int id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public Booking createReservation(Booking booking, String clientIdentification) {
        Optional<Client> clientOpt = userRepository.findClientsByIdentification(clientIdentification);
        if (clientOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con cédula: " + clientIdentification);
        }
        
        if (booking.getCabin() != null) {
            boolean isCabinAvailable = !bookingRepository.existsByCabinAndDateRange(
                booking.getCabin().getCabinID(),
                booking.getCheckInDate(),
                booking.getCheckOutDate());
            
            if (!isCabinAvailable) {
                throw new IllegalStateException("La cabaña no está disponible para las fechas seleccionadas");
            }
        }
        
        booking.setClient(clientOpt.get());
        if (booking.getReserveStatus() == null || booking.getReserveStatus().isEmpty()) {
            booking.setReserveStatus("Pendiente");
        }
        
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateReservation(int id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));
        
        if (bookingDetails.getCheckInDate() != null) {
            booking.setCheckInDate(bookingDetails.getCheckInDate());
        }
        if (bookingDetails.getCheckOutDate() != null) {
            booking.setCheckOutDate(bookingDetails.getCheckOutDate());
        }
//        if (bookingDetails.getNumberGuests() != null) {
//            booking.setNumberGuests(bookingDetails.getNumberGuests());
//        }
        if (bookingDetails.getBookingType() != null) {
            booking.setBookingType(bookingDetails.getBookingType());
        }
        if (bookingDetails.getReserveStatus() != null) {
            booking.setReserveStatus(bookingDetails.getReserveStatus());
        }
        if (bookingDetails.getSpecialRequirements() != null) {
            booking.setSpecialRequirements(bookingDetails.getSpecialRequirements());
        }
        if (bookingDetails.getPromotionCode() != null) {
            booking.setPromotionCode(bookingDetails.getPromotionCode());
        }
        
        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteReservation(int id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Reserva no encontrada con ID: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Booking> getReservationsByClientId(int clientId) {
        return bookingRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public List<Booking> getReservationsByClientIdentification(String identification) {
        Optional<Client> client = userRepository.findClientsByIdentification(identification);
        if (client.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        return bookingRepository.findByClientId(client.get().getId_user());
    }

    @Transactional(readOnly = true)
    public List<Booking> searchReservationsByStatus(String status) {
        return bookingRepository.findByReserveStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Booking> searchReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        return bookingRepository.findByDateRange(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Page<Booking> searchByClientName(String name, Pageable pageable) {
        return bookingRepository.findByClientNameContaining(name, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Booking> searchByBookingType(String bookingType, Pageable pageable) {
        return bookingRepository.findByBookingTypeContainingIgnoreCase(bookingType, pageable);
    }
    
//    @Transactional(readOnly = true)
//    public List<Booking> getUpcomingReservations() {
//        return bookingRepository.findUpcomingReservations();
//    }
    
    @Transactional(readOnly = true)
    public List<Booking> getActiveReservations() {
        return bookingRepository.findActiveReservations();
    }
    
    @Transactional(readOnly = true)
    public List<Booking> getReservationsByCabinId(int cabinId) {
        return bookingRepository.findByCabinId(cabinId);
    }
    
//    @Transactional(readOnly = true)
//    public List<Booking> getReservationsByTourId(int tourId) {
//        return bookingRepository.findByTourId(tourId);
//    }
//    
//    @Transactional(readOnly = true)
//    public List<Booking> getReservationsByTransportationId(int transportationId) {
//        return bookingRepository.findByTransportationId(transportationId);
//    }
    
    @Transactional(readOnly = true)
    public List<Booking> getReservationsByPaymentStatus(String status) {
        return bookingRepository.findByPaymentStatus(status);
    }
    
    @Transactional(readOnly = true)
    public boolean checkCabinAvailability(int cabinId, LocalDate startDate, LocalDate endDate) {
        return !bookingRepository.existsByCabinAndDateRange(cabinId, startDate, endDate);
    }
    
    @Transactional
    public Booking cancelReservation(int id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));
        
        if (!"Pendiente".equals(booking.getReserveStatus())) {
            throw new IllegalStateException("Solo se pueden cancelar reservaciones con estado 'Pendiente'");
        }
        
        booking.setReserveStatus("Cancelada");
        return bookingRepository.save(booking);
    }
    
    @Transactional
    public Booking confirmReservation(int id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));
        
        if (!"Pendiente".equals(booking.getReserveStatus())) {
            throw new IllegalStateException("Solo se pueden confirmar reservaciones con estado 'Pendiente'");
        }
        
        booking.setReserveStatus("Confirmada");
        return bookingRepository.save(booking);
    }
    
     @Transactional(readOnly = true)
    public Page<Booking> getReservationsByCabinId(int cabinId, Pageable pageable) {
        return bookingRepository.findByCabinId(cabinId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByTourId(int tourId, Pageable pageable) {
        return bookingRepository.findByTourId(tourId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByVehicleId(int vehicleId, Pageable pageable) {
        return bookingRepository.findByVehicleId(vehicleId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByFoodId(int foodId, Pageable pageable) {
        return bookingRepository.findByFoodId(foodId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByClientId(int clientId, Pageable pageable) {
        return bookingRepository.findByClientId(clientId, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByStatus(String status, Pageable pageable) {
        return bookingRepository.findByReserveStatus(status, pageable);
    }
}
