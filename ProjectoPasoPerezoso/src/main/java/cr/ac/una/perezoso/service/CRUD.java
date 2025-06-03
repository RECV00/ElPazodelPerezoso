
package cr.ac.una.perezoso.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author keyna
 */
public interface CRUD<T, ID> {//clase generica
    public void save(T t);
    public void delete(ID id);
    public List<T> getAll();
    public T getById(ID id);
    
    // Nuevos métodos para paginación
    Page<T> getAll(Pageable pageable);
    boolean existsById(ID id);
    
}