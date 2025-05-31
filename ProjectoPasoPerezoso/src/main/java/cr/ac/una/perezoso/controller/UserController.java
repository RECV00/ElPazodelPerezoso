/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;
import cr.ac.una.perezoso.data.FileUploadUtil;
import cr.ac.una.perezoso.domain.Admin;
import cr.ac.una.perezoso.domain.Client;
import cr.ac.una.perezoso.domain.Employee;
import cr.ac.una.perezoso.domain.User;
import cr.ac.una.perezoso.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
     @Autowired
    private UserService userService;
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private PasswordEncoder passwordEncoder;
  
     // Método para mostrar formulario de creación (compatible con AJAX)
    @GetMapping("/formulario")
    public String formularioUsuario(Model model, HttpServletRequest request) {
        model.addAttribute("userType", new String[]{"Admin", "Empleado", "Cliente"});
        
        if (isAjaxRequest(request)) {
            return "/form_user :: #formContent"; // Solo el contenido del formulario para AJAX
        }
        return "/form_user"; // Página completa para solicitudes normales
    }

 // Método para procesar creación de usuario (compatible con AJAX)
    @PostMapping("/guardar")
    public String guardarUsuario(
            @RequestParam("tipoUsuario") String tipoUsuario,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("cedula") String cedula,
            @RequestParam("fechaNacimiento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam("email") String email,
            @RequestParam("telefono") int telefono,
            @RequestParam("direccion") String direccion,
            @RequestParam("password") String password,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "puesto", required = false) String puesto,
            @RequestParam(value = "numeroCuenta", required = false) String numeroCuenta,
            @RequestParam(value = "salario", required = false) Double salario,
            HttpServletRequest request,
            Model model) throws IOException {

        try {
            String imagePath = imageFile != null && !imageFile.isEmpty() 
                ? FileUploadUtil.saveFile(imageFile) 
                : null;
            
            User usuario = crearUsuarioSegunTipo(tipoUsuario, nombre, apellido, cedula, fechaNacimiento, 
                                              email, telefono, direccion, password, imagePath, 
                                              puesto, numeroCuenta, salario);
            
            userService.save(usuario);
            
            if (isAjaxRequest(request)) {
                return "redirect:/admin/list?success=Usuario creado correctamente";
            }
            return "redirect:/admin/list?success=Usuario creado correctamente";
            
        } catch (Exception e) {
            logger.error("Error al guardar usuario", e);
            model.addAttribute("error", "Error al guardar usuario: " + e.getMessage());
            repoblarModelo(model, tipoUsuario, nombre, apellido, cedula, fechaNacimiento, 
                          email, telefono, direccion, puesto, numeroCuenta, salario);
            
            if (isAjaxRequest(request)) {
                return "/form_user :: #formContent";
            }
            return "/form_user";
        }
    }
    
    // Método para mostrar formulario de edición (compatible con AJAX)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable int id, Model model, HttpServletRequest request) {
        try {
            User usuario = userService.getById(id);
            if (usuario == null) {
                return "redirect:/admin/list";
            }

            model.addAttribute("user", usuario);
            model.addAttribute("tiposUsuario", new String[]{"Admin", "Empleado", "Cliente"});

            if (usuario instanceof Employee) {
                Employee empleado = (Employee) usuario;
                model.addAttribute("puesto", empleado.getWorkstation());
                model.addAttribute("numeroCuenta", empleado.getAccount_number());
                model.addAttribute("salario", empleado.getSalary());
            }

            if (isAjaxRequest(request)) {
                return "/form_user_edit :: #formContent";
            }
            return "/form_user_edit";
            
        } catch (Exception e) {
            logger.error("Error al cargar formulario de edición", e);
            return "redirect:/admin/list";
        }
    }

   // Método para procesar actualización (compatible con AJAX)
    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(
            @PathVariable int id,
            @RequestParam("tipoUsuario") String tipoUsuario,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("cedula") String cedula,
            @RequestParam("fechaNacimiento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam("email") String email,
            @RequestParam("telefono") int telefono,
            @RequestParam("direccion") String direccion,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "puesto", required = false) String puesto,
            @RequestParam(value = "numeroCuenta", required = false) String numeroCuenta,
            @RequestParam(value = "salario", required = false) Double salario,
            HttpServletRequest request,
            Model model) throws IOException {

        try {
            User usuario = userService.getById(id);
            if (usuario == null) {
                return "redirect:/admin/list";
            }

            actualizarDatosUsuario(usuario, nombre, apellido, cedula, fechaNacimiento, 
                                 email, telefono, direccion, tipoUsuario, 
                                 password, imageFile);

            if (usuario instanceof Employee && tipoUsuario.equalsIgnoreCase("Empleado")) {
                actualizarDatosEmpleado((Employee) usuario, puesto, numeroCuenta, salario);
            }

            userService.save(usuario);
            
            if (isAjaxRequest(request)) {
                return "redirect:/admin/list?success=Usuario actualizado correctamente";
            }
            return "redirect:/admin/list?success=Usuario actualizado correctamente";
            
        } catch (Exception e) {
            logger.error("Error al actualizar usuario", e);
            model.addAttribute("error", "Error al actualizar usuario: " + e.getMessage());
            model.addAttribute("tiposUsuario", new String[]{"Admin", "Empleado", "Cliente"});
            repoblarModeloEdicion(model, id, nombre, apellido, cedula, fechaNacimiento, 
                                email, telefono, direccion, tipoUsuario, 
                                puesto, numeroCuenta, salario);
            
            if (isAjaxRequest(request)) {
                return "/form_user_edit :: #formContent";
            }
            return "/form_user_edit";
        }
    }
