/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import java.util.List;

/**
 *
 * @author keyna
 */
public interface CRUD<T, ID> {//clase generica
    public void save(T t);
    public void delete(ID id);
    public List<T> getAll();
    public T getById(ID id);
    
}
