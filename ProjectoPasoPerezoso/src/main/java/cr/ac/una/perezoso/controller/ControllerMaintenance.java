
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.business.Logic;
import cr.ac.una.perezoso.domain.Maintenance;
import cr.ac.una.perezoso.domain.Tour;
import cr.ac.una.perezoso.service.MaintenanceService;
//import java.awt.print.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dayan
 */
@Controller
@RequestMapping("/maintenance")
public class ControllerMaintenance {
    
     @Autowired
    private MaintenanceService ms;
    ///maintenance/list
 /*@GetMapping("/list")
public String viewList(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "3") int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("maintenanceDate").descending());
    Page<Maintenance> pagedResult = ms.getAll((org.springframework.data.domain.Pageable) pageable);

    model.addAttribute("listM", pagedResult.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pagedResult.getTotalPages());

    return "/HTMLMaintenance/index";
}*/
     
     
     @GetMapping("/list")
    public String listMantenimientos(Model model, @PageableDefault(size = 5) Pageable pageable) {
    Page<Maintenance> page = ms.getAll(pageable);

    model.addAttribute("listM", page.getContent());
    model.addAttribute("currentPage", page.getNumber());
    model.addAttribute("totalPages", page.getTotalPages());

    return "/HTMLMaintenance/index"; 
}


  
  @GetMapping("/Form")
  public String form(){
    return "/HTMLMaintenance/create";
  }
  
  @PostMapping("/createMaintenance")
public String saveMaintenance(
    @RequestParam("maintenanceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maintenanceDate,
    @RequestParam("maintenanceType") String maintenanceType,
    @RequestParam("description") String description,
    @RequestParam("priorities") String priorities,
    @RequestParam("state") String state,
    @RequestParam("assignedPersonal") String assignedPersonal,
    @RequestParam("location") String location,
    Model model) {

    Logic logic = new Logic();

    if ( !logic.validateWord(maintenanceType) || 
        !logic.checkStrings(description) || !logic.validateWord(priorities) || 
        !logic.validateWord(state) || !logic.validateWord(assignedPersonal) || 
        !logic.checkStrings(location)) {

        model.addAttribute("error", "Todos los campos son obligatorios y deben ser válidos.");
        return "/HTMLMaintenance/create";
    }else if(!logic.validateLocalDate(maintenanceDate)){
         model.addAttribute("error", "La fecha no puede ser anterior a hoy.");
    
    }

    try {
        Maintenance maintenance = new Maintenance(maintenanceDate, maintenanceType, description, priorities, state, assignedPersonal, location);
        ms.save(maintenance);
        model.addAttribute("message", "Mantenimiento agregado correctamente.");
    } catch (Exception e) {
        model.addAttribute("error", "Mantenimiento no creado. Intente de nuevo.");
    }

    return "/HTMLMaintenance/create";
}



@GetMapping("/FormUpdate")
public String showUpdateForm(@RequestParam("id") int id, Model model) {
    Maintenance maintenance = ms.getById(id);
    
    if (maintenance == null) {
        model.addAttribute("alertTitle", "Error");
        model.addAttribute("alertMessage", "Mantenimiento no encontrado con ID: " + id);
        model.addAttribute("alertType", "error");
        return "/HTMLMaintenance/updateMaintenance"; 
    }
    
    model.addAttribute("maintenance", maintenance);
    return "/HTMLMaintenance/updateMaintenance";
}

@PostMapping("/updateM")
public String updateMaintenance(@ModelAttribute("maintenance") Maintenance maintenance, 
                              BindingResult result,
                              Model model) {
    
    
    if (result.hasErrors()) {
        model.addAttribute("alertTitle", "Error");
        model.addAttribute("alertMessage", "Datos inválidos en el formulario");
        model.addAttribute("alertType", "error");
        return "/HTMLMaintenance/updateMaintenance";
    }
    
    try {
        
        ms.save(maintenance);
        
       
        model.addAttribute("alertTitle", "¡Éxito!");
        model.addAttribute("alertMessage", "Mantenimiento actualizado correctamente");
        model.addAttribute("alertType", "success");
        
       
        Maintenance updatedMaintenance = ms.getById(maintenance.getId());
        model.addAttribute("maintenance", updatedMaintenance);
        
    } catch (Exception e) {
      
        model.addAttribute("alertTitle", "Error");
        model.addAttribute("alertMessage", "No se pudo actualizar: " + e.getMessage());
        model.addAttribute("alertType", "error");
    }
    
    return "/HTMLMaintenance/updateMaintenance";
}
   
    @GetMapping("/remove")
   public String deleteMaintenance(@RequestParam("id") int id) {
    ms.delete(id); 
    return "redirect:/maintenance/list";
   }
   
   /* @GetMapping("/detalleMantenimiento")
    public String showMaintenanceDetails(@RequestParam("id") int id, Model model) {
       
        Maintenance maintenance = ms.getById(id);
        
      
        if (maintenance == null) {
            throw new IllegalArgumentException("Mantenimiento no encontrado con ID: " + id);
        }
        
        
        model.addAttribute("maintenance", maintenance);
        
       
        return "/HTMLMaintenance/maintenanceDetails"; 
    }*///innecesario ya que en la tabla se muestra toda la informacion
    
    
 @GetMapping("/filterType")
public String filterByType(@RequestParam(required = false) String type, Model model) {
    try {
        List<Maintenance> results = (type == null || type.isEmpty())
                ? ms.getAll()
                : ms.findByType(type);

        model.addAttribute("maintenances", results);
        model.addAttribute("selectedType", type);
        model.addAttribute("types", ms.getAllTypes());

        model.addAttribute("filterMessage",
                (type == null || type.isEmpty())
                        ? "Mostrando todos los mantenimientos"
                        : String.format("Filtrado por tipo: %s (%d resultados)", type, results.size()));

    } catch (Exception e) {
        model.addAttribute("error", "Error al filtrar por tipo: " + e.getMessage());
    }
    return "/HTMLMaintenance/byType";
}


   @GetMapping("/filterState")
public String filterByState(@RequestParam(required = false) String state,
                            Model model) {
    try {
        List<Maintenance> results = (state == null || state.isEmpty())
                ? ms.getAll()
                : ms.findByState(state);

        model.addAttribute("maintenances", results);
        model.addAttribute("selectedState", state);
        model.addAttribute("availableStates", ms.getAllStates());
        model.addAttribute("filterMessage",
                state == null || state.isEmpty()
                        ? "Mostrando todos los mantenimientos"
                        : String.format("Filtrado por estado: %s (%d resultados)", state, results.size()));
    } catch (Exception e) {
        model.addAttribute("error", "Error al filtrar por estado: " + e.getMessage());
    }
    return "/HTMLMaintenance/byState";
}

}


    


    

