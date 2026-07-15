package com.recetapp.recetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Modelo para representar la información nutricional de una receta.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "NutriInformation")
public class NutriInformation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutriId")
    private Integer nutriId;
    
    @Column(name = "recipeId", nullable = false, unique = true)
    private Integer recipeId;
    
    @Column(name = "calories", length = 50)
    private String calories;
    
    @Column(name = "fat", length = 50)
    private String fat;
    
    @Column(name = "saturatedFat", length = 50)
    private String saturatedFat;
    
    @Column(name = "polyunsaturatedFat", length = 50)
    private String polyunsaturatedFat;
    
    @Column(name = "monounsaturatedFat", length = 50)
    private String monounsaturatedFat;
    
    @Column(name = "carbohydrates", length = 50)
    private String carbohydrates;
    
    @Column(name = "sugar", length = 50)
    private String sugar;
    
    @Column(name = "fiber", length = 50)
    private String fiber;
    
    @Column(name = "protein", length = 50)
    private String protein;
    
    @Column(name = "sodium", length = 50)
    private String sodium;
    
    // Relación con Recipe (opcional, para consultas)
    @OneToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    // Constructor por defecto
    public NutriInformation() {
    }
    
    // Constructor con campos obligatorios
    public NutriInformation(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    // Constructor completo
    public NutriInformation(Integer recipeId, String calories, String fat, String saturatedFat,
                           String polyunsaturatedFat, String monounsaturatedFat, String carbohydrates,
                           String sugar, String fiber, String protein, String sodium) {
        this.recipeId = recipeId;
        this.calories = calories;
        this.fat = fat;
        this.saturatedFat = saturatedFat;
        this.polyunsaturatedFat = polyunsaturatedFat;
        this.monounsaturatedFat = monounsaturatedFat;
        this.carbohydrates = carbohydrates;
        this.sugar = sugar;
        this.fiber = fiber;
        this.protein = protein;
        this.sodium = sodium;
    }
    
    // Getters y Setters
    public Integer getNutriId() {
        return nutriId;
    }
    
    public void setNutriId(Integer nutriId) {
        this.nutriId = nutriId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public String getCalories() {
        return calories;
    }
    
    public void setCalories(String calories) {
        this.calories = calories;
    }
    
    public String getFat() {
        return fat;
    }
    
    public void setFat(String fat) {
        this.fat = fat;
    }
    
    public String getSaturatedFat() {
        return saturatedFat;
    }
    
    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }
    
    public String getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }
    
    public void setPolyunsaturatedFat(String polyunsaturatedFat) {
        this.polyunsaturatedFat = polyunsaturatedFat;
    }
    
    public String getMonounsaturatedFat() {
        return monounsaturatedFat;
    }
    
    public void setMonounsaturatedFat(String monounsaturatedFat) {
        this.monounsaturatedFat = monounsaturatedFat;
    }
    
    public String getCarbohydrates() {
        return carbohydrates;
    }
    
    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    
    public String getSugar() {
        return sugar;
    }
    
    public void setSugar(String sugar) {
        this.sugar = sugar;
    }
    
    public String getFiber() {
        return fiber;
    }
    
    public void setFiber(String fiber) {
        this.fiber = fiber;
    }
    
    public String getProtein() {
        return protein;
    }
    
    public void setProtein(String protein) {
        this.protein = protein;
    }
    
    public String getSodium() {
        return sodium;
    }
    
    public void setSodium(String sodium) {
        this.sodium = sodium;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    @Override
    public String toString() {
        return "NutriInformation{" +
                "nutriId=" + nutriId +
                ", recipeId=" + recipeId +
                ", calories='" + calories + '\'' +
                ", fat='" + fat + '\'' +
                ", saturatedFat='" + saturatedFat + '\'' +
                ", polyunsaturatedFat='" + polyunsaturatedFat + '\'' +
                ", monounsaturatedFat='" + monounsaturatedFat + '\'' +
                ", carbohydrates='" + carbohydrates + '\'' +
                ", sugar='" + sugar + '\'' +
                ", fiber='" + fiber + '\'' +
                ", protein='" + protein + '\'' +
                ", sodium='" + sodium + '\'' +
                '}';
    }
}
