/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

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
    private Integer idPayment;

    @Column(name = "transaction_amount", nullable = false) 
    private int transactionAmount;

    @Column(name = "date_transfer") 
    private LocalDate dateTransfer;

    @Column(name = "method_payment")
    private String methodPayment;

    @Column(name = "number_reference")
    private String numberReference;

    @Column(name = "state_payment") 
    private String statePayment;

    @Column(name = "name_client")
    private String nameClient;

    @Column(name = "identification_fiscal") 
    private String identificationFiscal;

    @Column(name = "direction") 
    private String direction;

    @Column(name = "proof") 
    private String proof;

    public PaymentManagement() {
    }

    public PaymentManagement(int transactionAmount, LocalDate dateTransfer, String methodPayment, String numberReference, String statePayment, String nameClient, String identificationFiscal, String direction, String proof) {
        this.transactionAmount = transactionAmount;
        this.dateTransfer = dateTransfer;
        this.methodPayment = methodPayment;
        this.numberReference = numberReference;
        this.statePayment = statePayment;
        this.nameClient = nameClient;
        this.identificationFiscal = identificationFiscal;
        this.direction = direction;
        this.proof = proof;
    }

    public PaymentManagement(Integer idPayment, int transactionAmount, LocalDate dateTransfer, String methodPayment, String numberReference, String statePayment, String nameClient, String identificationFiscal, String direction, String proof) {
        this.idPayment = idPayment;
        this.transactionAmount = transactionAmount;
        this.dateTransfer = dateTransfer;
        this.methodPayment = methodPayment;
        this.numberReference = numberReference;
        this.statePayment = statePayment;
        this.nameClient = nameClient;
        this.identificationFiscal = identificationFiscal;
        this.direction = direction;
        this.proof = proof;
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(LocalDate dateTransfer) {
        this.dateTransfer = dateTransfer;
    }

    public String getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(String methodPayment) {
        this.methodPayment = methodPayment;
    }

    public String getNumberReference() {
        return numberReference;
    }

    public void setNumberReference(String numberReference) {
        this.numberReference = numberReference;
    }

    public String getStatePayment() {
        return statePayment;
    }

    public void setStatePayment(String statePayment) {
        this.statePayment = statePayment;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getIdentificationFiscal() {
        return identificationFiscal;
    }

    public void setIdentificationFiscal(String identificationFiscal) {
        this.identificationFiscal = identificationFiscal;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    @Override
    public String toString() {
        return "{" +
                "\"idPayment\":\"" + idPayment + "\"," +
                "\"transactionAmount\":" + transactionAmount + "," +
                "\"dateTransfer\":\"" + dateTransfer + "\"," +
                "\"methodPayment\":\"" + methodPayment + "\"," +
                "\"numberReference\":\"" + numberReference + "\"," +
                "\"statePayment\":\"" + statePayment + "\"," +
                "\"nameClient\":\"" + nameClient + "\"," +
                "\"identificationFiscal\":\"" + identificationFiscal + "\"," +
                "\"direction\":\"" + direction + "\"," +
                "\"proof\":\"" + proof + "\"" +
                "}";
    }

}    