/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.service;

import cr.ac.una.perezoso.domain.Article;
import cr.ac.una.perezoso.domain.Dishe;
import cr.ac.una.perezoso.jpa.ArticleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
@Transactional
public class ArticleService implements CRUD<Article, Integer>{

     private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(Integer id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article getById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    // Métodos específicos adicionales para Article
    public List<Article> searchByProductName(String productName) {
        return articleRepository.findByProductNameContainingIgnoreCase(productName);
    }

    public Page<Article> findBySupplierContaining(String supplier, Pageable pageable) {
        return articleRepository.findBySupplierContainingIgnoreCase(supplier,pageable);
    }

    public List<Article> findByExpirationDate(LocalDate expirationDate) {
        return articleRepository.findByExpirationDate(expirationDate);
    }
    
     @Override
    public Page<Article> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

     public Page<Article> findByProductNameContaining(String name, Pageable pageable) {
    return articleRepository.findByProductNameContainingIgnoreCase(name, pageable);
}
     
     @Override
    public boolean existsById(Integer id) {
        return articleRepository.existsById(id);
    }
}
