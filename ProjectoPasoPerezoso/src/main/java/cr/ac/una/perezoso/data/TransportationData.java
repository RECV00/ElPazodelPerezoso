package cr.ac.una.perezoso.data;

import cr.ac.una.perezoso.domain.Transportation;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportationData {

    // Método para crear un nuevo transporte (Create)
    public static void createTransportation(Transportation transportation) {
        String sql = "INSERT INTO tb_vehicle (id_vehicle, driver, dataTimeService, initialLocation, finalLocation, serviceStatus, serviceDuration) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, transportation.getPlate());
            pstmt.setString(2, transportation.getDriver());
            pstmt.setTimestamp(3, Timestamp.valueOf(transportation.getDataTimeService()));
            pstmt.setString(4, transportation.getInitialLocation());
            pstmt.setString(5, transportation.getFinalLocation());
            pstmt.setString(6, transportation.getServiceStatus());
            pstmt.setInt(7, transportation.getServiceDuration());
            
            pstmt.executeUpdate();
            
            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transportation.setId_transportation(generatedKeys.getInt(1));
                }
            }
            
            System.out.println("Transporte creado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para leer todos los transportes (Read)
    public static List<Transportation> getTransportations() {
        List<Transportation> transportations = new ArrayList<>();
        String sql = "SELECT * FROM tb_vehicle";
        try (Connection conn = ConectarBD.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Transportation transportation = new Transportation();
                transportation.setId_transportation(rs.getInt("id"));
                transportation.setPlate(rs.getString("id_vehicle"));
                transportation.setDriver(rs.getString("driver"));
                transportation.setDataTimeService(rs.getTimestamp("dataTimeService").toLocalDateTime());
                transportation.setInitialLocation(rs.getString("initialLocation"));
                transportation.setFinalLocation(rs.getString("finalLocation"));
                transportation.setServiceStatus(rs.getString("serviceStatus"));
                transportation.setServiceDuration(rs.getInt("serviceDuration"));
                
                transportations.add(transportation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transportations;
    }
  
    // Método para actualizar un transporte (Update)
    public static void updateTransportation(Transportation transportation) {
        String sql = "UPDATE tb_vehicle SET id_vehicle = ?, driver = ?, dataTimeService = ?, " +
                     "initialLocation = ?, finalLocation = ?, serviceStatus = ?, serviceDuration = ? " +
                     "WHERE id = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, transportation.getPlate());
            pstmt.setString(2, transportation.getDriver());
            pstmt.setTimestamp(3, Timestamp.valueOf(transportation.getDataTimeService()));
            pstmt.setString(4, transportation.getInitialLocation());
            pstmt.setString(5, transportation.getFinalLocation());
            pstmt.setString(6, transportation.getServiceStatus());
            pstmt.setInt(7, transportation.getServiceDuration());
            pstmt.setInt(8, transportation.getId_transportation());
            
            pstmt.executeUpdate();
            System.out.println("Transporte actualizado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un transporte (Delete)
    public static void deleteTransportation(int id) {
        String sql = "DELETE FROM tb_vehicle WHERE id = ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Transporte eliminado exitosamente.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Método para buscar transportes por conductor o ID de vehículo
    public static List<Transportation> searchTransportations(String filter) throws SQLException, ClassNotFoundException {
        List<Transportation> filteredTransportations = new ArrayList<>();

        String sql = "SELECT * FROM tb_vehicle WHERE driver LIKE ? OR id_vehicle LIKE ?";
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + filter + "%");
            pstmt.setString(2, "%" + filter + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transportation transportation = new Transportation();
                    transportation.setId_transportation(rs.getInt("id"));
                    transportation.setPlate(rs.getString("id_vehicle"));
                    transportation.setDriver(rs.getString("driver"));
                    transportation.setDataTimeService(rs.getTimestamp("dataTimeService").toLocalDateTime());
                    transportation.setInitialLocation(rs.getString("initialLocation"));
                    transportation.setFinalLocation(rs.getString("finalLocation"));
                    transportation.setServiceStatus(rs.getString("serviceStatus"));
                    transportation.setServiceDuration(rs.getInt("serviceDuration"));
                    
                    filteredTransportations.add(transportation);
                }
            }
        }

        return filteredTransportations;
    }

    // Método para obtener un transporte por su ID
    public static Transportation getTransportationById(int id) throws SQLException, ClassNotFoundException {
        Transportation transportation = null;
        String sql = "SELECT * FROM tb_vehicle WHERE id = ?";
        
        try (Connection conn = ConectarBD.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    transportation = new Transportation();
                    transportation.setId_transportation(rs.getInt("id"));
                    transportation.setPlate(rs.getString("id_vehicle"));
                    transportation.setDriver(rs.getString("driver"));
                    transportation.setDataTimeService(rs.getTimestamp("dataTimeService").toLocalDateTime());
                    transportation.setInitialLocation(rs.getString("initialLocation"));
                    transportation.setFinalLocation(rs.getString("finalLocation"));
                    transportation.setServiceStatus(rs.getString("serviceStatus"));
                    transportation.setServiceDuration(rs.getInt("serviceDuration"));
                }
            }
        }
        return transportation;
    }
}