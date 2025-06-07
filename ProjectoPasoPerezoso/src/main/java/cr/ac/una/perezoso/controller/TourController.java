/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;
import java.sql.SQLException;
import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.service.TourService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourService tourService;
    
     private static final Logger logger = LoggerFactory.getLogger(TourController.class);
    // Mostrar todos los tours
    @GetMapping("/listTours")
public String listTours(Model model,
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "4") int size) {
    
    Page<Tour> tourPage = tourService.getAll(PageRequest.of(page, size));
    model.addAttribute("tours", tourPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", tourPage.getTotalPages());
    
    return "/tour/listadoTours";
}
    // Endpoint para la lista paginada y filtrada (AJAX)
    @GetMapping("/api/list")
    @ResponseBody
    public Page<Tour> getToursPaginated(
            @RequestParam(required = false) String nameTour,
            @RequestParam(required = false) String startingPoint,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        if (nameTour != null && !nameTour.isEmpty()) {
            return tourService.findByNameContaining(nameTour, pageable);
        } else if (startingPoint != null && !startingPoint.isEmpty()) {
            return tourService.findByStartingPointContaining(startingPoint, pageable);
        } else {
            return tourService.getAll(pageable);
        }
    }
    
    
//------------------------------------------ANADIRNUEVO----------------------------------------------------------------------------------
    // Mostrar formulario para agregar un nuevo tour
    @GetMapping("/add-form")
    public String showAddTourForm(Model model) {
        model.addAttribute("tour", new Tour());
        return "/tour/add_tour_modal :: addModal";
    }

    // Procesar el formulario para agregar un nuevo tour
   @PostMapping("/add")
public String addTour(
    @RequestParam("nameTour") String nameTour,
    @RequestParam("description") String description,
    @RequestParam("price") double price,
    @RequestParam("date") LocalDate date,
    @RequestParam("startTime") LocalTime startTime,
    @RequestParam("duration") LocalTime duration,
    @RequestParam("startingPoint") String startingPoint,
    @RequestParam("multimedia") MultipartFile multimediaFile, // multimedia separado
    RedirectAttributes redirectAttributes
) throws IOException {
        // Guardar la imagen y obtener la ruta relativa
         String imagePath = FileUploadUtil.saveFile(multimediaFile);   
  
             Tour tour = new Tour(nameTour, description, price, date, startTime, duration, startingPoint,imagePath);
             tourService.save(tour);        
             // Redirigir con un mensaje de éxito
         redirectAttributes.addFlashAttribute("successMessage", "Tour agregada correctamente.");
     return "redirect:/tours/listTours"; // Redirigir a la lista de tours
}


//---------------------------------------EDITAR--------------------------------------------------------------------------------------


    // Mostrar formulario para editar un tour
    @GetMapping("/edit-form")
    public String showEditTourForm(@PathVariable int id, Model model) {
        Tour tour = tourService.getById(id);
        
        if (tour == null) {
            return "redirect:/tours/listadoTours?error=Cabaña no encontrada";
        }
        model.addAttribute("tour", tour);
        return "/tour/edit_tour_modal :: editModal";
        
        
        
    }

    // Procesar el formulario para actualizar un tour
@PostMapping("/update")
public String updateTour(@RequestParam("id_Tour") int id_Tour,
                         @RequestParam("nameTour") String nametTour, 
                         @RequestParam("description") String description, 
                         @RequestParam("price") double price, 
                         @RequestParam("date") LocalDate date, 
                         @RequestParam("startTime") LocalTime startTime, 
                         @RequestParam("duration") LocalTime duration,
                         @RequestParam("startingPoint") String startingPoint,
                         @RequestParam("multimedia") MultipartFile imageFile, 
                         RedirectAttributes redirectAttributes) throws SQLException, 
                         ClassNotFoundException,IOException {
   
 
   
    Tour tour = tourService.getById(id_Tour);
    
     if (tour == null) {
            return "redirect:/tours/listTours?error=Tour no encontrado";
        }
    // Si se subió una nueva imagen, guardarla y actualizar la ruta
    if (!imageFile.isEmpty()) {
        String imagePath = FileUploadUtil.saveFile(imageFile);
        tour.setMultimedia(imagePath);
    }
    // Actualizar los demás campos
    tour.setNameTour(nametTour);
    tour.setDescription(description);
    tour.setPrice(price);
    tour.setDate(date);
    tour.setStartTime(startTime);
    tour.setDuration(duration);
    tour.setStartingPoint(startingPoint);
    
    // Guardar los cambios en la base de datos
     tourService.save(tour);
    // Redirigir con un mensaje de éxito
    redirectAttributes.addFlashAttribute("successMessage", "Tour actualizado correctamente.");
    return "redirect:/tours/listTours"; // Redirigir a la lista de tours
}
    
//------------------------------------------ELIMINAR-------------------------------------------------------------------------------

    
    @DeleteMapping("/delete/{id}")
public String deleteTour(@PathVariable int id, RedirectAttributes redirectAttributes) {
    try {
        Tour tour = tourService.getById(id);
        if (tour == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tour no encontrado");
            return "redirect:/tours/listTours";
        }
        
        tourService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Tour eliminado correctamente");
    } catch (Exception e) {
        logger.error("Error al eliminar el Tour ID: " + id, e);
        redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar tour: " + e.getMessage());
    }
    return "redirect:/tours/listTours";
}
}