/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.data;

import cr.ac.una.perezoso.domain.Article;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author keyna
 */
public class ArticleData {
     // Método para obtener todos los artículos
   public static LinkedList<Article> getArticle() throws SQLException, ClassNotFoundException {
    LinkedList<Article> listArticle = new LinkedList<>();
    String sql = "SELECT id_article, product_name, description, " +
                 "product_quantity, unit_of_measurement, " +
                 "IF(expiration_date = '0000-00-00', NULL, expiration_date) AS expiration_date, " +
                 "supplier, unit_price FROM tb_article";
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConectarBD.connect();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        
        while (rs.next()) {
            Article a = new Article();
            a.setId_article(rs.getInt("id_article"));
            a.setProductName(rs.getString("product_name")); 
            a.setDescription(rs.getString("description"));
            a.setProductQuantity(rs.getInt("product_quantity"));
            a.setUnitOfMeasurement(rs.getString("unit_of_measurement"));
            
            // Manejo seguro de la fecha
            java.sql.Date sqlDate = rs.getDate("expiration_date");
            if (sqlDate != null) {
                a.setExpirationDate(sqlDate.toLocalDate());
            } else {
                a.setExpirationDate(null); // O puedes usar una fecha por defecto
            }
            
            a.setSupplier(rs.getString("supplier"));
            a.setUnitPrice(rs.getInt("unit_price"));

            listArticle.add(a);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Relanza la excepción para manejo superior
    } finally {
        
       ConectarBD.disconnect(); // Asumo que disconnect() recibe la conexión
        
    }
    
    return listArticle;
}
    
    // Método para guardar un nuevo artículo
    public static void saveArticle(Article article) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tb_article (product_name, description, product_quantity, unit_of_measurement, NULLIF(expiration_date, '0000-00-00'), supplier, unit_price) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, article.getProductName());
            pstmt.setString(2, article.getDescription());
            pstmt.setInt(3, article.getProductQuantity());
            pstmt.setString(4, article.getUnitOfMeasurement());
            pstmt.setDate(5, Date.valueOf(article.getExpirationDate()));
            pstmt.setString(6, article.getSupplier());
            pstmt.setInt(7, article.getUnitPrice());
            
            pstmt.executeUpdate();
        }catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConectarBD.disconnect();
    }
    }
    
    // Método para eliminar un artículo
    public static void removeArticle(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tb_article WHERE id_article = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    // Método para actualizar un artículo
    public static void updateArticle(Article article) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tb_article SET product_name = ?, description = ?, product_quantity = ?, " +
                     "unit_of_measurement = ?, expiration_date = ?, supplier = ?, unit_price = ? " +
                     "WHERE id_article = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, article.getProductName());
            pstmt.setString(2, article.getDescription());
            pstmt.setInt(3, article.getProductQuantity());
            pstmt.setString(4, article.getUnitOfMeasurement());
            pstmt.setDate(5, Date.valueOf(article.getExpirationDate()));
            pstmt.setString(6, article.getSupplier());
            pstmt.setInt(7, article.getUnitPrice());
            pstmt.setInt(8, article.getId_article());
            
            pstmt.executeUpdate();
        }
    }
    
    // Método para obtener un artículo por ID
    public static Article getArticleById(int id_article) throws SQLException, ClassNotFoundException {
        Article article = null;
        String sql = "SELECT id_article, product_name, description, product_quantity, unit_of_measurement, " +
                     "expiration_date, supplier, unit_price FROM tb_article WHERE id_article = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id_article);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    article = new Article();
                    article.setId_article(rs.getInt("id"));
                    article.setProductName(rs.getString("product_name"));
                    article.setDescription(rs.getString("description"));
                    article.setProductQuantity(rs.getInt("product_quantity"));
                    article.setUnitOfMeasurement(rs.getString("unit_of_measurement"));
                    article.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                    article.setSupplier(rs.getString("supplier"));
                    article.setUnitPrice(rs.getInt("unit_price"));
                }
            }
        }
        return article;
    }
    
    // Método para obtener artículos por proveedor
    public static LinkedList<Article> getArticlesBySupplier(String supplier) throws SQLException, ClassNotFoundException {
        LinkedList<Article> articles = new LinkedList<>();
        String sql = "SELECT id_article, product_name, description, product_quantity, unit_of_measurement, " +
                     "expiration_date, supplier, unit_price FROM tb_article WHERE supplier LIKE ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + supplier + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article();
                    article.setId_article(rs.getInt("id_article"));
                    article.setProductName(rs.getString("product_name"));
                    article.setDescription(rs.getString("description"));
                    article.setProductQuantity(rs.getInt("product_quantity"));
                    article.setUnitOfMeasurement(rs.getString("unit_of_measurement"));
                    article.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                    article.setSupplier(rs.getString("supplier"));
                    article.setUnitPrice(rs.getInt("unit_price"));
                    
                    articles.add(article);
                }
            }
        }
        return articles;
    }
    
    // Método para buscar artículos por nombre
    public static LinkedList<Article> searchArticlesByName(String name) throws SQLException, ClassNotFoundException {
        LinkedList<Article> articles = new LinkedList<>();
        String sql = "SELECT id_article, product_name, description, product_quantity, unit_of_measurement, " +
                     "expiration_date, supplier, unit_price FROM tb_article WHERE product_name LIKE ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article();
                    article.setId_article(rs.getInt("id_article"));
                    article.setProductName(rs.getString("product_name"));
                    article.setDescription(rs.getString("description"));
                    article.setProductQuantity(rs.getInt("product_quantity"));
                    article.setUnitOfMeasurement(rs.getString("unit_of_measurement"));
                    article.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
                    article.setSupplier(rs.getString("supplier"));
                    article.setUnitPrice(rs.getInt("unit_price"));
                    
                    articles.add(article);
                }
            }
        }
        return articles;
    }
}

