/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Employee;
import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.service.UserService;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/detalle")
public String employeeDetail(Model model, Authentication authentication) {
    try {
        // 1. Obtener el empleado autenticado
        String username = authentication.getName();
        User user = userService.findByIdentification(username);
        
        // 2. Verificar que es un empleado
        if (!(user instanceof Employee)) {
            throw new RuntimeException("El usuario no es un empleado");
        }
     
        Employee employee = (Employee) user;
        // 3. Agregar datos al modelo
        model.addAttribute("employee", employee);
        
        return "/employee/employee_detalle";
        
    } catch (RuntimeException e) {
        model.addAttribute("error", "Error al cargar los datos del empleado: " + e.getMessage());
        return "error";
    }
}
}
