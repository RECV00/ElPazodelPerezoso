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
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/dishes")
public class DisheController {
    
    private static final Logger logger = LoggerFactory.getLogger(DisheController.class);
    
    private final DisheService disheService;

    // Lista de categorías disponibles (puedes obtenerlas de la BD si lo prefieres)
    private final List<String> categories = Arrays.asList(
        "Entrada", "Plato Principal", "Postre", "Bebida", "Ensalada", "Sopa"
    );
    
    
    @Autowired
    public DisheController(DisheService disheService) {
        this.disheService = disheService;
    }
    
     // Versión completa de la página (puede ser útil para SSR)
    @GetMapping("/ListFood")
    public String listDishes(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "disheID", required = false) Integer dishId,
                          @RequestParam(value = "category", required = false) String category,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "4") int size,
                          Model model) {
        
        Page<Dishe> dishPage;
        
        if (name != null && !name.isEmpty()) {
            dishPage = disheService.findByNameContaining(name, PageRequest.of(page, size));
        }  else if (category != null && !category.isEmpty()) {
            dishPage = disheService.findByCategory(category, PageRequest.of(page, size));
        } else {
            dishPage = disheService.getAll(PageRequest.of(page, size));
        }
        
        model.addAttribute("title", "Listado de Platos");
        model.addAttribute("count", dishPage.getTotalElements());
        model.addAttribute("dishes", dishPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", dishPage.getTotalPages());
        model.addAttribute("categories", categories);
        return "/dishe/list_dishe";
    }
    
     // Endpoint para AJAX
    @GetMapping("/api/list")
    @ResponseBody
    public Page<Dishe> listDishesApi(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "4") int size) {
        
        if (name != null && !name.isEmpty()) {
            return disheService.findByNameContaining(name, PageRequest.of(page, size));
        } else if (category != null && !category.isEmpty()) {
            return disheService.findByCategory(category, PageRequest.of(page, size));
        } else {
            return disheService.getAll(PageRequest.of(page, size));
        }
    }
    
@PostMapping("/add")
    public String addDish(@RequestParam("name") String name,
                        @RequestParam("description") String description,
                        @RequestParam("price") double price,
                        @RequestParam("category") String category,
                        @RequestParam("available") boolean available,
                        @RequestParam("image") MultipartFile imageFile,
                        @RequestParam("preparationTime") String preparationTime,
                        RedirectAttributes redirectAttributes) throws IOException {
        
        String imagePath = FileUploadUtil.saveFile(imageFile);
        LocalTime prepTime = LocalTime.parse(preparationTime);
        
        Dishe dish = new Dishe(name, description, price, category, available, imagePath, prepTime);
        disheService.save(dish);
        
        redirectAttributes.addFlashAttribute("successMessage", "Plato añadido exitosamente.");
        return "redirect:/dishes/ListFood";
    }
   
   @GetMapping("/edit-form")
    public String getEditModal(@RequestParam("disheID") int dishId, Model model) {
        Dishe dish = disheService.getById(dishId);
        if (dish == null) {
            return "redirect:/dishes/ListFood?error=Plato no encontrado";
        }
        model.addAttribute("dish", dish);
        model.addAttribute("categories", categories);
        return "/dishe/edit_dish_modal :: editModal";
    }
    
    @PostMapping("/update")
    public String updateDish(@RequestParam("disheID") int disheID,
                           @RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("price") double price,
                           @RequestParam("category") String category,
                           @RequestParam("available") boolean available,
                           @RequestParam("image") MultipartFile imageFile,
                           @RequestParam("preparationTime") String preparationTime,
                           RedirectAttributes redirectAttributes) throws IOException {
        
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
        dish.setPreparationTime(LocalTime.parse(preparationTime));
        
        disheService.save(dish);
        
        redirectAttributes.addFlashAttribute("successMessage", "Plato actualizado exitosamente.");
        return "redirect:/dishes/ListFood";
    }
    
   
    @DeleteMapping("/delete/{id}")
    public String deleteDish(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            Dishe dish = disheService.getById(id);
            if (dish == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Plato no encontrado");
                return "redirect:/dishes/ListFood";
            }
            
            disheService.delete(id);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Plato eliminado exitosamente.");
        } catch (Exception e) {
            logger.error("Error al eliminar plato ID: " + id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar plato: " + e.getMessage());
        }
        return "redirect:/dishes/ListFood";
    }

    
}
