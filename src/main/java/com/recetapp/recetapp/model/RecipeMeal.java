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
 * Modelo para representar la relación entre recetas y tipos de comida.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "RecipeMeals")
public class RecipeMeal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeMealId")
    private Integer recipeMealId;
    
    @Column(name = "recipeId", nullable = false)
    private Integer recipeId;
    
    @Column(name = "mealId", nullable = false)
    private Integer mealId;
    
    // Relación con Recipe (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    // Relación con Meal (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "mealId", insertable = false, updatable = false)
    private Meal meal;
    
    // Constructor por defecto
    public RecipeMeal() {
    }
    
    // Constructor con campos obligatorios
    public RecipeMeal(Integer recipeId, Integer mealId) {
        this.recipeId = recipeId;
        this.mealId = mealId;
    }
    
    // Getters y Setters
    public Integer getRecipeMealId() {
        return recipeMealId;
    }
    
    public void setRecipeMealId(Integer recipeMealId) {
        this.recipeMealId = recipeMealId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public Integer getMealId() {
        return mealId;
    }
    
    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public Meal getMeal() {
        return meal;
    }
    
    public void setMeal(Meal meal) {
        this.meal = meal;
    }
    
    @Override
    public String toString() {
        return "RecipeMeal{" +
                "recipeMealId=" + recipeMealId +
                ", recipeId=" + recipeId +
                ", mealId=" + mealId +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RecipeMeal that = (RecipeMeal) o;
        
        if (recipeId != null ? !recipeId.equals(that.recipeId) : that.recipeId != null) return false;
        return mealId != null ? mealId.equals(that.mealId) : that.mealId != null;
    }
    
    @Override
    public int hashCode() {
        int result = recipeId != null ? recipeId.hashCode() : 0;
        result = 31 * result + (mealId != null ? mealId.hashCode() : 0);
        return result;
    }
}
