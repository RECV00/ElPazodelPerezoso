/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.PaymentManagement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author natal
 */
public interface PaymentsRepository extends JpaRepository<PaymentManagement, Integer> {
    PaymentManagement findTopByOrderByIdPaymentDesc(); // Obtener el último registro por ID en orden descendente
    
}


