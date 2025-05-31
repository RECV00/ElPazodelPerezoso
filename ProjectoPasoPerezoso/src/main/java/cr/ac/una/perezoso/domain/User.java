/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author keyna
 */

@Entity
@Table(name = "tb_user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_user") 
    protected int id_user;
    
     @Column(name = "name", nullable = false)
    protected String name;
    
    @Column(name = "last_name", nullable = false)
    protected String last_name;
    
    @Column(name = "identification", unique = true, nullable = false)
    protected String identification;
    
    @Column(name = "birthdate")
    protected LocalDate birthdate;
    
    @Column(name = "email", unique = true)
    protected String email;
    
    @Column(name = "phone")
    protected int phone;
    
    @Column(name = "address")
    protected String address;
    
    @Column(name = "password", nullable = false)
    protected String password;
    
    @Column(name = "profile_picture")
    protected String profile_picture;
    
    @Column(name = "user_type", nullable = false,insertable = false, updatable = false)
    protected String userType;
    
    public User() {
    }

     public User(int id_user, String name, String last_name, String identification, LocalDate birthdate,
               String email, int phone, String address, String password, 
               String profilePicture ) {
        this.id_user = id_user;
        this.name = name;
        this.last_name = last_name;
        this.identification = identification;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.profile_picture  = profile_picture ;
        this.birthdate = birthdate;
    }

   public User(String name, String last_name, String identification, LocalDate birthdate,
                String email, int phone, String address, String password, String profile_picture) {
        this.name = name;
        this.last_name = last_name;
        this.identification = identification;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.profile_picture = profile_picture;
    }
    
    // Getters y Setters
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_picture () {
        return profile_picture ;
    }

    public void setProfile_picture (String profile_picture ) {
        this.profile_picture  = profile_picture ;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    
   public String getUserType() { 
       return userType; 
   }
   public void setUserType(String userType) { 
       this.userType = userType; }
}
