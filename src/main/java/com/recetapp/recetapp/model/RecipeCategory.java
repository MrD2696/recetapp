package com.recetapp.recetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Modelo para representar la relación entre recetas y categorías.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "RecipeCategories")
public class RecipeCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeCategoryId")
    private Integer recipeCategoryId;
    
    @Column(name = "recipeId", nullable = false)
    private Integer recipeId;
    
    @Column(name = "categoryId", nullable = false)
    private Integer categoryId;
    
    // Relación con Recipe (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    // Relación con Category (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    private Category category;
    
    // Constructor por defecto
    public RecipeCategory() {
    }
    
    // Constructor con campos obligatorios
    public RecipeCategory(Integer recipeId, Integer categoryId) {
        this.recipeId = recipeId;
        this.categoryId = categoryId;
    }
    
    // Getters y Setters
    public Integer getRecipeCategoryId() {
        return recipeCategoryId;
    }
    
    public void setRecipeCategoryId(Integer recipeCategoryId) {
        this.recipeCategoryId = recipeCategoryId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public Integer getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return "RecipeCategory{" +
                "recipeCategoryId=" + recipeCategoryId +
                ", recipeId=" + recipeId +
                ", categoryId=" + categoryId +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RecipeCategory that = (RecipeCategory) o;
        
        if (recipeId != null ? !recipeId.equals(that.recipeId) : that.recipeId != null) return false;
        return categoryId != null ? categoryId.equals(that.categoryId) : that.categoryId != null;
    }
    
    @Override
    public int hashCode() {
        int result = recipeId != null ? recipeId.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        return result;
    }
}
