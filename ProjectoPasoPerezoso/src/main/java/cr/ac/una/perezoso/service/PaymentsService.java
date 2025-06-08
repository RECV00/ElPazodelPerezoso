/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;
import java.util.List;
import cr.ac.una.perezoso.domain.PaymentManagement;
import cr.ac.una.perezoso.jpa.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
/**
 *
 * @author natal
 */
@Service
public class PaymentsService implements CRUD<PaymentManagement, Integer>{
    @Autowired
    private PaymentsRepository repoPayments;
    @Override
    public void save(PaymentManagement p){
        repoPayments.save(p);

    }
    @Override
    public void delete(Integer id){
        repoPayments.deleteById(id);
    }
    @Override
    public List<PaymentManagement>getAll(){
        return repoPayments.findAll();
    }
    @Override
    public PaymentManagement getById(Integer id){
        return repoPayments.findById(id).orElse(null);
    }
    
    
    public void delete(PaymentManagement p){
        repoPayments.delete(p);
    }
    
    public String getLastReferenceNumber() {
        PaymentManagement lastPayment = repoPayments.findTopByOrderByIdPaymentDesc();
        return lastPayment != null ? lastPayment.getNumberReference() : null;
    }
    
    @Override
    public Page<PaymentManagement> getAll(Pageable pageable) {
        return repoPayments.findAll(pageable);
    }
    
    @Override
    public boolean existsById(Integer id) {
        return repoPayments.existsById(id);
    }
}