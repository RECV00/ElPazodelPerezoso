/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.business.DisheLogic;
import cr.ac.una.perezoso.data.DisheData;
import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Dishe;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/dishes")
public class DisheController {
    
    private static final Logger logger = LoggerFactory.getLogger(DisheController.class);
    
    @Autowired
    private DisheLogic disheLogic;
    
    @Autowired
    private final DisheData disheData;
    
    @Autowired
    public DisheController(DisheData disheData) {
        this.disheData = disheData;
    }

    @GetMapping("/ListFood")
    public String listDishes(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "disheID", required = false) Integer dishId,
                          Model model) throws SQLException, ClassNotFoundException {
        
        LinkedList<Dishe> dishes;
        
        if (name != null && !name.isEmpty()) {
            dishes = disheLogic.searchDisheByName(name);
            if (dishes.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontraron platos con nombre: " + name);
            }
        } else if (dishId != null) {
            dishes = disheLogic.searchDisheByID(dishId);
            if (dishes.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontraron platos con ID: " + dishId);
            }
        }  else {
            dishes = disheLogic.listDishes();
        }
        
        model.addAttribute("title", "Dishes List");
        model.addAttribute("count", dishes.size());
        model.addAttribute("dishes", dishes);
        return "dishe/list_dishe";
    }
    
    @GetMapping("/addForm")
    public String addDishForm(Model model) {
        model.addAttribute("dish", new Dishe());
        return "dishe/add_dishe";
    }
    
    @PostMapping("/add")
    public String addDish(@RequestParam("name") String name,
                        @RequestParam("description") String description,
                        @RequestParam("price") double price,
                        @RequestParam("category") String category,
                        @RequestParam("available") boolean available,
                        @RequestParam("image") MultipartFile imageFile,
                        @RequestParam("preparationTime") LocalTime preparationTime,
                        RedirectAttributes redirectAttributes) throws SQLException, ClassNotFoundException, IOException {
        
        String imagePath = FileUploadUtil.saveFile(imageFile);
        
        Dishe dish = new Dishe(name, description, price, category, available, imagePath, preparationTime);
        disheLogic.addDishe(dish);
        
        redirectAttributes.addFlashAttribute("successMessage", "Plato añadido exitosamente.");
        return "redirect:/dishes/ListFood";
    }
    
    @GetMapping("/updateForm")
    public String editDish(@RequestParam("disheID") int dishId, Model model) 
                          throws SQLException, ClassNotFoundException {
        if (dishId <= 0) {
            return "redirect:/dishes/ListFood?error=Dish not found";
        }
        
        Dishe dish = disheLogic.getDisheByID(dishId);
        if (dish != null) {
            model.addAttribute("dish", dish);
        } else {
            return "redirect:/dishes/ListFood?error=Dish not found";
        }
        return "dishe/edit_dishe";
    }
    
    @PostMapping("/update")
    public String updateDish(@RequestParam("disheID") int disheID,
                           @RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("price") double price,
                           @RequestParam("category") String category,
                           @RequestParam("available") boolean available,
                           @RequestParam("image") MultipartFile imageFile,
                           @RequestParam("preparationTime") LocalTime preparationTime,
                           RedirectAttributes redirectAttributes) throws SQLException, ClassNotFoundException, IOException {
        
        logger.debug("Dish ID received: {}", disheID);
        
        Dishe dish = disheLogic.getDisheByID(disheID);
        
        if (!imageFile.isEmpty()) {
            String imagePath = FileUploadUtil.saveFile(imageFile);
            dish.setImageUrl(imagePath);
        }
        
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setCategory(category);
        dish.setAvailable(available);
        dish.setPreparationTime(preparationTime);
        
        disheData.updateDish(dish);
        
        redirectAttributes.addFlashAttribute("successMessage", "Plato actualizado exitosamente.");
        return "redirect:/dishes/ListFood";
    }
    
    @GetMapping("/confirmDelete")
    public String confirmDelete(@RequestParam("disheID") int dishId, Model model) {
        model.addAttribute("dishIdToDelete", dishId);
        model.addAttribute("showConfirmation", true);
        return "dishe/list_dishe";
    }
    
    @PostMapping("/delete")
    public String deleteDish(@RequestParam("disheID") int dishId, 
                           RedirectAttributes redirectAttributes) throws SQLException, ClassNotFoundException {
        disheLogic.deleteDishe(dishId);
        redirectAttributes.addFlashAttribute("deleteSuccess", "Plato eliminado exitosamente.");
        return "redirect:/dishes/ListFood?deleteSuccess=true";
    }
}
