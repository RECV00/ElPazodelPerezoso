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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author corra
 */
@Entity
@Table(name = "tb_food")
public class Food {
    
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_food")
    private int id_food;
    
    @Column(name = "selected_menu", nullable = false, length = 100)
    private String selectedMenu;
    
    @Column(name = "date_service", nullable = false)
    private LocalDate dateService;
    
    @Column(name = "hour_service", nullable = false)
    private LocalTime hourService;
    
    @Column(name = "number_dishes", nullable = false)
    private int numberDishes;
    
    @Column(name = "custom_options", length = 255)
    private String customOptions;
    
    @Column(name = "additional_observations", length = 500)
    private String additionalObservations;
    
    @Column(name = "type_service", nullable = false, length = 50)
    private String typeService;

//    @OneToOne
//    @JoinColumn(name = "booking_id")
//    private Booking booking;
//    
//    @ManyToMany
//    @JoinTable(
//        name = "food_dishes",
//        joinColumns = @JoinColumn(name = "food_id"),
//        inverseJoinColumns = @JoinColumn(name = "dish_id")
//    )
//    private List<Dishe> dishes;
//    
//    private LocalDate serviceDate;
//    private String specialNotes;
    public Food() {
    }

    public Food(String selectedMenu, LocalDate dateService, LocalTime hourService, 
               int numberDishes, String customOptions, String additionalObservations, 
               String typeService) {
        this.selectedMenu = selectedMenu;
        this.dateService = dateService;
        this.hourService = hourService;
        this.numberDishes = numberDishes;
        this.customOptions = customOptions;
        this.additionalObservations = additionalObservations;
        this.typeService = typeService;
    }

    // Getters y Setters
    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public String getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(String selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public LocalDate getDateService() {
        return dateService;
    }

    public void setDateService(LocalDate dateService) {
        this.dateService = dateService;
    }

    public LocalTime getHourService() {
        return hourService;
    }

    public void setHourService(LocalTime hourService) {
        this.hourService = hourService;
    }

    public int getNumberDishes() {
        return numberDishes;
    }

    public void setNumberDishes(int numberDishes) {
        this.numberDishes = numberDishes;
    }

    public String getCustomOptions() {
        return customOptions;
    }

    public void setCustomOptions(String customOptions) {
        this.customOptions = customOptions;
    }

    public String getAdditionalObservations() {
        return additionalObservations;
    }

    public void setAdditionalObservations(String additionalObservations) {
        this.additionalObservations = additionalObservations;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }    

    public Object getDishes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
