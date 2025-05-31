/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.jpa.UserRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
public class AuthService {
     @Autowired
    private UserRepository userRepository;
     
     @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String identification, String plainPassword) throws AuthException {
        // 1. Buscar usuario (case-insensitive)
        User user = userRepository.findByIdentificationIgnoreCase(identification)
            .orElseThrow(() -> new AuthException("Usuario no encontrado"));
        
        // 2. Verificar contraseña
        if (!passwordEncoder.matches(plainPassword, user.getPassword())) {
            throw new AuthException("Contraseña incorrecta");
        }
        
        return user;
    }

}
