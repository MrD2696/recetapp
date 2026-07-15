package com.recetapp.recetapp.dto;

import java.util.List;

/**
 * DTO para crear y actualizar recetas con categorías y tipos de comida.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class RecipeCreateUpdateDTO {
    
    private Integer recipeId; // Solo para actualizaciones
    private Integer userId;
    private String title;
    private String description;
    private String preparationTime;
    private Integer servings;
    private Integer ingredientsCount;
    private String proced;
    
    // Nuevos campos para categorías y tipos de comida
    private List<Integer> categoryIds; // IDs de las categorías seleccionadas
    private List<Integer> mealIds;     // IDs de los tipos de comida seleccionados
    
    // Constructor por defecto
    public RecipeCreateUpdateDTO() {
    }
    
    // Constructor para crear receta
    public RecipeCreateUpdateDTO(Integer userId, String title, String description, 
                               String preparationTime, Integer servings, Integer ingredientsCount, 
                               String proced, List<Integer> categoryIds, List<Integer> mealIds) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.ingredientsCount = ingredientsCount;
        this.proced = proced;
        this.categoryIds = categoryIds;
        this.mealIds = mealIds;
    }
    
    // Constructor para actualizar receta
    public RecipeCreateUpdateDTO(Integer recipeId, Integer userId, String title, String description, 
                               String preparationTime, Integer servings, Integer ingredientsCount, 
                               String proced, List<Integer> categoryIds, List<Integer> mealIds) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.ingredientsCount = ingredientsCount;
        this.proced = proced;
        this.categoryIds = categoryIds;
        this.mealIds = mealIds;
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
    
    public List<Integer> getCategoryIds() {
        return categoryIds;
    }
    
    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
    
    public List<Integer> getMealIds() {
        return mealIds;
    }
    
    public void setMealIds(List<Integer> mealIds) {
        this.mealIds = mealIds;
    }
    
    @Override
    public String toString() {
        return "RecipeCreateUpdateDTO{" +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime='" + preparationTime + '\'' +
                ", servings=" + servings +
                ", ingredientsCount=" + ingredientsCount +
                ", proced='" + proced + '\'' +
                ", categoryIds=" + categoryIds +
                ", mealIds=" + mealIds +
                '}';
    }
}
