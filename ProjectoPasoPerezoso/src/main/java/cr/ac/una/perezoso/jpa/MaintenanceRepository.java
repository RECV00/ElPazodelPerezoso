
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Maintenance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 *
 * @author dayan
 */
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer>,JpaSpecificationExecutor<Maintenance>{
    List<Maintenance> findByMaintenanceTypeContainingIgnoreCase(String type);
    List<Maintenance> findByStateIgnoreCase(String state);
}