/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Cabin;
import cr.ac.una.perezoso.service.CabinService;
import java.io.IOException;
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
@RequestMapping("/cabins")
public class CabinController {
    
     private static final Logger logger = LoggerFactory.getLogger(CabinController.class);
    
    @Autowired
    private CabinService cabinService;

    @GetMapping("/List")
    public String listCabin(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "cabinID", required = false) Integer cabinID, 
                         Model model) {
    
        List<Cabin> cabins;

        if (name != null && !name.isEmpty()) {
            // Buscar por nombre (necesitarías implementar este método en el servicio)
            cabins = cabinService.findByNameContaining(name);    
            if (cabins.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontró ninguna cabaña con el nombre: " + name);
            }
        } else if (cabinID != null) {
            // Buscar por ID
            Cabin cabin = cabinService.getById(cabinID);
            cabins = cabin != null ? List.of(cabin) : List.of();
            if (cabins.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontró ninguna cabaña con el ID: " + cabinID);
            }
        } else {
            // Si no hay filtro, mostrar todas las cabañas
            cabins = cabinService.getAll();
        }

        model.addAttribute("titulo", "Listado de Cabañas");
        model.addAttribute("cantidad", cabins.size());
        model.addAttribute("cabins", cabins);
        return "/cabin/list_cabins";
    }
    
    @GetMapping("/addForm")
    public String addCabinForm(Model model) {
        model.addAttribute("cabin", new Cabin());
        return "/cabin/add_cabin";
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
      
    @GetMapping("/updateForm")
    public String editCabin(@RequestParam("cabinID") int cabinID, Model model) {
        if (cabinID <= 0) {
            return "redirect:/cabins/List?error=Cabaña no encontrada";
        }
        
        Cabin cabin = cabinService.getById(cabinID);
        if (cabin != null) {
            model.addAttribute("cabin", cabin);
        } else {
            return "redirect:/cabins/List?error=Cabaña no encontrada";
        }
        return "/cabin/edit_cabin";
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
   
        logger.debug("CabinID recibido: {}", cabinID);
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
    
    @GetMapping("/confirmDelete")
    public String confirmDelete(@RequestParam("cabinID") int cabinID, Model model) {
        model.addAttribute("cabinIDToDelete", cabinID);
        model.addAttribute("showConfirmation", true);
        return "/cabin/list_cabins";
    }
    
    @PostMapping("/delete")
    public String deleteCabin(@RequestParam("cabinID") int cabinID, 
                           RedirectAttributes redirectAttributes) {
        cabinService.delete(cabinID);
        redirectAttributes.addFlashAttribute("deleteSuccess", "La cabaña ha sido eliminada correctamente.");
        return "redirect:/cabins/List?deleteSuccess=true";
    }
}

