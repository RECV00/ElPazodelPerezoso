/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Client;
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
 * @author corra
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
    
     @Query("SELECT b FROM Booking b " +
           "LEFT JOIN FETCH b.client " +
           "LEFT JOIN FETCH b.cabin " +
           "LEFT JOIN FETCH b.dishe " +
           "LEFT JOIN FETCH b.transportation " +
           "LEFT JOIN FETCH b.tour " +
           "LEFT JOIN FETCH b.payment " +
           "WHERE b.id_booking = :id")
    Optional<Booking> findByIdWithDetails(@Param("id") int id);
    @Query("SELECT b FROM Booking b JOIN b.client c WHERE c.identification = :identification")
    Page<Booking> findByClientIdentification(@Param("identification") String identification, Pageable pageable);
    List<Booking> findByClient(Client client);
    List<Booking> findByReserveStatus(String status);
    @Query("SELECT b FROM Booking b WHERE b.client.id_user = :clientId")
    List<Booking> findByClientId(@Param("clientId") Integer clientId);
    @Query("SELECT b FROM Booking b WHERE b.client.id_user = :clientId")
    Page<Booking> findByClientId(@Param("clientId") Integer clientId, Pageable pageable);
    @Query("SELECT b FROM Booking b JOIN b.client c WHERE " +
           "LOWER(CONCAT(c.name, ' ', c.last_name)) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Booking> findByClientNameContaining(@Param("name") String name, Pageable pageable);
    Page<Booking> findByBookingTypeContainingIgnoreCase(String bookingType, Pageable pageable);
    @Query("SELECT b FROM Booking b WHERE CURRENT_DATE BETWEEN b.checkInDate AND b.checkOutDate")
    List<Booking> findActiveReservations();
    @Query("SELECT b FROM Booking b WHERE b.cabin.cabinID = :cabinId")
    List<Booking> findByCabinId(@Param("cabinId") Integer cabinId);
    @Query("SELECT b FROM Booking b WHERE b.cabin.cabinID = :cabinId")
    Page<Booking> findByCabinId(@Param("cabinId") Integer cabinId, Pageable pageable);
    @Query("SELECT b FROM Booking b WHERE b.tour.id_tour = :tourId")
    Page<Booking> findByTourId(@Param("tourId") Integer tourId, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.transportation.id_transportation = :vehicleId")
    Page<Booking> findByVehicleId(@Param("vehicleId") Integer vehicleId, Pageable pageable);
        @Query("SELECT b FROM Booking b WHERE b.dishe.disheID = :disheId")
        Page<Booking> findByDisheId(@Param("disheId") Integer disheId, Pageable pageable);
    Page<Booking> findByReserveStatus(String status, Pageable pageable);
    Page<Booking> findByClient_IdentificationContainingIgnoreCase(String identification, Pageable pageable);
    Page<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE " +
               "b.cabin.cabinID = :cabinId AND " +
               "b.reserveStatus <> 'Cancelada' AND " +  // Usando el valor exacto
               "((b.checkInDate < :checkOut AND b.checkOutDate > :checkIn))")
    List<Booking> findConflictingBookings(
        @Param("cabinId") Integer cabinId,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut);
}
