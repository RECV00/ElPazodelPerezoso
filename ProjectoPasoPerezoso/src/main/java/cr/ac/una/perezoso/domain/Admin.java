/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author keyna
 */
@Entity
@Table(name = "tb_admin")
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "id_admin") // referencia a la PK de User
public class Admin extends User{
    
    public Admin(){    
        super();
    }
    
    public Admin(int adminId, String name, String lastName, String identification, LocalDate birthdate, 
                String email, int phone, String address, String password, 
                String profilePicture) {
        super(name, lastName, identification, birthdate, email, phone, address, 
              password, profilePicture);
         
    }
    public Admin(String name, String lastName, String identification, LocalDate birthdate,
                 String email, int phone, String address, String password, String profilePicture) {
        super(name, lastName, identification, birthdate, email, phone, address, password, profilePicture);
    }
}
