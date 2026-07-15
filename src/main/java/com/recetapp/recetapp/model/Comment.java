package com.recetapp.recetapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Modelo para representar los comentarios de los usuarios en las recetas.
 * Un usuario puede comentar en cualquier receta.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "Comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Integer commentId;
    
    @Column(name = "recipeId", nullable = false)
    private Integer recipeId;
    
    @Column(name = "userId", nullable = false)
    private Integer userId;
    
    @Column(name = "content", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    // Relaciones con otras entidades (opcionales, para consultas)
    @ManyToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    // Constructor por defecto
    public Comment() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor con parámetros
    public Comment(Integer recipeId, Integer userId, String content) {
        this();
        this.recipeId = recipeId;
        this.userId = userId;
        this.content = content;
    }
    
    // Constructor completo
    public Comment(Integer commentId, Integer recipeId, Integer userId, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }
    
    // Getters y Setters
    public Integer getCommentId() {
        return commentId;
    }
    
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
