/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.Column;
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
@Table(name = "tb_employee")
@DiscriminatorValue("EMPLOYEE")
@PrimaryKeyJoinColumn(name = "id_employee")
public class Employee extends User{
    @Column(name = "workstation")
    private String workstation;
    @Column(name = "account_number")
    private String account_number;
    @Column(name = "salary")
    private double salary;
    
     public Employee() {
        super();
        this.setUserType("EMPLOYEE");
    }
     
     public Employee(String name, String last_name, String identification, LocalDate birthdate,
                    String email, int phone, String address, String password, 
                    String photo, String workstation, String account_number, double salary) {
        super(name, last_name, identification, birthdate, email, phone, address, password, photo);
        this.workstation = workstation;
        this.account_number = account_number;
        this.salary = salary;
        this.userType = "EMPLOYEE";
    }

    public Employee(String nombre, String apellido, String cedula, LocalDate fechaN, String email, int telefono, String direccion, String password, String fotoPerfil) {
        super(nombre, apellido, cedula, fechaN, email, telefono, direccion, password, fotoPerfil);
        this.workstation = workstation;
        this.account_number = account_number;
        this.salary = salary;
        this.userType = "EMPLOYEE";
    }

    // Getters y Setters específicos de Employee
    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
