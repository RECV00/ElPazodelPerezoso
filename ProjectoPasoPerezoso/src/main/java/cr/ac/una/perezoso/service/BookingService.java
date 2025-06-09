/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.Dishe;
import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.domain.Transportation;
import cr.ac.una.perezoso.jpa.BookingRepository;
import cr.ac.una.perezoso.jpa.DisheRepository;
import cr.ac.una.perezoso.jpa.TourRepository;
import cr.ac.una.perezoso.jpa.TransportationRepository;
import cr.ac.una.perezoso.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author corra
 */
@Service
public class BookingService {
   
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;  
    private final DisheRepository disheRepository;  
    private final TransportationRepository transportationRepository; 
    
    @Autowired
    public BookingService(BookingRepository bookingRepository,
                        UserRepository userRepository,
                        TourRepository tourRepository,
                        DisheRepository disheRepository,
                        TransportationRepository transportationRepository){
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.disheRepository = disheRepository;
        this.transportationRepository = transportationRepository;
    }
    @Transactional
    public Optional<Booking> findById(Integer id) {
        return bookingRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public Client findClientByIdentification(String identification) {
        return userRepository.findClientsByIdentification(identification);
    }
     @Transactional
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
    @Transactional(readOnly = true)
    public List<Tour> findAllTours() {
        return tourRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Booking> findByClient(Client client) {
        return bookingRepository.findByClient(client);
    }
    @Transactional(readOnly = true)
    public List<Booking> findByReserveStatus(String status) {
        return bookingRepository.findByReserveStatus(status);
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
    public void deleteReservation(int id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Reserva no encontrada con ID: " + id);
        }
        bookingRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Page<Booking> searchByBookingType(String bookingType, Pageable pageable) {
        return bookingRepository.findByBookingTypeContainingIgnoreCase(bookingType, pageable);
    }
    @Transactional(readOnly = true)
    public List<Booking> getReservationsByCabinId(int cabinId) {
        return bookingRepository.findByCabinId(cabinId);
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
    public Page<Booking> getReservationsByDisheId(int disheId, Pageable pageable) {
        return bookingRepository.findByDisheId(disheId, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByClientId(int clientId, Pageable pageable) {
        return bookingRepository.findByClientId(clientId, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByStatus(String status, Pageable pageable) {
        return bookingRepository.findByReserveStatus(status, pageable);
    }
    public Optional<Booking> getReservationWithDetails(int id) {
    return bookingRepository.findByIdWithDetails(id);
    }
    public Optional<Tour> getTourById(int tourId) {
        return tourRepository.findById(tourId);

    }
    public Dishe getDisheById(Integer id) {
        return disheRepository.findById(id).orElse(null);
    }
    public Transportation getTransportationById(Integer id) {
        return transportationRepository.findById(id).orElse(null);
    }
    @Transactional(readOnly = true)
    public Page<Booking> filterByStatus(String status, Pageable pageable) {
        return bookingRepository.findByReserveStatus(status, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Booking> filterByIdentification(String identification, Pageable pageable) {
        return bookingRepository.findByClient_IdentificationContainingIgnoreCase(identification, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Booking> filterByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return bookingRepository.findByCheckInDateBetween(startDate, endDate, pageable);
    }
    public boolean isCabinAvailable(Integer cabinId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
            cabinId, checkIn, checkOut);
        return conflictingBookings.isEmpty();
    }

}
