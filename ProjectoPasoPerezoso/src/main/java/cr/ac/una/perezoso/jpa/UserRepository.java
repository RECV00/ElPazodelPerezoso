/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Admin;
import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.Employee;
import cr.ac.una.perezoso.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author keyna
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
     // Buscar usuario por cédula
    Optional<User> findByIdentification(String identification);
    // Nuevo método para búsqueda case-insensitive
    Optional<User> findByIdentificationIgnoreCase(String identification);
    // Listado de cada tipo de usuario usando herencia
    @Query("SELECT a FROM Admin a")
    List<Admin> findAllAdmins();

    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();

    @Query("SELECT c FROM Client c")
    List<Client> findAllClients();

    // Buscar por cédula en tipos específicos
    @Query("SELECT u FROM User u WHERE TYPE(u) = Admin AND u.identification = ?1")
    Optional<Admin> findAdminByIdentification(String identification);

    @Query("SELECT u FROM User u WHERE TYPE(u) = Employee AND u.identification = ?1")
    Optional<Employee> findEmployeeByIdentification(String identification);

    @Query("SELECT u FROM User u WHERE TYPE(u) = Client AND u.identification = ?1")
    Optional<Client> findClientsByIdentification(String identification);

    // Conteo de usuarios por tipo (usando el atributo userType)
    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = :userType")
    long countByUserType(@Param("user_type") String userType);

    
    // Búsquedas generales
    List<User> findByIdentificationContainingIgnoreCase(String identification);
    List<User> findByUserType(String userType);
    
    // Consultas de Booking 
    @Query("SELECT b FROM Booking b WHERE b.client.id = :id_client ORDER BY b.checkInDate DESC")
    List<Booking> findByClientId(@Param("id_client") Integer clientId);
    
    @Query("SELECT b FROM Booking b WHERE b.client.id = :clientId AND b.reserveStatus = :status")
    List<Booking> findByClientIdAndStatus(
        @Param("clientId") Integer clientId, 
        @Param("status") String status);
    
    // Versión nativa con límite
    @Query(value = "SELECT * FROM tb_booking WHERE id_client = :clientId ORDER BY checkInDate", 
           nativeQuery = true)
    List<Booking> findRecentBookingsByClient(@Param("clientId") Integer clientId);
    
    // Métodos para paginación
    Page<User> findByIdentificationContainingIgnoreCase(String identification, Pageable pageable);
    Page<User> findByUserType(String userType, Pageable pageable);
    Page<User> findByIdentificationContainingIgnoreCaseAndUserType(String identification, String userType, Pageable pageable);
    
    // Métodos para conteo con filtros combinados
    public long countByIdentificationContainingIgnoreCaseAndUserType(String identification, String userType);

}
