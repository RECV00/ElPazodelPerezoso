/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Cabin;
import cr.ac.una.perezoso.jpa.CabinRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
public class CabinService implements CRUD<Cabin, Integer>{
    private final CabinRepository cabinRepository;

    @Autowired
    public CabinService(CabinRepository cabinRepository) {
        this.cabinRepository = cabinRepository;
    }

    @Override
    public void save(Cabin cabin) {
        cabinRepository.save(cabin);
    }

    @Override
    public void delete(Integer id) {
        cabinRepository.deleteById(id);
    }

    @Override
    public List<Cabin> getAll() {
        return cabinRepository.findAll();
    }

    @Override
    public Cabin getById(Integer id) {
        Optional<Cabin> cabin = cabinRepository.findById(id);
        return cabin.orElse(null);
    }

    // Métodos adicionales específicos para Cabin
    
    public List<Cabin> findByNameContaining(String name) {
        return cabinRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Cabin> findByLocation(String location) {
        return cabinRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Cabin> findByCapacityGreaterThanEqual(int capacity) {
        return cabinRepository.findByCapacityGreaterThanEqual(capacity);
    }

    public List<Cabin> findByPricePerNightBetween(double minPrice, double maxPrice) {
        return cabinRepository.findByPricePerNightBetween(minPrice, maxPrice);
    }

    public boolean existsById(Integer id) {
        return cabinRepository.existsById(id);
    }
}
