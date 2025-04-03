/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;
import java.time.LocalDate;

/**
 *
 * @author keyna
 */
public class Article {
    private int id_article;
    private String productName;
    private String description;
    private int productQuantity;
    private String unitOfMeasurement;
    private LocalDate expirationDate;
    private String supplier;
    private int unitPrice;

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

