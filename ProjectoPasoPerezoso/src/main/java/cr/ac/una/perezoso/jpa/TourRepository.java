/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Tour;
import java.time.LocalDate;
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
public interface TourRepository extends JpaRepository<Tour, Integer>,JpaSpecificationExecutor<Tour>{
     
    List<Tour> findByNameTourContainingIgnoreCase(String name);
    
    Page<Tour> findByNameTourContainingIgnoreCase(String name,Pageable pageable);
    
    List<Tour> findByDate(LocalDate date);
    
    List<Tour> findByPriceBetween(Double minPrice, Double maxPrice);
    
    List<Tour> findByStartingPointContainingIgnoreCase(String location);
    @Query("SELECT t FROM Tour t WHERE t.id IN :ids")
    List<Tour> findByIdIn(@Param("ids") List<Integer> ids);
//    public boolean existsById(int tourId);
    Optional<Tour> findById(int tourId);

    Page<Tour> findByStartingPointContainingIgnoreCase(String location, Pageable pageable);

}
