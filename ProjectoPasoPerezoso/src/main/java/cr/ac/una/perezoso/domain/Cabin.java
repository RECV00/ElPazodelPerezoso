/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

/**
 *
 * @author keyna
 */
public class Cabin {
    
    private int cabinID;//para la base de datos 
    
    private String name;
    private String description;
    private int capacity;
    private double pricePerNight;
    private String location;
    private String image;
    private String includedServices;

    public Cabin() {
    }

    public Cabin(int cabinID, String name, String description, int capacity, double pricePerNight, String location, String image, String includedServices) {
        this.cabinID = cabinID;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.location = location;
        this.image = image;
        this.includedServices = includedServices;
    }

    public Cabin(String name, String description, int capacity, double pricePerNight, String location, String image, String includedServices) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.location = location;
        this.image = image;
        this.includedServices = includedServices;
    }

    
    public int getCabinID() {
        return cabinID;
    }

    public void setCabinID(int cabinID) {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
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
