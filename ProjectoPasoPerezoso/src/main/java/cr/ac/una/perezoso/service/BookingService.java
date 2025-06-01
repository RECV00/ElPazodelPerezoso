/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

//import cr.ac.una.perezoso.domain.Admin;
//import cr.ac.una.perezoso.domain.Booking;
//import cr.ac.una.perezoso.domain.Client;
//import cr.ac.una.perezoso.domain.Employee;
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
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                        UserRepository userRepository,
                        CabinService cabinService,
                        TourService tourService,
                        TransportationService transportationService,
                        FoodService foodService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.cabinService = cabinService;
        this.tourService = tourService;
        this.transportationService = transportationService;
        this.foodService = foodService;
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
        // Buscar cliente por cédula
        Optional<Client> clientOpt = userRepository.findClientsByIdentification(clientIdentification);
        if (clientOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con cédula: " + clientIdentification);
        }
        
        booking.setClient(clientOpt.get());
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateReservation(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteReservation(int id) {
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
        return bookingRepository.findByReservationStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Booking> searchReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookingRepository.findByDateRange(startDate, endDate);
    }


    @Transactional(readOnly = true)
    public Map<String, String> getRelatedNames(Booking booking) {
        // Implementación para obtener nombres relacionados usando los servicios
        // ...
        return null;
    }

    // Métodos específicos para empleados/administradores
    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByEmployee(String employeeIdentification, Pageable pageable) {
        Optional<Employee> employee = userRepository.findEmployeeByIdentification(employeeIdentification);
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        // Lógica para obtener reservaciones manejadas por este empleado
        // ...
        return bookingRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Booking> getReservationsByAdmin(String adminIdentification, Pageable pageable) {
        Optional<Admin> admin = userRepository.findAdminByIdentification(adminIdentification);
        if (admin.isEmpty()) {
            throw new IllegalArgumentException("Administrador no encontrado");
        }
        // Lógica para obtener todas las reservaciones (acceso completo)
        return bookingRepository.findAll(pageable);
    }
}
