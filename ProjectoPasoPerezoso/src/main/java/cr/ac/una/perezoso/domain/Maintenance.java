/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import java.time.LocalDate;

/**
 *
 * @author dayan
 */
@Entity
@Table(name="tmaintenancetable")
public class Maintenance {
     @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private LocalDate maintenanceDate;//(selector de fecha)
    private String maintenanceType;//(Preventivo o correctivo)
    private String description;//(Area de texto)
    private String priorities;//(alta,media,baja)combo
    private String state;//(Pendiente, en proceso, terminada) combo
    private String assignedPersonal;//(combo usuario o tecnico)
    private String location;//texto

    public Maintenance() {
    }

    public Maintenance(LocalDate maintenanceDate, String maintenanceType, String description, String priorities, String state, String assignedPersonal, String location) {
        
        this.maintenanceDate = maintenanceDate;
        this.maintenanceType = maintenanceType;
        this.description = description;
        this.priorities = priorities;
        this.state = state;
        this.assignedPersonal = assignedPersonal;
        this.location = location;
    }
    
  

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorities() {
        return priorities;
    }

    public void setPriorities(String priorities) {
        this.priorities = priorities;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAssignedPersonal() {
        return assignedPersonal;
    }

    public void setAssignedPersonal(String assignedPersonal) {
        this.assignedPersonal = assignedPersonal;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    @Override
    public String toString() {
        return "Maintenance{" + "maintenanceDate=" + maintenanceDate + ", maintenanceType=" + maintenanceType + ", description=" + description + ", priorities=" + priorities + ", state=" + state + ", assignedPersonal=" + assignedPersonal + ", location=" + location + '}';
    }
    
    
    
}
