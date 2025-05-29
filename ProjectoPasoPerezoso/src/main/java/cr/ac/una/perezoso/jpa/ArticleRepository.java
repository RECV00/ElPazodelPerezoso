/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.jpa;

import cr.ac.una.perezoso.domain.Article;
import cr.ac.una.perezoso.domain.Tour;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keyna
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article>{
    List<Article> findByProductNameContainingIgnoreCase(String productName);
    Page<Article> findByProductNameContainingIgnoreCase(String productName,Pageable pageable);
    List<Article> findBySupplierContainingIgnoreCase(String supplier);
    List<Article> findByExpirationDate(LocalDate expirationDate);
}
