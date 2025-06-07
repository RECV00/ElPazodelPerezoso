/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Dishe;
import java.util.List;
import java.util.Optional;
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
public interface DisheRepository extends JpaRepository<Dishe, Integer>,JpaSpecificationExecutor<Dishe>{
    
    List<Dishe> findByCategory(String category);
    
    List<Dishe> findByAvailable(Boolean available);
    
    List<Dishe> findByNameContainingIgnoreCase(String name);
    
    Page<Dishe> findByNameContainingIgnoreCase(String name,Pageable pageable);
    
    List<Dishe> findByPriceBetween(Double minPrice, Double maxPrice);
    Optional<Dishe> findById(Integer id);
}
