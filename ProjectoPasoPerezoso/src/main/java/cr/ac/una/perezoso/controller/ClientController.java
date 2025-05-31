/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Booking;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/client")
public class ClientController {
   @Autowired
    private UserService userService;
    
    @GetMapping("/listUser")
    public String listadoUsuarios(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("userTypes", List.of("ADMIN", "EMPLOYEE", "CLIENT")); // Para el select de tipos
        return "/list_user";
    }
    
     @GetMapping("/detalle")
    public String clientDetail(Model model, Authentication authentication) {
        try {
            // Obtener el cliente autenticado
            String username = authentication.getName();
            Client client = userService.getClientByIdentification(username);            
            // Obtener las reservas del cliente
            List<Booking> bookings = userService.getClientBookings(client.getId_user());           
            model.addAttribute("user", client);
            model.addAttribute("bookings", bookings);
           
            return "/client/client_detalle";
            
        } catch (RuntimeException e) {
            model.addAttribute("error", "Error al cargar los datos: " + e.getMessage());
            return "error";
        }
    } 
}
