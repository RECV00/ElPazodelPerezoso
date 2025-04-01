/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.ProjectSlothsStep.data;

/**
 *
 * @author corra
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {

    private final static String DATABASE_NAME = "bdslothsstep";
    private final static String USER = "root";
    private final static String PASS = "";
    private final static int PORT = 3306;
    private final static String HOST = "localhost";
    private final static String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME;

    private static Connection conexion;

    // Constructor privado para evitar instanciación
    private ConectarBD() {}

    // Método para obtener la conexión (Singleton)
    public static Connection conectar() throws SQLException, ClassNotFoundException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver
                conexion = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Conexión establecida con la base de datos.");
            } catch (ClassNotFoundException e) {
                System.err.println("Error: No se encontró el driver de MySQL.");
                throw e;
            } catch (SQLException e) {
                System.err.println("Error: No se pudo establecer la conexión con la base de datos.");
                throw e;
            }
        }
        return conexion;
    }

    // Método para cerrar la conexión
public static void cerrarConexion() {
   if (conexion != null) {
        try {
             conexion.close();
            System.out.println("Conexión cerrada.");
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
         }
     }
   }
}

