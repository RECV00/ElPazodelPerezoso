
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.ProjectSlothsStep.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author corra
 */
public class Tour {
    
    private int id_Tour;
    private String nameTour;
    private String description;
    private double price;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime duration;
    private String startingPoint;
    private String multimedia; 

    public Tour() {
    } 

    public Tour(String nameTour, String description, double price, LocalDate date, LocalTime startTime, LocalTime duration, String startingPoint, String multimedia) {
        this.nameTour = nameTour;
        this.description = description;
        this.price = price;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.startingPoint = startingPoint;
        this.multimedia = multimedia;
    }

    public Tour(int id_Tour, String nameTour, String description, double price, LocalDate date, LocalTime startTime, LocalTime duration, String startingPoint, String multimedia) {
        this.id_Tour = id_Tour;
        this.nameTour = nameTour;
        this.description = description;
        this.price = price;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.startingPoint = startingPoint;
        this.multimedia = multimedia;
    }

    public int getId_Tour() {
        return id_Tour;
    }

    public void setId_Tour(int id_Tour) {
        this.id_Tour = id_Tour;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public String toString() {
        return "Tour{" + "id_Tour=" + id_Tour + ", nameTour=" + nameTour + ", description=" + description + ", price=" + price + ", date=" + date + ", startTime=" + startTime + ", duration=" + duration + ", startingPoint=" + startingPoint + ", multimedia=" + multimedia + '}';
    }
     
    
}