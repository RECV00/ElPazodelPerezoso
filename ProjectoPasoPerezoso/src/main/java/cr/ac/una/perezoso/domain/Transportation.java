/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import java.time.LocalDateTime;

/**
 *
 * @author natal
 */
public class Transportation {
    private int id; 
    private String idVehicle;
    private String driver;
    private LocalDateTime dataTimeService;
    private String initialLocation;
    private String finalLocation;
    private String serviceStatus;
    private int serviceDuration;
  
    public Transportation() {
    }
    public Transportation(int id, String idVehicle, String driver, LocalDateTime dataTimeService, String initialLocation, String finalLocation, String serviceStatus, int serviceDuration) {
        this.id = id;
        this.idVehicle = idVehicle;
        this.driver = driver;
        this.dataTimeService = dataTimeService;
        this.initialLocation = initialLocation;
        this.finalLocation = finalLocation;
        this.serviceStatus = serviceStatus;
        this.serviceDuration = serviceDuration;
    }
    public Transportation(String idVehicle, String driver, LocalDateTime dataTimeService, String initialLocation, String finalLocation, String serviceStatus, int serviceDuration) {
        this.idVehicle = idVehicle;
        this.driver = driver;
        this.dataTimeService = dataTimeService;
        this.initialLocation = initialLocation;
        this.finalLocation = finalLocation;
        this.serviceStatus = serviceStatus;
        this.serviceDuration = serviceDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
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
              "id=" + id +
              ", idVehicle=" + idVehicle +
              ", driver=" + driver +
              ", dataTimeService=" + dataTimeService +
              ", initialLocation=" + initialLocation +
              ", finalLocation=" + finalLocation +
              ", serviceStatus=" + serviceStatus +
              ", serviceDuration=" + serviceDuration +
              '}';
   }
    
}
