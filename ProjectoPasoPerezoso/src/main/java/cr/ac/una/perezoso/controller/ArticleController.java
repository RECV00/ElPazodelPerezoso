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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    
    // Vista completa con paginación
    @GetMapping("/list")
    public String listArticles(
        @RequestParam(value = "productName", required = false) String productName,
        @RequestParam(value = "supplier", required = false) String supplier,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "6") int size,
        Model model) throws SQLException, ClassNotFoundException {
        
        Page<Article> articlePage = getFilteredArticles(productName, supplier, page, size);
        
        model.addAttribute("articles", articlePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlePage.getTotalPages());
        return "/article/list_article";
    }
    
     // Endpoint para AJAX
    @GetMapping("/api/list")
    @ResponseBody
    public Page<Article> listArticlesApi(
        @RequestParam(value = "productName", required = false) String productName,
        @RequestParam(value = "supplier", required = false) String supplier,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "6") int size) {
        
        return getFilteredArticles(productName, supplier, page, size);
    }

    private Page<Article> getFilteredArticles(String productName, String supplier, int page, int size) {
        if (productName != null && !productName.isEmpty()) {
            return articleService.findByProductNameContaining(productName, PageRequest.of(page, size));
        } else if (supplier != null && !supplier.isEmpty()) {
            return articleService.findBySupplierContaining(supplier, PageRequest.of(page, size));
        } else {
            return articleService.getAll(PageRequest.of(page, size));
        }
    }
    
    
  // Métodos para modales
    @GetMapping("/add-form")
    public String getAddModal(Model model) {
        model.addAttribute("article", new Article());
        return "/article/add_article_modal :: addModal";
    }
    
     //"/Article"/form""
  @PostMapping("/add")
    public String saveArticles(
        @RequestParam("productName") String productName,
        @RequestParam("description") String description,
        @RequestParam("productQuantity") int productQuantity,
        @RequestParam("unitOfMeasurement") String unitOfMeasurement,
        @RequestParam(value = "expirationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate,
        @RequestParam("supplier") String supplier,
        @RequestParam("unitPrice") int unitPrice,
        RedirectAttributes redirectAttributes) throws SQLException, ClassNotFoundException {
        
        // Validación
        if (!logic.validateWord(productName) || !logic.checkStrings(description) || 
            !logic.validateNumbers(productQuantity) || !logic.validateWord(unitOfMeasurement) || 
            !logic.validateWord(supplier) || !logic.validateNumbers(unitPrice) ||
            (expirationDate != null && !logic.validateLocalDate(expirationDate))) {
            
            redirectAttributes.addFlashAttribute("error", "Todos los campos son obligatorios y deben ser válidos.");
            return "redirect:/Article/add-form";
        }
        
        Article article = new Article(productName, description, productQuantity, 
                                    unitOfMeasurement, expirationDate, supplier, unitPrice);
        articleService.save(article);
        
        redirectAttributes.addFlashAttribute("success", "Artículo creado correctamente");
        return "redirect:/Article/list";
    }
   
     @GetMapping("/edit-form")
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
    return "/article/edit_article_modal :: editModal"; 
    }   
        
    //Editar Articulos 
    
    @PostMapping("/update")
    public String UpdateArticles(
    @RequestParam("id_article") int id_article,
    @RequestParam("productName") String productName,
    @RequestParam("description") String description,
    @RequestParam("productQuantity") int productQuantity,
    @RequestParam("unitOfMeasurement") String unitOfMeasurement,
    @RequestParam(value = "expirationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate,
    @RequestParam("supplier") String supplier,
    @RequestParam("unitPrice") int unitPrice,RedirectAttributes redirectAttributes) throws SQLException, ClassNotFoundException {
    
    Article article =   articleService.getById(id_article);
    
    if (article == null) {
            return "redirect:/Article/list?error=Articulo no encontrado";
        }
    
    article.setProductName(productName);
    article.setDescription(description);
    article.setProductQuantity(productQuantity);
    article.setUnitOfMeasurement(unitOfMeasurement);
    article.setExpirationDate(expirationDate);
    article.setSupplier(supplier);
    article.setUnitPrice(unitPrice);
    
   articleService.save(article);
    
   redirectAttributes.addFlashAttribute("successMessage", "Articulo actualizado correctamente.");
    return "redirect:/Article/list?success=Artículo actualizado correctamente";
}
    
   @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable int id, RedirectAttributes redirectAttributes) 
        throws SQLException, ClassNotFoundException {
        
        articleService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Artículo eliminado correctamente");
        return "redirect:/Article/list";
    }


}

