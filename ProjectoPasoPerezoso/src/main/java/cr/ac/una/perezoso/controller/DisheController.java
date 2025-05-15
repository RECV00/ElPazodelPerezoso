/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Dishe;
import cr.ac.una.perezoso.service.DisheService;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
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
    
    private final DisheService disheService;

    @Autowired
    public DisheController(DisheService disheService) {
        this.disheService = disheService;
    }

    @GetMapping("/ListFood")
    public String listDishes(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "disheID", required = false) Integer dishId,
                          Model model) {
        
        List<Dishe> dishes;
        
        if (name != null && !name.isEmpty()) {
            dishes = disheService.searchByName(name);
            if (dishes.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontraron platos con nombre: " + name);
            }
        } else if (dishId != null) {
            Dishe dish = disheService.getById(dishId);
            dishes = dish != null ? List.of(dish) : List.of();
            if (dishes.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontraron platos con ID: " + dishId);
            }
        } else {
            dishes = disheService.getAll();
        }
        
        model.addAttribute("title", "Listado de Platos");
        model.addAttribute("count", dishes.size());
        model.addAttribute("dishes", dishes);
        return "/dishe/list_dishe";
    }
    
    @GetMapping("/addForm")
    public String addDishForm(Model model) {
        model.addAttribute("dish", new Dishe());
        return "/dishe/add_dishe";
    }
    
    @PostMapping("/add")
    public String addDish(@RequestParam("name") String name,
                        @RequestParam("description") String description,
                        @RequestParam("price") double price,
                        @RequestParam("category") String category,
                        @RequestParam("available") boolean available,
                        @RequestParam("image") MultipartFile imageFile,
                        @RequestParam("preparationTime") LocalTime preparationTime,
                        RedirectAttributes redirectAttributes) throws IOException {
        
        String imagePath = FileUploadUtil.saveFile(imageFile);
        
        Dishe dish = new Dishe(name, description, price, category, available, imagePath, preparationTime);
        disheService.save(dish);
        
        redirectAttributes.addFlashAttribute("successMessage", "Plato añadido exitosamente.");
        return "redirect:/dishes/ListFood";
    }
    
    @GetMapping("/updateForm")
    public String editDish(@RequestParam("disheID") int dishId, Model model) {
        if (dishId <= 0) {
            return "redirect:/dishes/ListFood?error=Plato no encontrado";
        }
        
        Dishe dish = disheService.getById(dishId);
        if (dish != null) {
            model.addAttribute("dish", dish);
        } else {
            return "redirect:/dishes/ListFood?error=Plato no encontrado";
        }
        return "/dishe/edit_dishe";
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
                           RedirectAttributes redirectAttributes) throws IOException {
        
        logger.debug("ID de Plato recibido: {}", disheID);
        
        Dishe dish = disheService.getById(disheID);
        if (dish == null) {
            return "redirect:/dishes/ListFood?error=Plato no encontrado";
        }
        
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
        
        disheService.save(dish);
        
        redirectAttributes.addFlashAttribute("successMessage", "Plato actualizado exitosamente.");
        return "redirect:/dishes/ListFood";
    }
    
    @GetMapping("/confirmDelete")
    public String confirmDelete(@RequestParam("disheID") int dishId, Model model) {
        model.addAttribute("dishIdToDelete", dishId);
        model.addAttribute("showConfirmation", true);
        return "/dishe/list_dishe";
    }
    
    @PostMapping("/delete")
    public String deleteDish(@RequestParam("disheID") int dishId, 
                           RedirectAttributes redirectAttributes) {
        disheService.delete(dishId);
        redirectAttributes.addFlashAttribute("deleteSuccess", "Plato eliminado exitosamente.");
        return "redirect:/dishes/ListFood?deleteSuccess=true";
    }
}
