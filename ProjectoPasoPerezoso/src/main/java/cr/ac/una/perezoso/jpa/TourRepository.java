/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Tour;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Integer>{
     
    List<Tour> findByNameTourContainingIgnoreCase(String name);
    
    List<Tour> findByDate(LocalDate date);
    
    List<Tour> findByPriceBetween(Double minPrice, Double maxPrice);
    
    List<Tour> findByStartingPointContainingIgnoreCase(String location);
}
