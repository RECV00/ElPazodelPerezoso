/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.controller;
import cr.ac.una.perezoso.business.ArticleLogic;
import cr.ac.una.perezoso.domain.Article;
import cr.ac.una.perezoso.service.ArticleService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author keyna
 */
@Controller
@RequestMapping("/Article")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
     
    @Autowired
    private ArticleLogic logic;
    
    public ArticleController(){
        logic = new ArticleLogic();
    }
    
  @GetMapping({"/addForm"})
    public String addArticle(Model model) {
        model.addAttribute("article",new Article());             
        return "/article/add_article";  
    }
     //"/Article"/form""
 @PostMapping("/saveArticle")
public String saveArticles(
    @RequestParam("productName") String productName,
    @RequestParam("description") String description,
    @RequestParam("productQuantity") int productQuantity,
    @RequestParam("unitOfMeasurement") String unitOfMeasurement,
    @RequestParam(value = "expirationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate,
    @RequestParam("supplier") String supplier,
    @RequestParam("unitPrice") int unitPrice,
    Model model) throws SQLException, ClassNotFoundException {
    
    // Validación modificada para fecha opcional
    if (!logic.validateWord(productName) || !logic.checkStrings(description) || 
        !logic.validateNumbers(productQuantity) || !logic.validateWord(unitOfMeasurement) || 
        !logic.validateWord(supplier) || !logic.validateNumbers(unitPrice) ||
        (expirationDate != null && !logic.validateLocalDate(expirationDate))) {
        
        model.addAttribute("error", "Todos los campos son obligatorios y deben ser válidos.");
        return "/article/add_article"; 
    }
    
    Article article = new Article(productName, description, productQuantity, 
                                unitOfMeasurement, expirationDate, supplier, unitPrice);
     articleService.save(article);
    
    return "redirect:/Article/list?success=Artículo creado correctamente";
}

        //Listar articulos  
       // "/Article/list"
     @GetMapping("/list")
    public String listArticles(Model model) throws SQLException, ClassNotFoundException {
    List<Article> articles = articleService.getAll();
     model.addAttribute("articles", articles);
    
        return "/article/list_article";
                
    }
    
    // Método para filtrar tours por nombre
    @GetMapping("/filter")
    public String filterArticle(@RequestParam(value="supplier",required = false) String supplier,@RequestParam(value="id_article", required = false) Integer id_article, Model model) {
        List<Article> articles;
        
        if (supplier != null && !supplier.isEmpty()) {
            // Filtrar tours por nombre si se proporciona un valor
            articles = articleService.searchBySupplier(supplier);
            if (articles.isEmpty()) {
            model.addAttribute("errorMessage", "No se encontraron artículos del proveedor: " + supplier);
             }
        }else if(id_article == null){
            // Mostrar todos los tours si no se proporciona un valor
            articles = articleService.getAll();
        }
        else {
            Article article = articleService.getById(id_article);
            articles = article != null ? List.of(article) : List.of();
            if (articles.isEmpty()) {
                model.addAttribute("errorMessage", "No se encontró ningún artículo con el ID: " + id_article);
            }
        }
        model.addAttribute("articles", articles); // Pasar la lista filtrada a la vista
        return "/article/list_article"; // Renderiza la vista tours.html
    }
    
    
    //"/Article/formRemove"
      @GetMapping({"/formRemove", "/createRemove"})
    public String formRemove() {
        return "remove";  
    }

    
    //Eliminar Articulos 
     //"/Article/removeData"
    @PostMapping("/removeData")
    public String removeArticles(@RequestParam("id_article") int id_article) throws SQLException, ClassNotFoundException{
        articleService.delete(id_article);
    
        return "redirect:/Article/list";
    }
  

     @GetMapping("/formUpdate")
    public String formUpdate(@RequestParam("id_article") int id_article, Model model)  throws SQLException, ClassNotFoundException{
        if (id_article <= 0) {
            return "redirect:/Article/list?error=Articulo no encontrado";
        }
    // Obtener el articulo existente por su ID
    Article article = articleService.getById(id_article);
    if (article != null) {
        model.addAttribute("article", article);
    } else {
        return "redirect:/Article/list?error=Articulo no encontrado";
    }
    return "/article/edit_article";  
    }   
        
    //Editar Articulos 
    //"/Article/Updates"
    @PostMapping("/Updates")
    public String UpdateArticles(
    @RequestParam("id_article") int id_article,
    @RequestParam("productName") String productName,
    @RequestParam("description") String description,
    @RequestParam("productQuantity") int productQuantity,
    @RequestParam("unitOfMeasurement") String unitOfMeasurement,
    @RequestParam(value = "expirationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate,
    @RequestParam("supplier") String supplier,
    @RequestParam("unitPrice") int unitPrice) throws SQLException, ClassNotFoundException {
    
    // Validación modificada para fecha opcional
    if (!logic.validateNumbers(id_article) || !logic.validateWord(productName) || 
        !logic.checkStrings(description) || !logic.validateNumbers(productQuantity) || 
        !logic.validateWord(unitOfMeasurement) || !logic.validateWord(supplier) || 
        !logic.validateNumbers(unitPrice) || 
        (expirationDate != null && !logic.validateLocalDate(expirationDate))) {
        
        return "redirect:/Article/list?error=Datos inválidos";
    }
    
    Article article = new Article(id_article, productName, description, productQuantity,
                                unitOfMeasurement, expirationDate, supplier, unitPrice);
   articleService.save(article);
    
    return "redirect:/Article/list?success=Artículo actualizado correctamente";
}
    
   
//     @GetMapping({"/formSearch", "/createSearch"})
//    public String formSearch() {
//        return "searchResult";  
//    }
//    //"/Article/searchById""
//    @PostMapping("/searchById")
//    public String searchArticleById(@RequestParam("id_article") int id_article, Model model) throws SQLException, ClassNotFoundException {
//    Article article = articleService.getById(id_article);
//    if (article != null) {
//        model.addAttribute("article", article);
//    } else {
//        model.addAttribute("error", "No se encontró el artículo con ID " + id_article);
//    }
//    return "searchResult";
//   
//}
//    //"/Article/formSupplier
//     @GetMapping({"/formSupplier", "/createSupplier"})
//    public String formSupplier() {
//        return "searchSupplier";  
//    }
//    //"/Article/searchBySupplier
//    @PostMapping("/searchBySupplier")
//    public String searchArticlesBySupplier(@RequestParam("supplier") String supplier, Model model) throws SQLException, ClassNotFoundException {
//        List<Article> articles = articleService.searchBySupplier(supplier);
//        if (articles.isEmpty()) {
//            model.addAttribute("error", "No se encontraron artículos para el proveedor: " + supplier);
//        } else {
//            model.addAttribute("articles", articles);
//        }
//        return "/article/list_article"; 
//    }

}

