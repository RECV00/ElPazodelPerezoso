/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Food;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author corra
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
   
      // Búsqueda por menú seleccionado (ignorando mayúsculas/minúsculas)
    Page<Food> findBySelectedMenuContainingIgnoreCase(String selectedMenu, Pageable pageable);
    
    // Búsqueda por tipo de servicio (ignorando mayúsculas/minúsculas)
    Page<Food> findByTypeServiceContainingIgnoreCase(String typeService, Pageable pageable);
    
    // Búsqueda por fecha de servicio
    Page<Food> findByDateService(LocalDate dateService, Pageable pageable);
    
    // Búsqueda por rango de fechas
    @Query("SELECT f FROM Food f WHERE f.dateService BETWEEN :startDate AND :endDate")
    List<Food> findByDateRange(@Param("startDate") LocalDate startDate, 
                             @Param("endDate") LocalDate endDate);
    
    // Búsqueda por número de platos (mayor que)
    List<Food> findByNumberDishesGreaterThan(int minDishes);
    
    // Búsqueda por opciones personalizadas
    Page<Food> findByCustomOptionsContainingIgnoreCase(String customOptions, Pageable pageable);
}
