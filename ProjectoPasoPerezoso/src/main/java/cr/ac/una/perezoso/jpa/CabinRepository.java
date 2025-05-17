/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Cabin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface CabinRepository extends JpaRepository<Cabin, Integer>{
    List<Cabin> findByNameContainingIgnoreCase(String name);
    
    List<Cabin> findByLocationContainingIgnoreCase(String location);
    
    List<Cabin> findByCapacityGreaterThanEqual(int capacity);
    
    List<Cabin> findByPricePerNightBetween(double minPrice, double maxPrice);
}
