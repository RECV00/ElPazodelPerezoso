/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identification) throws UsernameNotFoundException {
        User user = userRepository.findByIdentification(identification)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + identification));
        
         // Convertir userType a formato de roles de Spring Security
        String role = "ROLE_" + user.getUserType().toUpperCase();
        
        // Verificación adicional para desarrollo
        System.out.println("=== DEBUG AUTENTICACIÓN ===");
        System.out.println("Usuario: " + user.getIdentification());
        System.out.println("Hash en BD: " + user.getPassword());
        System.out.println("Role: " + user.getUserType());
        
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getIdentification())
            .password(user.getPassword())
            .roles(user.getUserType())
            .build();
    }
}