@GetMapping("/detalle/{id}")
public String detalleUsuario(@PathVariable int id, Model model) {
    try {
        if (id <= 0) {
            return "redirect:/admin/list?error=ID inválido";
        }
        
        User usuario = userService.getById(id);
        
        if (usuario == null) {
            return "redirect:/admin/list?error=Usuario no encontrado";
        }
        
        model.addAttribute("user", usuario);
        
        // Determinar tipo de usuario de manera segura
        String userType = "Desconocido";
        if (usuario instanceof Admin) {
            userType = "ADMIN";
        } else if (usuario instanceof Employee) {
            userType = "EMPLOYEE";
        } else if (usuario instanceof Client) {
            userType = "CLIENT";
        }
        model.addAttribute("userType", userType);
        
        return "/detalle_user";
        
    } catch (Exception e) {
        logger.error("Error al mostrar detalle del usuario ID: " + id, e);
        return "redirect:/admin/list?error=Error al cargar el usuario";
    }
}
    
    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            // Loggear el error
        }
        return "redirect:/admin/list";
    }

    // Métodos  privados
    private User crearUsuarioSegunTipo(String tipo, String nombre, String apellido, String cedula, 
                                     LocalDate fechaNacimiento, String email, int telefono, 
                                     String direccion, String password, String imagePath,
                                     String puesto, String numeroCuenta, Double salario) {
        return switch (tipo) {
            case "Admin" -> new Admin(nombre, apellido, cedula, fechaNacimiento, email, 
                                    telefono, direccion, password, imagePath);
            case "Empleado" -> {
                Employee empleado = new Employee(nombre, apellido, cedula, fechaNacimiento, email, 
                                               telefono, direccion, password, imagePath);
                empleado.setWorkstation(puesto);
                empleado.setAccount_number(numeroCuenta);
                empleado.setSalary(salario);
                yield empleado;
            }
            case "Cliente" -> new Client(nombre, apellido, cedula, fechaNacimiento, email, 
                                       telefono, direccion, password, imagePath);
            default -> throw new IllegalArgumentException("Tipo de usuario no válido");
        };
    }

    private void actualizarDatosUsuario(User usuario, String nombre, String apellido, String cedula, 
                                      LocalDate fechaNacimiento, String email, int telefono, 
                                      String direccion, String tipoUsuario, 
                                      String password, MultipartFile imageFile) throws IOException {
        usuario.setName(nombre);
        usuario.setLast_name(apellido);
        usuario.setIdentification(cedula);
        usuario.setBirthdate(fechaNacimiento);
        usuario.setEmail(email);
        usuario.setPhone(telefono);
        usuario.setAddress(direccion);
        usuario.setUserType(tipoUsuario);
        
        if (password != null && !password.isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = FileUploadUtil.saveFile(imageFile);
            usuario.setProfile_picture(imagePath);
        }
    }

    private void actualizarDatosEmpleado(Employee empleado, String puesto, String numeroCuenta, Double salario) {
        empleado.setWorkstation(puesto);
        empleado.setAccount_number(numeroCuenta);
        empleado.setSalary(salario);
    }

    private void repoblarModelo(Model model, String tipoUsuario, String nombre, String apellido, 
                              String cedula, LocalDate fechaNacimiento, String email, 
                              int telefono, String direccion, String puesto, 
                              String numeroCuenta, Double salario) {
        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellido", apellido);
        model.addAttribute("cedula", cedula);
        model.addAttribute("fechaNacimiento", fechaNacimiento);
        model.addAttribute("email", email);
        model.addAttribute("telefono", telefono);
        model.addAttribute("direccion", direccion);
        model.addAttribute("puesto", puesto);
        model.addAttribute("numeroCuenta", numeroCuenta);
        model.addAttribute("salario", salario);
    }

    private void repoblarModeloEdicion(Model model, int id, String nombre, String apellido, 
                                     String cedula, LocalDate fechaNacimiento, String email, 
                                     int telefono, String direccion, String tipoUsuario, 
                                     String puesto, String numeroCuenta, Double salario) {
        User userToShow = new User();
        userToShow.setId_user(id);
        userToShow.setName(nombre);
        userToShow.setLast_name(apellido);
        userToShow.setIdentification(cedula);
        userToShow.setBirthdate(fechaNacimiento);
        userToShow.setEmail(email);
        userToShow.setPhone(telefono);
        userToShow.setAddress(direccion);
        userToShow.setUserType(tipoUsuario);
        
        model.addAttribute("user", userToShow);
        model.addAttribute("puesto", puesto);
        model.addAttribute("numeroCuenta", numeroCuenta);
        model.addAttribute("salario", salario);
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
