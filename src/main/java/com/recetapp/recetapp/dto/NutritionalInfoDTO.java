package com.recetapp.recetapp.dto;

/**
 * DTO para información nutricional de una receta.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class NutritionalInfoDTO {
    
    private Integer nutriId;
    private String calories;
    private String fat;
    private String saturatedFat;
    private String polyunsaturatedFat;
    private String monounsaturatedFat;
    private String carbohydrates;
    private String sugar;
    private String fiber;
    private String protein;
    private String sodium;
    
    // Constructor por defecto
    public NutritionalInfoDTO() {}
    
    // Constructor con parámetros
    public NutritionalInfoDTO(Integer nutriId, String calories, String fat, String saturatedFat,
                            String polyunsaturatedFat, String monounsaturatedFat, String carbohydrates,
                            String sugar, String fiber, String protein, String sodium) {
        this.nutriId = nutriId;
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
    public Integer getNutriId() { return nutriId; }
    public void setNutriId(Integer nutriId) { this.nutriId = nutriId; }
    
    public String getCalories() { return calories; }
    public void setCalories(String calories) { this.calories = calories; }
    
    public String getFat() { return fat; }
    public void setFat(String fat) { this.fat = fat; }
    
    public String getSaturatedFat() { return saturatedFat; }
    public void setSaturatedFat(String saturatedFat) { this.saturatedFat = saturatedFat; }
    
    public String getPolyunsaturatedFat() { return polyunsaturatedFat; }
    public void setPolyunsaturatedFat(String polyunsaturatedFat) { this.polyunsaturatedFat = polyunsaturatedFat; }
    
    public String getMonounsaturatedFat() { return monounsaturatedFat; }
    public void setMonounsaturatedFat(String monounsaturatedFat) { this.monounsaturatedFat = monounsaturatedFat; }
    
    public String getCarbohydrates() { return carbohydrates; }
    public void setCarbohydrates(String carbohydrates) { this.carbohydrates = carbohydrates; }
    
    public String getSugar() { return sugar; }
    public void setSugar(String sugar) { this.sugar = sugar; }
    
    public String getFiber() { return fiber; }
    public void setFiber(String fiber) { this.fiber = fiber; }
    
    public String getProtein() { return protein; }
    public void setProtein(String protein) { this.protein = protein; }
    
    public String getSodium() { return sodium; }
    public void setSodium(String sodium) { this.sodium = sodium; }
}
