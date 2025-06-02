/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author keyna
 */
@Entity
@Table(name = "tb_booking")
public class Booking {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private int id_booking;
    
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;
    
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;
    
    @Column(name = "number_guest", nullable = false)
    private Integer numberGuests;
    
    @Column(name = "booking_type", nullable = false)
    private String bookingType;
    
    @Column(name = "additional_services", nullable = false)
    private boolean additionalServices;
    
    @Column(name = "special_requirements", length = 500)
    private String specialRequirements;
    
    @Column(name = "promotion_code", length = 20)
    private String promotionCode;
    
    
    @Column(name = "reserve_status", nullable = false, length = 20)
    private String reserveStatus;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", referencedColumnName = "id_client", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cabin")
    private Cabin cabin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tour")
    private Tour tour;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle")
    private Transportation transportation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_food")
    private Food food;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payment", referencedColumnName = "id_payment")
    private PaymentManagement payment;


    public Booking() {
    }

    public Booking(LocalDate checkInDate, LocalDate checkOutDate, int numberGuests, 
            String bookingType, boolean additionalServices, String specialRequirements, 
            String promotionCode, String reserveStatus, Client client, 
            Cabin cabin, Tour tour, Transportation transportation, Food food, 
            PaymentManagement payment) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberGuests = numberGuests;
        this.bookingType = bookingType;
        this.additionalServices = additionalServices;
        this.specialRequirements = specialRequirements;
        this.promotionCode = promotionCode;
        this.reserveStatus = reserveStatus;
        this.client = client;
        this.cabin = cabin;
        this.tour = tour;
        this.transportation = transportation;
        this.food = food;
        this.payment = payment;
    }

    // Getters y setters
    public int getId_booking() {
        return id_booking;
    }

    public void setId_booking(int id_booking) {
        this.id_booking = id_booking;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberGuests() {
        return numberGuests;
    }

    public void setNumberGuests(int numberGuests) {
        this.numberGuests = numberGuests;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public boolean isAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(boolean additionalServices) {
        this.additionalServices = additionalServices;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public PaymentManagement getPayment() {
        return payment;
    }

    public void setPayment(PaymentManagement payment) {
        this.payment = payment;
    }
    
    @Override
    public String toString() {
        return "Booking{" + "id_booking=" + id_booking + ", checkInDate=" + checkInDate 
                + ", checkOutDate=" + checkOutDate + ", numberGuests=" + numberGuests 
                + ", reservationType=" + bookingType + ", additionalServices=" 
                + additionalServices + ", specialRequirements=" + specialRequirements 
                + ", promotionCode=" + promotionCode + ", reservationStatus=" 
                + reserveStatus + '}';
    }
    
   // Método de negocio para validaciones
//    @PrePersist
//    @PreUpdate
//    private void validate() {
//        // Validación de fechas
//        if (checkInDate == null || checkOutDate == null) {
//            throw new IllegalArgumentException("Las fechas de check-in y check-out son obligatorias");
//        }
//        
//        if (checkOutDate.isBefore(checkInDate)) {
//            throw new IllegalArgumentException("La fecha de check-out debe ser posterior a la de check-in");
//        }
//        
//        if (checkInDate.isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException("La fecha de check-in debe ser hoy o en el futuro");
//        }
//        
//        // Validación de huéspedes
//        if (numberGuests < 1) {
//            throw new IllegalArgumentException("Debe haber al menos 1 huésped");
//        }
//        
//        if (numberGuests > 20) {
//            throw new IllegalArgumentException("No puede haber más de 20 huéspedes");
//        }
//        
//        // Validación de campos de texto
//        if (specialRequirements != null && specialRequirements.length() > 500) {
//            throw new IllegalArgumentException("Los requisitos especiales no pueden exceder los 500 caracteres");
//        }
//        
//        if (promotionCode != null && promotionCode.length() > 20) {
//            throw new IllegalArgumentException("El código de promoción no puede exceder los 20 caracteres");
//        }
//        
//        // Validación de relaciones obligatorias
//        if (client == null) {
//            throw new IllegalArgumentException("El cliente es obligatorio");
//        }
//        
//        if (reserveStatus == null) {
//            throw new IllegalArgumentException("El estado de la reservación es obligatorio");
//        }
//    }
}
