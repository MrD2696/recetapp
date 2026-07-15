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
 * Modelo para las imágenes de las recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "RecipeImages")
public class RecipeImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private Integer imageId;
    
    @Column(name = "recipeId", nullable = false)
    private Integer recipeId;
    
    @Column(name = "userId", nullable = false)
    private Integer userId;
    
    @Column(name = "imageUrl", nullable = false, length = 255)
    private String imageUrl;
    
    @Column(name = "uploadedAt", nullable = false)
    private LocalDateTime uploadedAt;
    
    // Relación con Recipe (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    // Constructor por defecto
    public RecipeImage() {
        this.uploadedAt = LocalDateTime.now();
    }
    
    // Constructor con parámetros
    public RecipeImage(Integer recipeId, Integer userId, String imageUrl) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.uploadedAt = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Integer getImageId() { return imageId; }
    public void setImageId(Integer imageId) { this.imageId = imageId; }
    
    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    
    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }
    
    @Override
    public String toString() {
        return "RecipeImage{" +
                "imageId=" + imageId +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", imageUrl='" + imageUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
