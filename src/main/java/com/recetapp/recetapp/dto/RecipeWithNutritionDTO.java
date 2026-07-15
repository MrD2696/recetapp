package com.recetapp.recetapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO que combina información de receta con información nutricional, categorías y meals.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class RecipeWithNutritionDTO {
    
    private Integer recipeId;
    private Integer userId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String preparationTime;
    private Integer servings;
    private Integer ingredientsCount;
    private String proced;
    
    @JsonProperty("Information Nutritional")
    private List<NutritionalInfoDTO> nutritionalInfo;
    
    @JsonProperty("Categories")
    private List<CategoryDTO> categories;
    
    @JsonProperty("Meals")
    private List<MealDTO> meals;
    
    // Constructor por defecto
    public RecipeWithNutritionDTO() {}
    
    // Constructor con parámetros
    public RecipeWithNutritionDTO(Integer recipeId, Integer userId, String title, String description,
                                LocalDateTime createdAt, String preparationTime, Integer servings,
                                Integer ingredientsCount, String proced, List<NutritionalInfoDTO> nutritionalInfo,
                                List<CategoryDTO> categories, List<MealDTO> meals) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.ingredientsCount = ingredientsCount;
        this.proced = proced;
        this.nutritionalInfo = nutritionalInfo;
        this.categories = categories;
        this.meals = meals;
    }
    
    // Getters y Setters
    public Integer getRecipeId() { return recipeId; }
    public void setRecipeId(Integer recipeId) { this.recipeId = recipeId; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getPreparationTime() { return preparationTime; }
    public void setPreparationTime(String preparationTime) { this.preparationTime = preparationTime; }
    
    public Integer getServings() { return servings; }
    public void setServings(Integer servings) { this.servings = servings; }
    
    public Integer getIngredientsCount() { return ingredientsCount; }
    public void setIngredientsCount(Integer ingredientsCount) { this.ingredientsCount = ingredientsCount; }
    
    public String getProced() { return proced; }
    public void setProced(String proced) { this.proced = proced; }
    
    public List<NutritionalInfoDTO> getNutritionalInfo() { return nutritionalInfo; }
    public void setNutritionalInfo(List<NutritionalInfoDTO> nutritionalInfo) { this.nutritionalInfo = nutritionalInfo; }
    
    public List<CategoryDTO> getCategories() { return categories; }
    public void setCategories(List<CategoryDTO> categories) { this.categories = categories; }
    
    public List<MealDTO> getMeals() { return meals; }
    public void setMeals(List<MealDTO> meals) { this.meals = meals; }
}
