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
import java.time.LocalDateTime;

/**
 *
 * @author natal
 */
@Entity
@Table(name = "tb_vehicle")
public class Transportation {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicle")
    private Integer id_transportation;
    
    @Column(name = "plate", nullable = false, length = 50, unique = true)
    private String plate;
    
    @Column(name = "driver", nullable = false, length = 100)
    private String driver;
    
    @Column(name = "date_time_service", nullable = false)
    private LocalDateTime dataTimeService;
    
    @Column(name = "initial_location", nullable = false, length = 200)
    private String initialLocation;
    
    @Column(name = "final_location", nullable = false, length = 200)
    private String finalLocation;
    
    @Column(name = "service_status", nullable = false, length = 50)
    private String serviceStatus;
    
    @Column(name = "service_duration", nullable = false)
    private Integer serviceDuration;

  
   // Constructores
    public Transportation() {
    }

    public Transportation(Integer id_transportation, String plate, String driver, LocalDateTime dataTimeService, 
                        String initialLocation, String finalLocation, 
                        String serviceStatus, int serviceDuration) {
        this.id_transportation = id_transportation;
        this.plate = plate;
        this.driver = driver;
        this.dataTimeService = dataTimeService;
        this.initialLocation = initialLocation;
        this.finalLocation = finalLocation;
        this.serviceStatus = serviceStatus;
        this.serviceDuration = serviceDuration;
    }

    public Transportation(String plate, String driver, LocalDateTime dataTimeService, 
                        String initialLocation, String finalLocation, 
                        String serviceStatus, int serviceDuration) {
        this.plate = plate;
        this.driver = driver;
        this.dataTimeService = dataTimeService;
        this.initialLocation = initialLocation;
        this.finalLocation = finalLocation;
        this.serviceStatus = serviceStatus;
        this.serviceDuration = serviceDuration;
    }

    // Getters y Setters
    public Integer getId_transportation() {
        return id_transportation;
    }

    public void setId_transportation(Integer id_transportation) {
        this.id_transportation = id_transportation;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public LocalDateTime getDataTimeService() {
        return dataTimeService;
    }

    public void setDataTimeService(LocalDateTime dataTimeService) {
        this.dataTimeService = dataTimeService;
    }

    public String getInitialLocation() {
        return initialLocation;
    }

    public void setInitialLocation(String initialLocation) {
        this.initialLocation = initialLocation;
    }

    public String getFinalLocation() {
        return finalLocation;
    }

    public void setFinalLocation(String finalLocation) {
        this.finalLocation = finalLocation;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(int serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    @Override
   public String toString() {
       return "Transportation{" +
              "id=" + id_transportation +
              ", plate=" + plate +
              ", driver=" + driver +
              ", dataTimeService=" + dataTimeService +
              ", initialLocation=" + initialLocation +
              ", finalLocation=" + finalLocation +
              ", serviceStatus=" + serviceStatus +
              ", serviceDuration=" + serviceDuration +
              '}';
   }

}
