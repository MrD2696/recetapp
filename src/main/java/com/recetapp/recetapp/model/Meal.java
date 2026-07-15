package com.recetapp.recetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modelo para representar un tipo de comida en el sistema.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "Meals")
public class Meal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mealId")
    private Integer mealId;
    
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;
    
    // Constructor por defecto
    public Meal() {
    }
    
    // Constructor con nombre
    public Meal(String name) {
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
        return "Meal{" +
                "mealId=" + mealId +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Meal meal = (Meal) o;
        
        if (mealId != null ? !mealId.equals(meal.mealId) : meal.mealId != null) return false;
        return name != null ? name.equals(meal.name) : meal.name == null;
    }
    
    @Override
    public int hashCode() {
        int result = mealId != null ? mealId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
