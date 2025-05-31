/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author keyna
 */
@Controller
public class RoleRedirectController {
     @GetMapping("/redirect-by-role")
    public String redirectByRole(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "/index";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return "redirect:/employee/detalle";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"))) {
            return "redirect:/client/detalle";
        }
        
        return "redirect:/";
    }
}
