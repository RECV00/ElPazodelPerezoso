/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Food;
import cr.ac.una.perezoso.jpa.FoodRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author corra
 */
@Service
public class FoodService {

     private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    // Guardar o actualizar un registro de comida
    public Food saveOrUpdateFood(Food food) {
        return foodRepository.save(food);
    }

    // Obtener todos los registros de comida (paginados)
    public Page<Food> getAllFoods(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    // Obtener un registro por ID
    public Optional<Food> getFoodById(int id) {
        return foodRepository.findById(id);
    }

    // Eliminar un registro por ID
    public void deleteFood(int id) {
        foodRepository.deleteById(id);
    }

    // Búsqueda por menú seleccionado
    public Page<Food> searchBySelectedMenu(String menu, Pageable pageable) {
        return foodRepository.findBySelectedMenuContainingIgnoreCase(menu, pageable);
    }

    // Búsqueda por tipo de servicio
    public Page<Food> searchByTypeService(String typeService, Pageable pageable) {
        return foodRepository.findByTypeServiceContainingIgnoreCase(typeService, pageable);
    }

    // Búsqueda por fecha
    public Page<Food> searchByDate(LocalDate date, Pageable pageable) {
        return foodRepository.findByDateService(date, pageable);
    }

    // Búsqueda por rango de fechas
    public List<Food> searchByDateRange(LocalDate startDate, LocalDate endDate) {
        return foodRepository.findByDateRange(startDate, endDate);
    }

    // Búsqueda por número mínimo de platos
    public List<Food> searchByMinDishes(int minDishes) {
        return foodRepository.findByNumberDishesGreaterThan(minDishes);
    }

    // Búsqueda por opciones personalizadas
    public Page<Food> searchByCustomOptions(String options, Pageable pageable) {
        return foodRepository.findByCustomOptionsContainingIgnoreCase(options, pageable);
    }
}
