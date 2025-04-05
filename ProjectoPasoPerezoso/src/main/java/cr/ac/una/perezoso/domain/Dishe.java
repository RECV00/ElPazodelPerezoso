/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import java.time.LocalTime;

/**
 *
 * @author keyna
 */
public class Dishe {
     private int disheID;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;
    private String imageUrl; 
    private LocalTime preparationTime; 

    public Dishe() {
    }

    public Dishe(int disheID, String name, String description, double price, String category, boolean available, String imageUrl, LocalTime preparationTime) {
        this.disheID = disheID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = available;
        this.imageUrl = imageUrl;
        this.preparationTime = preparationTime;
    }

    public Dishe(String name, String description, double price, String category, boolean available, String imageUrl, LocalTime preparationTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = available;
        this.imageUrl = imageUrl;
        this.preparationTime = preparationTime;
    }

    public int getDisheID() {
        return disheID;
    }

    public void setDisheID(int disheID) {
        this.disheID = disheID;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalTime getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(LocalTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    
}
