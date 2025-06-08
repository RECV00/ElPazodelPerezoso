/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Cabin;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface CabinRepository extends JpaRepository<Cabin, Integer>,JpaSpecificationExecutor<Cabin>{
    
    Page<Cabin> findByNameContainingIgnoreCase(String name,Pageable pageable);
   
    List<Cabin> findByNameContainingIgnoreCase(String name);
    
   Page<Cabin> findByLocationContainingIgnoreCase(String location,Pageable pageable);
   
    List<Cabin> findByLocationContainingIgnoreCase(String location);
    
    List<Cabin> findByCapacityGreaterThanEqual(int capacity);
    
    List<Cabin> findByPricePerNightBetween(double minPrice, double maxPrice);
}
