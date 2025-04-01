/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.ProjectSlothsStep.data;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
/**
 *
 * @author corra
 */
public class FileUploadUtil {
    
    private static final String UPLOAD_DIR = "src/main/resources/static/img/";

    public static String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        // Generar un hash corto
        int randomHash = new Random().nextInt(0xFFFF); // Número aleatorio de 16 bits
        String fileName = Integer.toHexString(randomHash) + "_" + file.getOriginalFilename();
        // Crear la ruta completa del archivo
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Guardar el archivo
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        // Devolver la ruta relativa (sin "src/main/resources/static")
        return "img/" + fileName;
    }
} 
