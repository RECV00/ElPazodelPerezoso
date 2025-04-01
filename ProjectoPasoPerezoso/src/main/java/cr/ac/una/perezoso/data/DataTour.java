/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.data;

/**
 *
 * @author corra
 */
import cr.ac.una.perezoso.domain.Tour;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataTour {
    


    // Método para crear un nuevo tour (Create)
    public static void createTour(Tour tour) {
        String sql = "INSERT INTO tb_tour (nameTour, description, price, date, startTime, duration, startingPoint, multimedia) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tour.getNameTour());
            pstmt.setString(2, tour.getDescription());
            pstmt.setDouble(3, tour.getPrice());
            pstmt.setDate(4, Date.valueOf(tour.getDate())); 
            pstmt.setTime(5, Time.valueOf(tour.getStartTime())); 
            pstmt.setTime(6, Time.valueOf(tour.getDuration())); 
            pstmt.setString(7, tour.getStartingPoint());
            pstmt.setString(8, tour.getMultimedia());
            pstmt.executeUpdate();
            System.out.println("Tour creado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para leer todos los tours (Read)
    public static List<Tour> getTours() {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM tb_tour";
        try (Connection conn = ConectarBD.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tour tour = new Tour();
                tour.setId_Tour(rs.getInt("id_Tour"));
                tour.setNameTour(rs.getString("nameTour"));
                tour.setDescription(rs.getString("description"));
                tour.setPrice(rs.getDouble("price"));
                tour.setDate(rs.getDate("date").toLocalDate()); 
                tour.setStartTime(rs.getTime("startTime").toLocalTime()); 
                tour.setDuration(rs.getTime("duration").toLocalTime());
                tour.setStartingPoint(rs.getString("startingPoint"));
                tour.setMultimedia(rs.getString("multimedia"));
                tours.add(tour);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            
        }
        return tours;
    }
    
  
    // Método para actualizar un tour (Update)
    public static void updateTour(Tour tour) {
        String sql = "UPDATE tb_tour SET nameTour = ?, description = ?, price = ?, date = ?, startTime = ?, " +
                     "duration = ?, startingPoint = ?, multimedia = ? WHERE id_Tour = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tour.getNameTour());
            pstmt.setString(2, tour.getDescription());
            pstmt.setDouble(3, tour.getPrice());
            pstmt.setDate(4, Date.valueOf(tour.getDate())); 
            pstmt.setTime(5, Time.valueOf(tour.getStartTime())); 
            pstmt.setTime(6, Time.valueOf(tour.getDuration())); 
            pstmt.setString(7, tour.getStartingPoint());
            pstmt.setString(8, tour.getMultimedia());
            pstmt.setInt(9, tour.getId_Tour());
            pstmt.executeUpdate();
            System.out.println("Tour actualizado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un tour (Delete)
    public static void deleteTour(int idTour) {
        String sql = "DELETE FROM tb_tour WHERE id_Tour = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idTour);
            pstmt.executeUpdate();
            System.out.println("Tour eliminado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
public static List<Tour> searchToursByName(String nameTour) throws SQLException, ClassNotFoundException {
    List<Tour> toursFiltrados = new ArrayList<>();

    // Consulta SQL para buscar tours por nombre
    String sql = "SELECT * FROM tb_tour WHERE nameTour LIKE ?";
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, "%" + nameTour + "%"); // Usar LIKE para búsqueda parcial
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Tour tour = new Tour();
                tour.setId_Tour(rs.getInt("id_Tour"));
                tour.setNameTour(rs.getString("nameTour"));
                tour.setDescription(rs.getString("description"));
                tour.setPrice(rs.getDouble("price"));
                tour.setDate(rs.getDate("date").toLocalDate());
                tour.setStartTime(rs.getTime("startTime").toLocalTime());
                tour.setDuration(rs.getTime("duration").toLocalTime());
                tour.setStartingPoint(rs.getString("startingPoint"));
                tour.setMultimedia(rs.getString("multimedia"));
                toursFiltrados.add(tour);
            }
        }
    }

    return toursFiltrados;
}

public static Tour getTourById(int idTour) throws SQLException, ClassNotFoundException {
    Tour tour = null;
    String sql = "SELECT * FROM tb_tour WHERE id_Tour = ?";
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, idTour);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                tour = new Tour();
                tour.setId_Tour(rs.getInt("id_Tour"));
                tour.setNameTour(rs.getString("nameTour"));
                tour.setDescription(rs.getString("description"));
                tour.setPrice(rs.getDouble("price"));
                tour.setDate(rs.getDate("date").toLocalDate());
                tour.setStartTime(rs.getTime("startTime").toLocalTime());
                tour.setDuration(rs.getTime("duration").toLocalTime());
                tour.setStartingPoint(rs.getString("startingPoint"));
                tour.setMultimedia(rs.getString("multimedia"));
            }
        }
    }
    return tour;
}
}
