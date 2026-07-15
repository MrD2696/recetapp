package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO para las imágenes de las recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class RecipeImageDTO {
    
    private Integer imageId;
    private Integer recipeId;
    private Integer userId;
    private String imageUrl;
    private LocalDateTime uploadedAt;
    
    // Constructor por defecto
    public RecipeImageDTO() {}
    
    // Constructor con parámetros
    public RecipeImageDTO(Integer imageId, Integer recipeId, Integer userId, String imageUrl, LocalDateTime uploadedAt) {
        this.imageId = imageId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.uploadedAt = uploadedAt;
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
    
    @Override
    public String toString() {
        return "RecipeImageDTO{" +
                "imageId=" + imageId +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", imageUrl='" + imageUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
