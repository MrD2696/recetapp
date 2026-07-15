package com.recetapp.recetapp.dto;

/**
 * DTO para transferir datos de tipos de comida.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class MealDTO {
    
    private Integer mealId;
    private String name;
    
    // Constructor por defecto
    public MealDTO() {
    }
    
    // Constructor con campos
    public MealDTO(Integer mealId, String name) {
        this.mealId = mealId;
        this.name = name;
    }
    
    // Getters y Setters
    public Integer getMealId() {
        return mealId;
    }
    
    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "MealDTO{" +
                "mealId=" + mealId +
                ", name='" + name + '\'' +
                '}';
    }
}
