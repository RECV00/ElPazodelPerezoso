/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Article;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{
    List<Article> findByProductNameContainingIgnoreCase(String productName);
    List<Article> findBySupplierContainingIgnoreCase(String supplier);
    List<Article> findByExpirationDate(LocalDate expirationDate);
}
