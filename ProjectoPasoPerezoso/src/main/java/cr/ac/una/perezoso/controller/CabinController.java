/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.business.CabinLogic;
import cr.ac.una.perezoso.data.CabinData;
import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Cabin;
import java.io.IOException;
import java.sql.SQLException;
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
@RequestMapping("/cabins")
public class CabinController {
    
    private static final Logger logger=LoggerFactory.getLogger(CabinController.class);
    @Autowired
    private CabinLogic cabinLogic;
    @Autowired
    private final CabinData cabinData;
    
    // Inyección por constructor
    @Autowired
    public CabinController(CabinData cabinData) {
        this.cabinData = cabinData;
    }
   

    @GetMapping("/List")
    public String listCabin(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "cabinID", required = false) Integer cabinID, 
                         Model model) throws SQLException, ClassNotFoundException {
    
     LinkedList<Cabin> cabins;

    if (name != null && !name.isEmpty()) {
        // Buscar por nombre
        cabins = cabinLogic.searchCabinsByName(name);    
        if (cabins.isEmpty()) {
            model.addAttribute("errorMessage", "No se encontró ninguna cabaña con el nombre: " + name);
        }
    } else if (cabinID != null) {
        // Buscar por ID
        cabins = cabinLogic.searchCabinsByID(cabinID); // Devuelve una LinkedList<Cabin>
        if (cabins.isEmpty()) {
            model.addAttribute("errorMessage", "No se encontró ninguna cabaña con el ID: " + cabinID);
        }
    } else {
        // Si no hay filtro, mostrar todas las cabañas
        cabins = cabinLogic.listCabins();
    }

    model.addAttribute("titulo", "Listado de Cabañas");
    model.addAttribute("cantidad", cabins.size());
    model.addAttribute("cabins", cabins);
    return "list_cabins";
    }
    
    @GetMapping("/addForm")
    public String addCabinForm(Model model) {
    model.addAttribute("cabin", new Cabin()); // Pasar un objeto Cabin vacío al formulario
    return "add_cabin"; // Redirigir al formulario de agregar
}
     @PostMapping("/add")
    public String addCabin(@RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("capacity") int capacity,
                          @RequestParam("pricePerNight") double pricePerNight,
                          @RequestParam("location") String location,
                          @RequestParam("image") MultipartFile imageFile,
                          @RequestParam("includedServices") String includedServices, 
                          RedirectAttributes redirectAttributes) throws SQLException, 
                          ClassNotFoundException,IOException {       
        // Guardar la imagen y obtener la ruta relativa
    String imagePath = FileUploadUtil.saveFile(imageFile);   
        // Crear objeto de la cabaña
        Cabin cabin = new Cabin(name, description, capacity, pricePerNight, location, imagePath, includedServices);
        cabinLogic.addCabins(cabin);  // Insertar la cabaña en la base de datos        
        // Redirigir con un mensaje de éxito
    redirectAttributes.addFlashAttribute("successMessage", "Cabaña agregada correctamente.");
        return "redirect:/cabins/List";
    }
      
   @GetMapping("/updateForm")
    public String editCabin(@RequestParam("cabinID") int cabinID, Model model) 
                            throws SQLException, ClassNotFoundException {
        if (cabinID <= 0) {
            return "redirect:/cabins/List?error=Cabaña no encontrada";
        }
    // Obtener la cabaña existente por su ID
    Cabin cabin = cabinLogic.getCabinsBYID(cabinID);
    if (cabin != null) {
        model.addAttribute("cabin", cabin);
    } else {
        return "redirect:/cabins/List?error=Cabaña no encontrada";
    }
    return "edit_cabin"; // Redirigir al formulario de edición
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
                         RedirectAttributes redirectAttributes) throws SQLException, 
                         ClassNotFoundException,IOException {
   
    logger.debug("CabinID recibido: {}", cabinID); // Log para depuración  
    // Obtener la cabaña existente
    Cabin cabin = cabinLogic.getCabinsBYID(cabinID);
    // Si se subió una nueva imagen, guardarla y actualizar la ruta
    if (!imageFile.isEmpty()) {
        String imagePath = FileUploadUtil.saveFile(imageFile);
        cabin.setImage(imagePath);
    }
    // Actualizar los demás campos
    cabin.setName(name);
    cabin.setDescription(description);
    cabin.setCapacity(capacity);
    cabin.setPricePerNight(pricePerNight);
    cabin.setLocation(location);
    cabin.setIncludedServices(includedServices);
    
    // Guardar los cambios en la base de datos
    cabinData.updateCabin(cabin);
    // Redirigir con un mensaje de éxito
    redirectAttributes.addFlashAttribute("successMessage", "Cabaña actualizada correctamente.");
    return "redirect:/cabins/List";
}
    
    @GetMapping("/confirmDelete")
    public String confirmDelete(@RequestParam("cabinID") int cabinID, Model model) {
        model.addAttribute("cabinIDToDelete", cabinID); // Pasar el ID de la cabaña a eliminar
        model.addAttribute("showConfirmation", true); // Mostrar el mensaje de confirmación
        return "list_cabins"; // Volver a la misma página
    }
    
   @PostMapping("/delete")
   public String deleteCabin(@RequestParam("cabinID") int cabinID, RedirectAttributes redirectAttributes) throws SQLException, ClassNotFoundException {
       cabinLogic.deleteCabin(cabinID);
        // Agregar un mensaje de éxito
        redirectAttributes.addFlashAttribute("deleteSuccess", "La cabaña ha sido eliminada correctamente.");
       return "redirect:/cabins/List?deleteSuccess=true";
   }
    
}

