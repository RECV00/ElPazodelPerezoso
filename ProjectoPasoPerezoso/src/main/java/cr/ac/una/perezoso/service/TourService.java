/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.jpa.TourRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
@Transactional
public class TourService implements CRUD<Tour, Integer>{
    
    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public void delete(Integer id) {
        tourRepository.deleteById(id);
    }

    @Override
    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    @Override
    public Tour getById(Integer id) {
        return tourRepository.findById(id).orElse(null);
    }

    // Métodos específicos adicionales para Tour
    public List<Tour> searchByName(String name) {
        return tourRepository.findByNameTourContainingIgnoreCase(name);
    }

    public List<Tour> getToursByDate(LocalDate date) {
        return tourRepository.findByDate(date);
    }

    public List<Tour> getToursByPriceRange(Double min, Double max) {
        return tourRepository.findByPriceBetween(min, max);
    }

    public List<Tour> searchByLocation(String location) {
        return tourRepository.findByStartingPointContainingIgnoreCase(location);
    }
    
     @Override
    public Page<Tour> getAll(Pageable pageable) {
        return tourRepository.findAll(pageable);
    }

     public Page<Tour> findByNameContaining(String name, Pageable pageable) {
    return tourRepository.findByNameTourContainingIgnoreCase(name, pageable);
}
     
    
     public Page<Tour> findByStartingPointContaining(String location, Pageable pageable) {
        return tourRepository.findByStartingPointContainingIgnoreCase(location, pageable);
    }
     
     
     @Override
    public boolean existsById(Integer id) {
        return tourRepository.existsById(id);
    }

    public Optional<Tour> findById(int tourId) {
    return tourRepository.findById(tourId);
    }
       
    
}
