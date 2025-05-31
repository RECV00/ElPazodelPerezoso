/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author keyna
 */
public class DebugController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-user/{id}")
    public ResponseEntity<?> checkUser(@PathVariable String id) {
        return ResponseEntity.ok(
            userRepository.findByIdentification(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
        );
    }
}
