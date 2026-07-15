package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;

/**
 * DTO para representar un favorito.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class FavoriteDTO {
    
    private Integer favoriteId;
    private Integer recipeId;
    private Integer userId;
    private LocalDateTime createdAt;
    
    // Información adicional de la receta (opcional)
    private String recipeTitle;
    private String recipeDescription;
    
    // Constructor por defecto
    public FavoriteDTO() {}
    
    // Constructor con parámetros básicos
    public FavoriteDTO(Integer recipeId, Integer userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }
    
    // Constructor completo
    public FavoriteDTO(Integer favoriteId, Integer recipeId, Integer userId, LocalDateTime createdAt) {
        this.favoriteId = favoriteId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.createdAt = createdAt;
    }
    
    // Constructor con información de receta
    public FavoriteDTO(Integer favoriteId, Integer recipeId, Integer userId, LocalDateTime createdAt,
                      String recipeTitle, String recipeDescription) {
        this(favoriteId, recipeId, userId, createdAt);
        this.recipeTitle = recipeTitle;
        this.recipeDescription = recipeDescription;
    }
    
    // Getters y Setters
    public Integer getFavoriteId() {
        return favoriteId;
    }
    
    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getRecipeTitle() {
        return recipeTitle;
    }
    
    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }
    
    public String getRecipeDescription() {
        return recipeDescription;
    }
    
    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }
    
    @Override
    public String toString() {
        return "FavoriteDTO{" +
                "favoriteId=" + favoriteId +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", recipeTitle='" + recipeTitle + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                '}';
    }
}
