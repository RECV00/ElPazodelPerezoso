/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.data;

import cr.ac.una.perezoso.domain.Cabin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public class CabinData {
    
   public LinkedList<Cabin> listCabins() throws SQLException, ClassNotFoundException {
    LinkedList<Cabin> cabins = new LinkedList<>();
    String sql = "SELECT cabinID, name, description, capacity, pricePerNight, location, image, includedServices FROM tb_cabin";
    
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            // Crear un objeto Cabin con los datos de la fila actual
            Cabin cabin = new Cabin(
                rs.getInt("cabinID"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("capacity"),
                rs.getDouble("pricePerNight"),
                rs.getString("location"),
                rs.getString("image"),
                rs.getString("includedServices")
            );
            
            // Agregar el objeto Cabin a la lista
            cabins.add(cabin);
            
            // Imprimir los datos para depuración
            System.out.println("CabinID: " + rs.getInt("cabinID"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("Capacity: " + rs.getInt("capacity"));
            System.out.println("PricePerNight: " + rs.getDouble("pricePerNight"));
            System.out.println("Location: " + rs.getString("location"));
            System.out.println("Image: " + rs.getString("image"));
            System.out.println("IncludedServices: " + rs.getString("includedServices"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConectarBD.disconnect(); // Cierra la conexión
    }
    
    return cabins;
}
    
   public void insertCabin(Cabin cabin) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO tb_cabin(name, description, capacity, pricePerNight, location, image, includedServices) VALUES(?,?,?,?,?,?,?)";
    
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
        pstmt.setString(1, cabin.getName());
        pstmt.setString(2, cabin.getDescription());
        pstmt.setInt(3, cabin.getCapacity());
        pstmt.setDouble(4, cabin.getPricePerNight());
        pstmt.setString(5, cabin.getLocation());
        pstmt.setString(6, cabin.getImage());
        pstmt.setString(7, cabin.getIncludedServices());
        
        // Ejecutar la inserción
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConectarBD.disconnect();
    }
}
    
    public void updateCabin(Cabin cabin) throws SQLException, ClassNotFoundException {
     String sql = "UPDATE tb_cabin SET name = ?, description = ?, capacity = ?, pricePerNight = ?, location = ?, image = ?, includedServices = ? WHERE cabinID = ?";
    try (Connection conn = ConectarBD.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cabin.getName());
        stmt.setString(2, cabin.getDescription());
        stmt.setInt(3, cabin.getCapacity());
        stmt.setDouble(4, cabin.getPricePerNight());
        stmt.setString(5, cabin.getLocation());
        stmt.setString(6, cabin.getImage());
        stmt.setString(7, cabin.getIncludedServices());
        stmt.setInt(8, cabin.getCabinID());
        
        // Ejecutar la actualización
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void deleteCabin(int cabinID) throws SQLException, ClassNotFoundException {
    String query = "DELETE FROM tb_cabin WHERE cabinID = ?";
    try (Connection conn = ConectarBD.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, cabinID);  // Establece el ID de la cabaña a eliminar
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public Cabin getCabinByID(int cabinID) throws SQLException, ClassNotFoundException {
    Cabin cabin = null;
    String sql = "SELECT cabinID, name, description, capacity, pricePerNight, location, image, includedServices FROM tb_cabin WHERE cabinID = ?";
    
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, cabinID);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                cabin = new Cabin(
                    rs.getInt("cabinID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("capacity"),
                    rs.getDouble("pricePerNight"),
                    rs.getString("location"),
                    rs.getString("image"),
                    rs.getString("includedServices")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConectarBD.disconnect(); // Cierra la conexión después de usarla
    }
    return cabin;
}
   public LinkedList<Cabin> searchCabinsByID(int cabinID) throws SQLException, ClassNotFoundException {
    LinkedList<Cabin> cabins = new LinkedList<>();
    String sql = "SELECT cabinID, name, description, capacity, pricePerNight, location, image, includedServices FROM tb_cabin WHERE cabinID LIKE ?";
    
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, "%" + cabinID + "%"); // Búsqueda parcial
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cabin cabin = new Cabin(
                    rs.getInt("cabinID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("capacity"),
                    rs.getDouble("pricePerNight"),
                    rs.getString("location"),
                    rs.getString("image"),
                    rs.getString("includedServices")
                );
                cabins.add(cabin);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConectarBD.disconnect(); // Cierra la conexión
    }
       return cabins;
    
   }
 public LinkedList<Cabin> searchCabinsByName(String name) throws SQLException, ClassNotFoundException {
    LinkedList<Cabin> cabins = new LinkedList<>();
    String sql = "SELECT cabinID, name, description, capacity, pricePerNight, location, image, includedServices FROM tb_cabin WHERE name LIKE ?";
    
    try (Connection conn = ConectarBD.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, "%" + name + "%"); // Búsqueda parcial
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cabin cabin = new Cabin(
                    rs.getInt("cabinID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("capacity"),
                    rs.getDouble("pricePerNight"),
                    rs.getString("location"),
                    rs.getString("image"),
                    rs.getString("includedServices")
                );
                cabins.add(cabin);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConectarBD.disconnect(); // Cierra la conexión
    }
    return cabins;
}
}
