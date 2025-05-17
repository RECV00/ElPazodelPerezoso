/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;
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
@Table(name = "tb_article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private int id_article;
    
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;
    
    @Column(name = "description", length = 500)
    private String description;
    
     @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;
     
     
    @Column(name = "unit_of_measurement", length = 50)
    private String unitOfMeasurement;
    
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(name = "supplier", length = 100)
    private String supplier;
    
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    public Article() {
    }
   
    public Article(int id_article, String productName, String description, int productQuantity, String unitOfMeasurement,
         LocalDate expirationDate, String supplier, int unitPrice) {
        this.id_article= id_article;
        this.productName = productName;
        this.description = description;
        this.productQuantity = productQuantity;
        this.unitOfMeasurement = unitOfMeasurement;
        this.expirationDate = expirationDate;
        this.supplier = supplier;
        this.unitPrice = unitPrice;
    }

    public Article(String productName, String description, int productQuantity, String unitOfMeasurement, LocalDate expirationDate, String supplier, int unitPrice) {
        this.productName = productName;
        this.description = description;
        this.productQuantity = productQuantity;
        this.unitOfMeasurement = unitOfMeasurement;
        this.expirationDate = expirationDate;
        this.supplier = supplier;
        this.unitPrice = unitPrice;
    }

    
    public int getId_article() {
        return id_article;
    }

    public void setId_article(int id_article) {
        this.id_article = id_article;
    }
    
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}

