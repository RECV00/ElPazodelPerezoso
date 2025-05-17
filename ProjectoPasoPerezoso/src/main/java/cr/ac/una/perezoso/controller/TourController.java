/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;
import java.sql.SQLException;
import cr.ac.una.perezoso.data.TourData;
import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.service.TourService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourService tourService;
    // Mostrar todos los tours
     @GetMapping("/listTours")
    public String showTours(Model model) {
        List<Tour> tours = tourService.getAll();
        model.addAttribute("tours", tours);
        return "/tour/listadoTours";
    }

    // Método para filtrar tours por nombre
    @GetMapping("/filter")
    public String filterTours(@RequestParam String nameTour, Model model) {
        List<Tour> tours;
        if (nameTour != null && !nameTour.isEmpty()) {
            // Filtrar tours por nombre si se proporciona un valor
            tours = tourService.searchByName(nameTour);
        } else {
            // Mostrar todos los tours si no se proporciona un valor
            tours = tourService.getAll();
        }
        model.addAttribute("tours", tours); // Pasar la lista filtrada a la vista
        return "/tour/listadoTours"; // Renderiza la vista tours.html
    }
//------------------------------------------ANADIRNUEVO----------------------------------------------------------------------------------
    // Mostrar formulario para agregar un nuevo tour
    @GetMapping("/add")
    public String showAddTourForm(Model model) {
        model.addAttribute("tour", new Tour());
        return "/tour/addTour"; // Nombre de la vista HTML (addTour.html)
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
    @GetMapping("/edit/{id}")
    public String showEditTourForm(@PathVariable int id, Model model) {
        Tour tour = tourService.getById(id);
        model.addAttribute("tour", tour);
        return "/tour/editTour"; 
    }

    // Procesar el formulario para actualizar un tour
@PostMapping("/edit/{id}")
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
    redirectAttributes.addFlashAttribute("successMessage", "Tour actualizada correctamente.");
    return "redirect:/tours/listTours"; // Redirigir a la lista de tours
}
    
//------------------------------------------ELIMINAR-------------------------------------------------------------------------------
    // Eliminar un tour
    @GetMapping("/delete/{id}")
    public String deleteTour(@PathVariable int id) {
        TourData.deleteTour(id);
        return "redirect:/tours/listTours"; // Redirigir a la lista de tours
    }
}