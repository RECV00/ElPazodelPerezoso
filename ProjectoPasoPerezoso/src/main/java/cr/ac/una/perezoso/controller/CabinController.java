/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Cabin;
import cr.ac.una.perezoso.service.CabinService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

/**
 *
 * @author keyna
 */

@Controller
@RequestMapping("/cabins")
public class CabinController {
    
     private static final Logger logger = LoggerFactory.getLogger(CabinController.class);
    
    @Autowired
    private CabinService cabinService;

   
    @GetMapping("/List")
    public String listCabins(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "4") int size) {
        Page<Cabin> cabinPage = cabinService.getAll(PageRequest.of(page, size));
        model.addAttribute("cabins", cabinPage.getContent());
         model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", cabinPage.getTotalPages());
        
        
        return "/cabin/list_cabins";
    }

    // Endpoint para AJAX
    @GetMapping("/api/list")
    @ResponseBody
    public Page<Cabin> listCabinsApi(
    @RequestParam(value = "name", required = false) String name,
    @RequestParam(value = "location", required = false) String location,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "4") int size) {
    
    if (name != null && !name.isEmpty()) {
        return cabinService.findByNameContaining(name, PageRequest.of(page, size));
    } else if (location != null && !location.isEmpty()) {
        return cabinService.findByLocation(location, PageRequest.of(page, size));
    } else {
        return cabinService.getAll(PageRequest.of(page, size));
    }
}
    
    @GetMapping("/add-form")
    public String getAddModal() {
        return "/cabin/add_cabin_modal :: addModal";
    }
    
    @PostMapping("/add")
    public String addCabin(@RequestParam("name") String name,
                        @RequestParam("description") String description,
                        @RequestParam("capacity") int capacity,
                        @RequestParam("pricePerNight") double pricePerNight,
                        @RequestParam("location") String location,
                        @RequestParam("image") MultipartFile imageFile,
                        @RequestParam("includedServices") String includedServices, 
                        RedirectAttributes redirectAttributes) throws IOException {
        
        String imagePath = FileUploadUtil.saveFile(imageFile);   
        Cabin cabin = new Cabin(name, description, capacity, pricePerNight, 
                              location, imagePath, includedServices);
        cabinService.save(cabin);
        
        redirectAttributes.addFlashAttribute("successMessage", "Cabaña agregada correctamente.");
        return "redirect:/cabins/List";
    }
      
     @GetMapping("/edit-form")
    public String getEditModal(@RequestParam("cabinID") int cabinID, Model model) {
        Cabin cabin = cabinService.getById(cabinID);
        if (cabin == null) {
            return "redirect:/cabins/List?error=Cabaña no encontrada";
        }
        model.addAttribute("cabin", cabin);
        return "/cabin/edit_cabin_modal :: editModal";
    }
    
    
    @PostMapping("/update")
    public String updateCabin(@RequestParam("cabinID") int cabinID,
                           @RequestParam("name") String name, 
                           @RequestParam("description") String description, 
                           @RequestParam("capacity") int capacity, 
                           @RequestParam("pricePerNight") double pricePerNight, 
                           @RequestParam("location") String location, 
                           @RequestParam("image") MultipartFile imageFile, 
                           @RequestParam("includedServices") String includedServices,
                           RedirectAttributes redirectAttributes) throws IOException {
   
//        logger.debug("CabinID recibido: {}", cabinID);
        Cabin cabin = cabinService.getById(cabinID);
        
        if (cabin == null) {
            return "redirect:/cabins/List?error=Cabaña no encontrada";
        }
        
        if (!imageFile.isEmpty()) {
            String imagePath = FileUploadUtil.saveFile(imageFile);
            cabin.setImage(imagePath);
        }
        
        cabin.setName(name);
        cabin.setDescription(description);
        cabin.setCapacity(capacity);
        cabin.setPricePerNight(pricePerNight);
        cabin.setLocation(location);
        cabin.setIncludedServices(includedServices);
        
        cabinService.save(cabin);
        
        redirectAttributes.addFlashAttribute("successMessage", "Cabaña actualizada correctamente.");
        return "redirect:/cabins/List";
    }
    
    
      @DeleteMapping("/delete/{id}")
public String deleteCabin(@PathVariable int id, RedirectAttributes redirectAttributes) {
    try {
        Cabin cabin = cabinService.getById(id);
        if (cabin == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cabaña no encontrada");
            return "redirect:/cabins/List";
        }
        
        cabinService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Cabaña eliminada correctamente");
    } catch (Exception e) {
        logger.error("Error al eliminar cabaña ID: " + id, e);
        redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar cabaña: " + e.getMessage());
    }
    return "redirect:/cabins/List";
}
}

