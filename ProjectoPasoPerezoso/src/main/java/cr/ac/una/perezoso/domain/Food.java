/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author corra
 */
public class Food {
    
    private int id_Food;
    private String selectedMenu;
    private LocalDate dateService;
    private LocalTime hourService;
    private int numberDishes;
    private String customOptions;
    private String additionalObservactions;
    private String typeService;

    public Food() {
    }

    public Food(String selectedMenu, LocalDate dateService, LocalTime hourService, int numberDishes, String customOptions, String additionalObservactions, String typeService) {
        this.selectedMenu = selectedMenu;
        this.dateService = dateService;
        this.hourService = hourService;
        this.numberDishes = numberDishes;
        this.customOptions = customOptions;
        this.additionalObservactions = additionalObservactions;
        this.typeService = typeService;
    }

    public int getId_Food() {
        return id_Food;
    }

    public void setId_Food(int id_Food) {
        this.id_Food = id_Food;
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

    public String getAdditionalObservactions() {
        return additionalObservactions;
    }

    public void setAdditionalObservactions(String additionalObservactions) {
        this.additionalObservactions = additionalObservactions;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    @Override
    public String toString() {
        return "Food{" + "id_Food=" + id_Food + ", selectedMenu=" + selectedMenu + ", dateService=" + dateService + ", hourService=" + hourService + ", numberDishes=" + numberDishes + ", customOptions=" + customOptions + ", additionalObservactions=" + additionalObservactions + ", typeService=" + typeService + '}';
    }
    
}
