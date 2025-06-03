/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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