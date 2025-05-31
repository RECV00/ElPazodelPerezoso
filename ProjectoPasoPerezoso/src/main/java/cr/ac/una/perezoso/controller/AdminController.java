/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.Admin;
import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
   
      @GetMapping("/list")
    public String listUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size,
            @RequestParam(value = "identification", required = false) String identification,
            @RequestParam(value = "type", required = false) String userType,
            HttpServletRequest request,
            Model model) {
        
        Page<User> userPage;
        
        // Si no hay filtros, usar getAll()
        if ((identification == null || identification.isEmpty()) && 
            (userType == null || userType.isEmpty())) {
            userPage = userService.getAll(PageRequest.of(page, size));
        } else {
            // Si hay filtros, usar filterAndPaginateUsers()
            userPage = userService.filterAndPaginateUsers(identification, userType, page, size);
        }
        
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("identificationFilter", identification);
        model.addAttribute("typeFilter", userType);
        model.addAttribute("size", size);
        
        if (isAjaxRequest(request)) {
            return "/list_user :: #usersTableBody";
        }
        return "/list_user";
    }

private boolean isAjaxRequest(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
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
