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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<Cabin> getAll(Pageable pageable) {
        return cabinRepository.findAll(pageable);
    }

    @Override
    public Cabin getById(Integer id) {
        Optional<Cabin> cabin = cabinRepository.findById(id);
        return cabin.orElse(null);
    }

    // Métodos adicionales específicos para Cabin
    
   public Page<Cabin> findByNameContaining(String name, Pageable pageable) {
    return cabinRepository.findByNameContainingIgnoreCase(name, pageable);
}
 public List<Cabin> findByNameContaining(String name) {
    return cabinRepository.findByNameContainingIgnoreCase(name);
}
    public Page<Cabin> findByLocation(String location, Pageable pageable) {
        return cabinRepository.findByLocationContainingIgnoreCase(location,pageable);
    }

    public List<Cabin> findByCapacityGreaterThanEqual(int capacity) {
        return cabinRepository.findByCapacityGreaterThanEqual(capacity);
    }

    public List<Cabin> findByPricePerNightBetween(double minPrice, double maxPrice) {
        return cabinRepository.findByPricePerNightBetween(minPrice, maxPrice);
    }

    
    @Override
    public boolean existsById(Integer id) {
        return cabinRepository.existsById(id);
    }
    
//    public Page<Cabin> findByFilters(String location, Integer minCapacity, 
//                               Double minPrice, Double maxPrice, Pageable pageable) {
//    Specification<Cabin> spec = Specification.where(null);
//    
//    if (location != null && !location.isEmpty()) {
//        spec = spec.and((root, query, cb) -> 
//            cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
//    }
//    
//    if (minCapacity != null) {
//        spec = spec.and((root, query, cb) -> 
//            cb.greaterThanOrEqualTo(root.get("capacity"), minCapacity));
//    }
//    
//    if (minPrice != null) {
//        spec = spec.and((root, query, cb) -> 
//            cb.greaterThanOrEqualTo(root.get("pricePerNight"), minPrice));
//    }
//    
//    if (maxPrice != null) {
//        spec = spec.and((root, query, cb) -> 
//            cb.lessThanOrEqualTo(root.get("pricePerNight"), maxPrice));
//    }
//    
//    return cabinRepository.findAll(spec, pageable);
//}
    
}
