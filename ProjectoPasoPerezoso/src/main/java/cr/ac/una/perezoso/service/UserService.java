/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Admin;
import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.Employee;
import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.jpa.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;



/**
 *
 * @author keyna
 */
@Service
public class UserService implements CRUD<User, Integer>{
 
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EntityManager em;
    @Override
    public void save(User user) {
        // Codifica la contraseña antes de guardar (si es nueva o actualizada)
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        repoUser.save(user);
    }
    
    @Override
    public void delete(Integer id) {
    repoUser.deleteById(id);    
    }

    @Override
    public User getById(Integer id) {
    return repoUser.findById(id).orElse(null);    
    }
    
     @Override
    public List<User> getAll() {
        return repoUser.findAll(); // Devuelve directamente la List (puede ser ArrayList)
    }
 
    @Override
    public Page<User> getAll(Pageable pageable) {
        return repoUser.findAll(pageable); // Devuelve directamente la List (puede ser ArrayList)
    }
    
    // Métodos específicos para User
    
   public User findByIdentification(String identification) {
        return repoUser.findByIdentificationIgnoreCase(identification)
            .orElse(null);
    }
    
    public void saveUserWithHashedPassword(User user) {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        repoUser.save(user);
    }
    
     // Métodos para tipos específicos
    public Admin saveAdmin(Admin admin) {
        admin.setUserType("ADMIN");
        return repoUser.save(admin);
    }
    
    public Employee saveEmployee(Employee employee) {
        employee.setUserType("EMPLOYEE");
        return repoUser.save(employee);
    }
    
    public Client saveClient(Client client) {
        client.setUserType("CLIENT");
        return repoUser.save(client);
    }
    
    public List<Admin> getAllAdmins() {
        return repoUser.findAllAdmins();
    }
    
    public List<Employee> getAllEmployees() {
        return repoUser.findAllEmployees();
    }
    
    public List<Client> getAllClients() {
        return repoUser.findAllClients();
    }

    public long getUserCount() {
        return repoUser.count();
    }

    // Versión para contar por tipo de usuario
    public long getUserCountByType(String userType) {
        return repoUser.countByUserType(userType);
    }
    
    public Client getClientByIdentification(String identification) {
        return repoUser.findByIdentification(identification)
            .map(user -> {
                if (user instanceof Client) {
                    return (Client) user;
                }
                throw new RuntimeException("El usuario encontrado no es un cliente");
            })
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    
    public List<Booking> getClientBookings(Integer clientId) {
    TypedQuery<Booking> query = em.createQuery(
        "SELECT b FROM Booking b WHERE b.client.id = :id_client ORDER BY b.checkInDate DESC", 
        Booking.class);
    query.setParameter("id_client", clientId);
    return query.getResultList();
}
    
  // Método para paginación y filtrado
    public Page<User> filterAndPaginateUsers(String identification, String userType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        if (identification != null && !identification.isEmpty() && 
            userType != null && !userType.isEmpty()) {
            return repoUser.findByIdentificationContainingIgnoreCaseAndUserType(identification, userType, pageable);
        } else if (identification != null && !identification.isEmpty()) {
            return repoUser.findByIdentificationContainingIgnoreCase(identification, pageable);
        } else if (userType != null && !userType.isEmpty()) {
            return repoUser.findByUserType(userType, pageable);
        }
        return repoUser.findAll(pageable);
    }
    
   @Override
    public boolean existsById(Integer id) {
        return repoUser.existsById(id);
    }
    
    
}
