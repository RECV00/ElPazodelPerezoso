/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author keyna
 */
@Entity
@Table(name = "tb_cabin")
public class Cabin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinID") 
    private Integer cabinID;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
   
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
   
    @Column(name = "price_per_night", nullable = false)
     private Double pricePerNight;
  
    @Column(name = "location", nullable = false, length = 200)
    private String location;
    
    @Column(name = "image", length = 255)
    private String image;
    
    @Column(name = "included_services", length = 500)
    private String includedServices;

    public Cabin() {
    }

    public Cabin(Integer cabinID, String name, String description, Integer capacity, 
               double pricePerNight, String location, String image, 
               String includedServices) {
        this.cabinID = cabinID;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.location = location;
        this.image = image;
        this.includedServices = includedServices;
    }

    public Cabin(String name, String description, Integer capacity, 
                double pricePerNight, String location, String image, 
                String includedServices) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.location = location;
        this.image = image;
        this.includedServices = includedServices;
    }

    // Getters y Setters
    public Integer getCabinID() {
        return cabinID;
    }

    public void setCabinID(Integer cabinID) {
        this.cabinID = cabinID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIncludedServices() {
        return includedServices;
    }

    public void setIncludedServices(String includedServices) {
        this.includedServices = includedServices;
    }

   
}