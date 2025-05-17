/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Dishe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface DisheRepository extends JpaRepository<Dishe, Integer>{
    
    List<Dishe> findByCategory(String category);
    
    List<Dishe> findByAvailable(Boolean available);
    
    List<Dishe> findByNameContainingIgnoreCase(String name);
    
    List<Dishe> findByPriceBetween(Double minPrice, Double maxPrice);
}
