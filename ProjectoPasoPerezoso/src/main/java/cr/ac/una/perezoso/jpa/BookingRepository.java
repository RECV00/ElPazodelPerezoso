/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Booking;
import java.time.LocalDate;
import java.util.List;
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
    // Buscar reservaciones por estado
    List<Booking> findByReservationStatus(String status);

    // Buscar reservaciones por rango de fechas
   @Query("SELECT b FROM Booking b WHERE b.checkInDate BETWEEN :startDate AND :endDate OR b.checkOutDate BETWEEN :startDate AND :endDate")
    List<Booking> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Buscar reservaciones por cliente (ID)
    @Query("SELECT b FROM Booking b WHERE b.client.id = :clientId")
    List<Booking> findByClientId(@Param("clientId") Integer clientId);
    
    // Versión paginada de búsqueda por estado
    Page<Booking> findByReservationStatus(String status, Pageable pageable);
    
    // Versión paginada de búsqueda por cliente
   @Query("SELECT b FROM Booking b WHERE b.client.id = :clientId")
   Page<Booking> findByClientId(@Param("clientId") Integer clientId, Pageable pageable);
  
}
