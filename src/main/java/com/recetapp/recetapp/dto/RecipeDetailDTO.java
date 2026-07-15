package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para mostrar recetas con información completa incluyendo categorías, tipos de comida y tags.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class RecipeDetailDTO {
    
    private Integer recipeId;
    private Integer userId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String preparationTime;
    private Integer servings;
    private Integer ingredientsCount;
    private String proced;
    
    // Información del usuario que creó la receta
    private String userName;
    
    // Categorías de la receta
    private List<CategoryDTO> categories;
    
    // Tipos de comida de la receta
    private List<MealDTO> meals;
    
    // Tags de la receta (se pueden generar automáticamente)
    private List<String> tags;
    
    // Información nutricional
    private List<NutritionalInfoDTO> nutritionalInfo;
    
    // Constructor por defecto
    public RecipeDetailDTO() {
    }
    
    // Constructor completo
    public RecipeDetailDTO(Integer recipeId, Integer userId, String title, String description,
                          LocalDateTime createdAt, String preparationTime, Integer servings,
                          Integer ingredientsCount, String proced, String userName,
                          List<CategoryDTO> categories, List<MealDTO> meals, 
                          List<String> tags, List<NutritionalInfoDTO> nutritionalInfo) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.ingredientsCount = ingredientsCount;
        this.proced = proced;
        this.userName = userName;
        this.categories = categories;
        this.meals = meals;
        this.tags = tags;
        this.nutritionalInfo = nutritionalInfo;
    }
    
    // Getters y Setters
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getPreparationTime() {
        return preparationTime;
    }
    
    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }
    
    public Integer getServings() {
        return servings;
    }
    
    public void setServings(Integer servings) {
        this.servings = servings;
    }
    
    public Integer getIngredientsCount() {
        return ingredientsCount;
    }
    
    public void setIngredientsCount(Integer ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
    }
    
    public String getProced() {
        return proced;
    }
    
    public void setProced(String proced) {
        this.proced = proced;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public List<CategoryDTO> getCategories() {
        return categories;
    }
    
    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
    
    public List<MealDTO> getMeals() {
        return meals;
    }
    
    public void setMeals(List<MealDTO> meals) {
        this.meals = meals;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public List<NutritionalInfoDTO> getNutritionalInfo() {
        return nutritionalInfo;
    }
    
    public void setNutritionalInfo(List<NutritionalInfoDTO> nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }
    
    @Override
    public String toString() {
        return "RecipeDetailDTO{" +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", preparationTime='" + preparationTime + '\'' +
                ", servings=" + servings +
                ", ingredientsCount=" + ingredientsCount +
                ", proced='" + proced + '\'' +
                ", userName='" + userName + '\'' +
                ", categories=" + categories +
                ", meals=" + meals +
                ", tags=" + tags +
                ", nutritionalInfo=" + nutritionalInfo +
                '}';
    }
}
