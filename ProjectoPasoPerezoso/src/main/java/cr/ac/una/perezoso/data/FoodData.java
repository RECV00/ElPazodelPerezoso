/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.data;

import cr.ac.una.perezoso.domain.Food;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author corra
 */

public class FoodData {
    
    // Método para crear un nuevo registro de comida (Create)
    public static void createFood(Food food) {
        String sql = "INSERT INTO tb_food (selectedMenu, dateService, hourService, numberDishes, customOptions, additionalObservactions, typeService) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, food.getSelectedMenu());
            pstmt.setDate(2, Date.valueOf(food.getDateService()));
            pstmt.setTime(3, Time.valueOf(food.getHourService()));
            pstmt.setInt(4, food.getNumberDishes());
            pstmt.setString(5, food.getCustomOptions());
            pstmt.setString(6, food.getAdditionalObservations());
            pstmt.setString(7, food.getTypeService());
            pstmt.executeUpdate();
            System.out.println("Registro de comida creado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para leer todos los registros de comida (Read)
    public static List<Food> getFoods() {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM tb_food";
        try (Connection conn = ConectarBD.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Food food = new Food();
                food.setId_food(rs.getInt("id_Food"));
                food.setSelectedMenu(rs.getString("selectedMenu"));
                food.setDateService(rs.getDate("dateService").toLocalDate());
                food.setHourService(rs.getTime("hourService").toLocalTime());
                food.setNumberDishes(rs.getInt("numberDishes"));
                food.setCustomOptions(rs.getString("customOptions"));
                food.setAdditionalObservations(rs.getString("additionalObservactions"));
                food.setTypeService(rs.getString("typeService"));
                foods.add(food);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return foods;
    }
    
    // Método para actualizar un registro de comida (Update)
    public static void updateFood(Food food) {
        String sql = "UPDATE tb_food SET selectedMenu = ?, dateService = ?, hourService = ?, numberDishes = ?, " +
                     "customOptions = ?, additionalObservactions = ?, typeService = ? WHERE id_Food = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, food.getSelectedMenu());
            pstmt.setDate(2, Date.valueOf(food.getDateService()));
            pstmt.setTime(3, Time.valueOf(food.getHourService()));
            pstmt.setInt(4, food.getNumberDishes());
            pstmt.setString(5, food.getCustomOptions());
            pstmt.setString(6, food.getAdditionalObservations());
            pstmt.setString(7, food.getTypeService());
            pstmt.setInt(8, food.getId_food());
            pstmt.executeUpdate();
            System.out.println("Registro de comida actualizado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un registro de comida (Delete)
    public static void deleteFood(int idFood) {
        String sql = "DELETE FROM tb_food WHERE id_Food = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFood);
            pstmt.executeUpdate();
            System.out.println("Registro de comida eliminado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Método para buscar registros de comida por menú seleccionado
    public static List<Food> searchFoodsByMenu(String selectedMenu) throws SQLException, ClassNotFoundException {
        List<Food> foodsFiltrados = new ArrayList<>();

        String sql = "SELECT * FROM tb_food WHERE selectedMenu LIKE ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + selectedMenu + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Food food = new Food();
                    food.setId_food(rs.getInt("id_Food"));
                    food.setSelectedMenu(rs.getString("selectedMenu"));
                    food.setDateService(rs.getDate("dateService").toLocalDate());
                    food.setHourService(rs.getTime("hourService").toLocalTime());
                    food.setNumberDishes(rs.getInt("numberDishes"));
                    food.setCustomOptions(rs.getString("customOptions"));
                    food.setAdditionalObservations(rs.getString("additionalObservactions"));
                    food.setTypeService(rs.getString("typeService"));
                    foodsFiltrados.add(food);
                }
            }
        }
        return foodsFiltrados;
    }

    // Método para obtener un registro de comida por su ID
    public static Food getFoodById(int idFood) throws SQLException, ClassNotFoundException {
        Food food = null;
        String sql = "SELECT * FROM tb_food WHERE id_Food = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFood);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    food = new Food();
                    food.setId_food(rs.getInt("id_Food"));
                    food.setSelectedMenu(rs.getString("selectedMenu"));
                    food.setDateService(rs.getDate("dateService").toLocalDate());
                    food.setHourService(rs.getTime("hourService").toLocalTime());
                    food.setNumberDishes(rs.getInt("numberDishes"));
                    food.setCustomOptions(rs.getString("customOptions"));
                    food.setAdditionalObservations(rs.getString("additionalObservactions"));
                    food.setTypeService(rs.getString("typeService"));
                }
            }
        }
        return food;
    }
}
