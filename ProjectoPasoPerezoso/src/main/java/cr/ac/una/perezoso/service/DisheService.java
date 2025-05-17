/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Dishe;
import cr.ac.una.perezoso.jpa.DisheRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
@Transactional
public class DisheService implements CRUD<Dishe, Integer>{
    
    private final DisheRepository disheRepository;

    @Autowired
    public DisheService(DisheRepository disheRepository) {
        this.disheRepository = disheRepository;
    }

    @Override
    public void save(Dishe dishe) {
        disheRepository.save(dishe);
    }

    @Override
    public void delete(Integer id) {
        disheRepository.deleteById(id);
    }

    @Override
    public List<Dishe> getAll() {
        return disheRepository.findAll();
    }

    @Override
    public Dishe getById(Integer id) {
        return disheRepository.findById(id).orElse(null);
    }
    
    // Métodos adicionales específicos para Dishe
    public List<Dishe> getByCategory(String category) {
        return disheRepository.findByCategory(category);
    }
    
    public List<Dishe> getAvailableDishes() {
        return disheRepository.findByAvailable(true);
    }
    
    public List<Dishe> searchByName(String name) {
        return disheRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Dishe> getByPriceRange(Double minPrice, Double maxPrice) {
        return disheRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
