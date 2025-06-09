/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.domain.Transportation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Integer>,JpaSpecificationExecutor<Transportation>{
     @Query("SELECT t FROM Transportation t WHERE " +
           "LOWER(t.plate) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
           "LOWER(t.driver) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Transportation> searchByPlateOrDriver(@Param("filter") String filter);
    Page<Transportation> findByPlateContaining(String plate, Pageable pageable);
    Page<Transportation> findByServiceStatus(String status,Pageable pageable);
    List<Transportation> findByDataTimeServiceBetween(LocalDateTime start, LocalDateTime end);
    Transportation findByPlate(String plate);
    Optional<Transportation> findById(Integer id);
}

