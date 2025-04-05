/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.data;

import cr.ac.una.perezoso.domain.Dishe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public class DisheData {
    public LinkedList<Dishe> listDishes() throws SQLException, ClassNotFoundException {
        LinkedList<Dishe> dishes = new LinkedList<>();
        String sql = "SELECT disheID, name, description, price, category, available, image_url, preparation_time FROM tb_dishe";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Dishe dish = new Dishe(
                    rs.getInt("disheID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getBoolean("available"),
                    rs.getString("image_url"),
                    rs.getTime("preparation_time").toLocalTime());                
                dishes.add(dish);                       
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
        
        return dishes;
    }
    
public void insertDish(Dishe dish) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tb_dishe(name, description, price, category, available, image_url, preparation_time) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dish.getName());
            pstmt.setString(2, dish.getDescription());
            pstmt.setDouble(3, dish.getPrice());
            pstmt.setString(4, dish.getCategory());
            pstmt.setBoolean(5, dish.isAvailable());
            pstmt.setString(6, dish.getImageUrl());
            pstmt.setTime(7, Time.valueOf(dish.getPreparationTime()));
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
    }
    
    public void updateDish(Dishe dish) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tb_dishe SET name = ?, description = ?, price = ?, category = ?, "
                   + "available = ?, image_url = ?, preparation_time = ? WHERE disheID = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dish.getName());
            pstmt.setString(2, dish.getDescription());
            pstmt.setDouble(3, dish.getPrice());
            pstmt.setString(4, dish.getCategory());
            pstmt.setBoolean(5, dish.isAvailable());
            pstmt.setString(6, dish.getImageUrl());
            pstmt.setTime(7, Time.valueOf(dish.getPreparationTime()));
            pstmt.setInt(8, dish.getDisheID());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
    }
    
    public void deleteDish(int dishId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tb_dishe WHERE disheID = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, dishId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
    }
    
    public Dishe getDishById(int disheID) throws SQLException, ClassNotFoundException {
        Dishe dish = null;
        String sql = "SELECT disheID, name, description, price, category, available, image_url, preparation_time "
                   + "FROM tb_dishe WHERE disheID = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, disheID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dish = new Dishe(
                        rs.getInt("disheID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getBoolean("available"),
                        rs.getString("image_url"),
                        rs.getTime("preparation_time").toLocalTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
        
        return dish;
    }
    
    public LinkedList<Dishe> searchDishesById(int disheID) throws SQLException, ClassNotFoundException {
        LinkedList<Dishe> dishes = new LinkedList<>();
        String sql = "SELECT disheID, name, description, price, category, available, image_url, preparation_time "
                   + "FROM tb_dishe WHERE disheID LIKE ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + disheID + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Dishe dish = new Dishe(
                        rs.getInt("disheID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getBoolean("available"),
                        rs.getString("image_url"),
                        rs.getTime("preparation_time").toLocalTime()
                    );
                    dishes.add(dish);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
        
        return dishes;
    }
    
    public LinkedList<Dishe> searchDishesByName(String name) throws SQLException, ClassNotFoundException {
        LinkedList<Dishe> dishes = new LinkedList<>();
        String sql = "SELECT disheID, name, description, price, category, available, image_url, preparation_time "
                   + "FROM tb_dishe WHERE name LIKE ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Dishe dish = new Dishe(
                        rs.getInt("disheID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getBoolean("available"),
                        rs.getString("image_url"),
                        rs.getTime("preparation_time").toLocalTime()
                    );
                    dishes.add(dish);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
        
        return dishes;
    }
    
    public LinkedList<Dishe> searchDishesByCategory(String category) throws SQLException, ClassNotFoundException {
        LinkedList<Dishe> dishes = new LinkedList<>();
        String sql = "SELECT disheID, name, description, price, category, available, image_url, preparation_time "
                   + "FROM tb_dishe WHERE category LIKE ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + category + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Dishe dish = new Dishe(
                        rs.getInt("disheID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getBoolean("available"),
                        rs.getString("image_url"),
                        rs.getTime("preparation_time").toLocalTime()
                    );
                    dishes.add(dish);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConectarBD.disconnect();
        }
        
        return dishes;
    }
}