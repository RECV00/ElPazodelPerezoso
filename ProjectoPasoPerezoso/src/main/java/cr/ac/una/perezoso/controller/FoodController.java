/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.data.FoodData;
import cr.ac.una.perezoso.domain.Food;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author corra
 */

@Controller
@RequestMapping("/foods")
public class FoodController {

    // Mostrar todos los registros de comida
    @GetMapping("/listaFoods")
    public String showFoods(Model model) {
        List<Food> foods = FoodData.getFoods(); // Obtener todos los registros
        model.addAttribute("foods", foods); // Pasar la lista a la vista
        return "/listFood"; // Renderiza la vista listadoFoods.html
    }

    
//----------------------------------------FILTRAR--------------------------------------------------------------------
    // Método para filtrar comidas por menú seleccionado
    @GetMapping("/filter")
    public String filterFoods(@RequestParam String selectedMenu, Model model) {
        try {
            List<Food> foods;
            if (selectedMenu != null && !selectedMenu.isEmpty()) {
                // Filtrar comidas por menú seleccionado
                foods = FoodData.searchFoodsByMenu(selectedMenu);
            } else {
                // Mostrar todas las comidas si no se proporciona un valor
                foods = FoodData.getFoods();
            }
            model.addAttribute("foods", foods); // Pasar la lista filtrada a la vista
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "/listFood"; // Renderiza la vista listadoFoods.html
    }

    
//------------------------------------ANADIR---------------------------------------------------------------------
    // Mostrar formulario para agregar un nuevo registro de comida
    @GetMapping("/add")
    public String showAddFoodForm(Model model) {
        model.addAttribute("food", new Food());
        return "/addFood"; // Nombre de la vista HTML (addFood.html)
    }

    // Procesar el formulario para agregar un nuevo registro de comida
    @PostMapping("/add")
    public String addFood(
        @RequestParam("selectedMenu") String selectedMenu,
        @RequestParam("dateService") LocalDate dateService,
        @RequestParam("hourService") LocalTime hourService,
        @RequestParam("numberDishes") int numberDishes,
        @RequestParam("customOptions") String customOptions,
        @RequestParam("additionalObservactions") String additionalObservactions,
        @RequestParam("typeService") String typeService,
        RedirectAttributes redirectAttributes
    ) {
        Food food = new Food(selectedMenu, dateService, hourService, numberDishes, 
                           customOptions, additionalObservactions, typeService);
        FoodData.createFood(food);
        
        // Redirigir con un mensaje de éxito
        redirectAttributes.addFlashAttribute("successMessage", "Registro de comida agregado correctamente.");
        return "redirect:/foods/listaFoods"; // Redirigir a la lista de comidas
    }

//---------------------------------------------EDITAR----------------------------------------------------------------------
    // Mostrar formulario para editar un registro de comida
    @GetMapping("/edit/{id}")
    public String showEditFoodForm(@PathVariable int id, Model model) {
        try {
            Food food = FoodData.getFoodById(id);
            model.addAttribute("food", food);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "/editFood"; 
    }

    // Procesar el formulario para actualizar un registro de comida
    @PostMapping("/edit/{id}")
    public String updateFood(
        @RequestParam("id_Food") int id_Food,
        @RequestParam("selectedMenu") String selectedMenu,
        @RequestParam("dateService") LocalDate dateService,
        @RequestParam("hourService") LocalTime hourService,
        @RequestParam("numberDishes") int numberDishes,
        @RequestParam("customOptions") String customOptions,
        @RequestParam("additionalObservactions") String additionalObservactions,
        @RequestParam("typeService") String typeService,
        RedirectAttributes redirectAttributes
    ) throws SQLException, ClassNotFoundException {
        
        Food food = FoodData.getFoodById(id_Food);
        // Actualizar los campos
        food.setSelectedMenu(selectedMenu);
        food.setDateService(dateService);
        food.setHourService(hourService);
        food.setNumberDishes(numberDishes);
        food.setCustomOptions(customOptions);
        food.setAdditionalObservactions(additionalObservactions);
        food.setTypeService(typeService);
        
        // Guardar los cambios en la base de datos
        FoodData.updateFood(food);
        
        // Redirigir con un mensaje de éxito
        redirectAttributes.addFlashAttribute("successMessage", "Registro de comida actualizado correctamente.");
        return "redirect:/foods/listaFoods"; // Redirigir a la lista de comidas
    }
    
//----------------------------------------------ELIMINAR--------------------------------------------------------------------
    // Eliminar un registro de comida
    @GetMapping("/delete/{id}")
    public String deleteFood(@PathVariable int id) {
        FoodData.deleteFood(id);
        return "redirect:/foods/listaFoods"; // Redirigir a la lista de comidas
    }
}
