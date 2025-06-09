/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;

import cr.ac.una.perezoso.domain.PaymentManagement;
import cr.ac.una.perezoso.service.PaymentsService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author natal
 */
@Controller
@RequestMapping("/payment")
public class PaymentsController {

    @GetMapping("/payments")
    public String paymentsPage() {
        return "/payments/payments";
    }

    @GetMapping("/list")
    public String listPaymentsPage() {
        return "/payments/ListPayments";
    }
    
    @Autowired
    private PaymentsService paymentsService;
    
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> savePayment(@RequestBody PaymentManagement payment) {
        try {
            // Asigna la fecha actual si no se proporciona
            if (payment.getDateTransfer() == null) {
                payment.setDateTransfer(LocalDate.now());
            }

            // Generar el número de referencia de forma automática
            String lastReference = paymentsService.getLastReferenceNumber();
            String newReference = generateNextReference(lastReference);
            payment.setNumberReference(newReference);

            paymentsService.save(payment);
            return ResponseEntity.ok(Map.of("message", "Pago guardado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error al guardar el pago: " + e.getMessage()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Obtener el nombre del archivo
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "uploads";
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Verificar si el archivo ya existe
            Path filePath = uploadPath.resolve(fileName);
            if (Files.exists(filePath)) {
                // Si el archivo existe, agregar un timestamp al nombre
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                String extension = fileName.substring(fileName.lastIndexOf('.'));
                fileName = baseName + "_" + System.currentTimeMillis() + extension;
                filePath = uploadPath.resolve(fileName);
            }

            // Guardar el archivo
            Files.copy(file.getInputStream(), filePath);

            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al subir el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/nextReference")
    public ResponseEntity<String> getNextReference() {
        String lastReference = paymentsService.getLastReferenceNumber();
        String newReference = generateNextReference(lastReference);
        return ResponseEntity.ok(newReference);
    }
    
    // Método para generar el siguiente número de referencia
    private String generateNextReference(String lastReference) {
        if (lastReference == null || lastReference.isEmpty()) {
            return "REF001"; // Si no hay registros previos, inicia con REF001
        }
    
        // Extraer el número de la referencia y aumentar en 1
        int lastNumber = Integer.parseInt(lastReference.replace("REF", ""));
        int nextNumber = lastNumber + 1;
    
        // Formatear el nuevo número con ceros a la izquierda
        return String.format("REF%03d", nextNumber);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentManagement>> getAllPayments() {
        List<PaymentManagement> payments = paymentsService.getAll();
        return ResponseEntity.ok(payments);
    }
    @GetMapping("/initialData")
    public ResponseEntity<Map<String, String>> getInitialPaymentData() {
        Map<String, String> initialData = new HashMap<>();
        initialData.put("dateTransfer", LocalDate.now().toString());
        initialData.put("numberReference", generateNextReference(paymentsService.getLastReferenceNumber()));
        return ResponseEntity.ok(initialData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Integer id) {
        try {
            PaymentManagement payment = paymentsService.getById(id);
            if (payment == null) {
                return ResponseEntity.notFound().build();
            }
            if (payment.getProof() != null && !payment.getProof().isEmpty()) {
                try {
                    ClassPathResource resource = new ClassPathResource("static/uploads");
                    Path filePath = Paths.get(resource.getURI()).resolve(payment.getProof());
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                    }
                } catch (IOException e) {
                    // Si hay error al eliminar el archivo
                    e.printStackTrace();
                }
            }

            paymentsService.delete(id);
            return ResponseEntity.ok("Pago eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el pago: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentManagement> getPayment(@PathVariable Integer id) {
        PaymentManagement payment = paymentsService.getById(id);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable Integer id, @RequestBody PaymentManagement payment) {
        try {
            PaymentManagement existingPayment = paymentsService.getById(id);
            if (existingPayment == null) {
                return ResponseEntity.notFound().build();
            }

            // Solo actualizar los campos permitidos
            existingPayment.setTransactionAmount(payment.getTransactionAmount());
            existingPayment.setMethodPayment(payment.getMethodPayment());
            existingPayment.setStatePayment(payment.getStatePayment());
            existingPayment.setProof(payment.getProof());

            paymentsService.save(existingPayment);
            return ResponseEntity.ok("Pago actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el pago: " + e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchPaymentById(@PathVariable String id) {
        try {
            Integer paymentId = Integer.parseInt(id);
            PaymentManagement payment = paymentsService.getById(paymentId);
            if (payment == null) {
                return ResponseEntity.ok("No se encontró ningún pago con la cédula: " + id);
            }
            return ResponseEntity.ok(payment);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El ID proporcionado no es válido");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar el pago: " + e.getMessage());
        }
    }
}

