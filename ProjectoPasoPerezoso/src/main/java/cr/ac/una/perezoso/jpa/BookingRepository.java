/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author keyna
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    
     @Query("SELECT b FROM Booking b " +
           "LEFT JOIN FETCH b.client " +
           "LEFT JOIN FETCH b.cabin " +
           "LEFT JOIN FETCH b.food " +
           "LEFT JOIN FETCH b.transportation " +
           "LEFT JOIN FETCH b.tour " +
           "LEFT JOIN FETCH b.payment " +
           "WHERE b.id_booking = :id")
    Optional<Booking> findByIdWithDetails(@Param("id") int id);
    
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.food WHERE b.id = :id")
    Booking findByIdWithFood(@Param("id") Long id);
    @Query("SELECT b FROM Booking b JOIN b.client c WHERE c.identification = :identification")
    Page<Booking> findByClientIdentification(@Param("identification") String identification, Pageable pageable);
      Page<Booking> findByClient_IdentificationContainingIgnoreCase(String identification, Pageable pageable);
    
    // Buscar por estado (búsqueda parcial insensible a mayúsculas)
    Page<Booking> findByReserveStatusContainingIgnoreCase(String status, Pageable pageable);
    
    // Buscar por rango de fechas
    Page<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    // Buscar por estado
    Page<Booking> findByReserveStatus(String status, Pageable pageable);
    
    List<Booking> findByReserveStatus(String status);
    // Find by date range
    @Query("SELECT b FROM Booking b WHERE " +
           "(b.checkInDate BETWEEN :startDate AND :endDate) OR " +
           "(b.checkOutDate BETWEEN :startDate AND :endDate) OR " +
           "(b.checkInDate <= :startDate AND b.checkOutDate >= :endDate)")
    List<Booking> findByDateRange(@Param("startDate") LocalDate startDate, 
                                 @Param("endDate") LocalDate endDate);
    
    // Find by client ID
    @Query("SELECT b FROM Booking b WHERE b.client.id_user = :clientId")
    List<Booking> findByClientId(@Param("clientId") Integer clientId);
    
    @Query("SELECT b FROM Booking b WHERE b.client.id_user = :clientId")
    Page<Booking> findByClientId(@Param("clientId") Integer clientId, Pageable pageable);
    
    // Find by client name (contains, case insensitive)
    @Query("SELECT b FROM Booking b JOIN b.client c WHERE " +
           "LOWER(CONCAT(c.name, ' ', c.last_name)) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Booking> findByClientNameContaining(@Param("name") String name, Pageable pageable);
    
    // Find by booking type (contains, case insensitive)
    Page<Booking> findByBookingTypeContainingIgnoreCase(String bookingType, Pageable pageable);
    
//    // Find upcoming reservations
//    @Query("SELECT b FROM Booking b WHERE b.checkInDate >= CURRENT_DATE ORDER BY b.checkInDate ASC")
//    List<Booking> findUpcomingReservations();
//    
    // Find active reservations (current date between check-in and check-out)
    @Query("SELECT b FROM Booking b WHERE CURRENT_DATE BETWEEN b.checkInDate AND b.checkOutDate")
    List<Booking> findActiveReservations();
    
    // Find by cabin ID
    @Query("SELECT b FROM Booking b WHERE b.cabin.cabinID = :cabinId")
    List<Booking> findByCabinId(@Param("cabinId") Integer cabinId);
    
//    // Find by tour ID
//    @Query("SELECT b FROM Booking b WHERE b.tour.id_tour = :tourId")
//    List<Booking> findByTourId(@Param("tourId") Integer tourId);
//    
//    // Find by transportation ID
//    @Query("SELECT b FROM Booking b WHERE b.transportation.id_vehicle = :id_transportation")
//    List<Booking> findByTransportationId(@Param("id_transportation") Integer transportationId);
//    
    // Find by payment status
    @Query("SELECT b FROM Booking b JOIN b.payment p WHERE p.statePayment = :status")
    List<Booking> findByPaymentStatus(@Param("status") String status);
    
    // Check availability for a cabin in a date range
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE " +
           "b.cabin.cabinID = :cabinId AND " +
           "((b.checkInDate BETWEEN :startDate AND :endDate) OR " +
           "(b.checkOutDate BETWEEN :startDate AND :endDate) OR " +
           "(b.checkInDate <= :startDate AND b.checkOutDate >= :endDate))")
    boolean existsByCabinAndDateRange(@Param("cabinId") Integer cabinId,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

 // Nuevos métodos para búsqueda por entidades relacionadas
    @Query("SELECT b FROM Booking b WHERE b.cabin.cabinID = :cabinId")
    Page<Booking> findByCabinId(@Param("cabinId") Integer cabinId, Pageable pageable);
    
   // Descomentar y actualizar estos métodos en BookingRepository
@Query("SELECT b FROM Booking b WHERE b.tour.id_tour = :tourId")
Page<Booking> findByTourId(@Param("tourId") Integer tourId, Pageable pageable);

@Query("SELECT b FROM Booking b WHERE b.transportation.id_transportation = :vehicleId")
Page<Booking> findByVehicleId(@Param("vehicleId") Integer vehicleId, Pageable pageable);
    @Query("SELECT b FROM Booking b WHERE b.food.id_food = :foodId")
    Page<Booking> findByFoodId(@Param("foodId") Integer foodId, Pageable pageable);
    
   

}
