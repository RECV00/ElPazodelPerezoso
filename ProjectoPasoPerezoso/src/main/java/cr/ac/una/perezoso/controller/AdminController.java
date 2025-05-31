/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Admin;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.Employee;
import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
   
//       // Vista principal con lista de usuarios
    @GetMapping("/list")
    public String listUsers(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "4") int size ) {
        
        Page<User> userPage = userService.getAll(PageRequest.of(page, size));
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        
       
        return "/list_user";
    }
    
    
// API para AJAX
    @GetMapping("/api/list")
    @ResponseBody
    public Page<User> listUsersApi(
            @RequestParam(value = "identification", required = false) String identification,
            @RequestParam(value = "type", required = false) String userType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        
        if (identification != null && !identification.isEmpty()) {
            if (userType != null && !userType.isEmpty()) {
                return userService.findByIdentificationContainingAndUserType(
                    identification, userType, PageRequest.of(page, size));
            }
            return userService.findByIdentificationContaining(
                identification, PageRequest.of(page, size));
        } else if (userType != null && !userType.isEmpty()) {
            return userService.findByUserType(userType, PageRequest.of(page, size));
        }
        return userService.getAll(PageRequest.of(page, size));
    }
    

    
    
    @PostMapping("/admins")
    public Admin saveAdmin(@RequestBody Admin admin) {
        return userService.saveAdmin(admin);
    }

    @GetMapping("/admins")
    public List<Admin> getAllAdmins() {
        return userService.getAllAdmins();
    }
    
}
