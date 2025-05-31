/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author keyna
 */
@Entity
@Table(name = "tb_payment_management")

public class PaymentManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private int idPayment;
    
    @Column(name = "transaction_amount", nullable = false)
    private double transactionAmount;
    
    @Column(name = "date_transaction", nullable = false)
    private LocalDate dateTransaction;
    
    @Column(name = "reference_number", nullable = false, length = 50)
    private String referenceNumber;
    
    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;
    
    @Column(name = "billing_data", columnDefinition = "TEXT")
    private String billingData;
    
    @Column(name = "voucher", length = 100)
    private String voucher;

    // Constructores
    public PaymentManagement() {
    }

    public PaymentManagement(double transactionAmount, LocalDate dateTransaction, 
                           String referenceNumber, String paymentStatus, 
                           String billingData, String voucher) {
        this.transactionAmount = transactionAmount;
        this.dateTransaction = dateTransaction;
        this.referenceNumber = referenceNumber;
        this.paymentStatus = paymentStatus;
        this.billingData = billingData;
        this.voucher = voucher;
    }

    // Getters y Setters
    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBillingData() {
        return billingData;
    }

    public void setBillingData(String billingData) {
        this.billingData = billingData;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

   
    @Override
    public String toString() {
        return "PaymentManagement{" +
               "idPayment=" + idPayment +
               ", transactionAmount=" + transactionAmount +
               ", dateTransaction=" + dateTransaction +
               ", referenceNumber='" + referenceNumber + '\'' +
               ", paymentStatus='" + paymentStatus + '\'' +
               ", billingData='" + billingData + '\'' +
               ", voucher='" + voucher + '\'' +
               '}';
    }
    
}
