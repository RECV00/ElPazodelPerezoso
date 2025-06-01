/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author keyna
 */

@Entity
@Table(name = "tb_client")
@DiscriminatorValue("CLIENT")

@PrimaryKeyJoinColumn(name = "id_client")
public class Client extends User{
    
     // Relación con Booking
    @OneToMany(mappedBy = "client")
    private List<Booking> bookings;
    
    
    public Client() {
        super();
        this.setUserType("CLIENT");
    }
   
    public Client(String name, String last_name, String identification, LocalDate birthdate,
                  String email, int phone, String address, String password, String photo) {
        super(name, last_name, identification, birthdate, email, phone, address, password, photo);
        this.userType = "CLIENT";
    }


    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
