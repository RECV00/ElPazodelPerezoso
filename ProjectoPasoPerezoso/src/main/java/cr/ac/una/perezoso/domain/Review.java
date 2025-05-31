/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keyna
 */
@Entity

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_review;
    
    private Integer rating;
    private String comment;
    private String reviewDate;
    private String title;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_client") 
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Review parentReview;

    @OneToMany(mappedBy = "parentReview", cascade = CascadeType.ALL)
    private List<Review> replies = new ArrayList<>();
    
    // Constructores
    public Review() {
        this.replies = new ArrayList<>();
    }

    public Review(String reviewId, String userId, Integer rating, String comment, 
                String reviewDate, String title, String status) {
        this();
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.title = title;
        this.status = status;
    }

    // Getters y Setters
  
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if(rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReplies() {
        return replies;
    }

    public void setReplies(List<Review> replies) {
        this.replies = replies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Métodos para manejar respuestas
    public void addReply(Review reply) {
        this.replies.add(reply);
    }

    public void removeReply(Review reply) {
        this.replies.remove(reply);
    }

    public void clearReplies() {
        this.replies.clear();
    }

}
