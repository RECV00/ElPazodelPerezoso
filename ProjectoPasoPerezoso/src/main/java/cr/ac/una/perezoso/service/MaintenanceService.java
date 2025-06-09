
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Maintenance;
import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.jpa.MaintenanceRepository;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *
 * @author dayan
 */
@Service
public class MaintenanceService implements CRUD<Maintenance, Integer> {
  @Autowired  
  private MaintenanceRepository mr;

    @Override
    public void save(Maintenance t) {
       mr.save(t);
    }
    @Override
    public void delete(Integer i) {
        mr.deleteById(i);
    }
    @Override
    public List<Maintenance> getAll() {
         return mr.findAll();
    }
    public Maintenance getById(int id) {
    return mr.findById(id)
             .orElseThrow(() -> new RuntimeException("No se encontró el mantenimiento con id: " + id));
    }
    public void update(Maintenance maintenance) {
            mr.save(maintenance);  
    }
   public List<Maintenance> findByType(String type) {
        return mr.findByMaintenanceTypeContainingIgnoreCase(type);
    }
    public List<String> getAllTypes() {
        return mr.findAll()
                .stream()
                .map(Maintenance::getMaintenanceType)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<Maintenance> findByState(String state) {
    return mr.findByStateIgnoreCase(state);
    }
    public Optional<Maintenance> findById(int id) {
    return mr.findById(id);
    }
    public List<String> getAllStates() {
        return mr.findAll()
                .stream()
                .map(Maintenance::getState)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
    @Override
    public boolean existsById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public Page<Maintenance> getAll(org.springframework.data.domain.Pageable pageable) {
        return mr.findAll(pageable);
    }
    @Override
    public Maintenance getById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
  

