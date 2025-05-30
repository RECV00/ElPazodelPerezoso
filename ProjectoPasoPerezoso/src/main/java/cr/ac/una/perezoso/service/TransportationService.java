/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Transportation;
import cr.ac.una.perezoso.jpa.TransportationRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
public class TransportationService implements CRUD<Transportation, Integer>{
    private final TransportationRepository transportationRepository;

    @Autowired
    public TransportationService(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    @Override
    public void save(Transportation transportation) {
        transportationRepository.save(transportation);
    }

    @Override
    public void delete(Integer id) {
        transportationRepository.deleteById(id);
    }

    @Override
    public List<Transportation> getAll() {
        return transportationRepository.findAll();
    }

    @Override
    public Transportation getById(Integer id) {
        return transportationRepository.findById(id).orElse(null);
    }

    public Transportation getByPlate(String plate) {
        return transportationRepository.findByPlate(plate);
    }

    public List<Transportation> searchByPlateOrDriver(String filter) {
        return transportationRepository.searchByPlateOrDriver(filter);
    }

    public List<Transportation> findByStatus(String status) {
        return transportationRepository.findByServiceStatus(status);
    }

    public List<Transportation> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return transportationRepository.findByDataTimeServiceBetween(start, end);
    }
    
    @Override
    public Page<Transportation> getAll(Pageable pageable) {
        return transportationRepository.findAll(pageable);
    }

//     public Page<Transportation> findByNameContaining(String name, Pageable pageable) {
//    return transportationRepository.findByNameContainingIgnoreCase(name, pageable);
//}
//     
     @Override
    public boolean existsById(Integer id) {
        return transportationRepository.existsById(id);
    }
}
