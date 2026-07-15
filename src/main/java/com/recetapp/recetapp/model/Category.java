package com.recetapp.recetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modelo para representar una categoría de recetas en el sistema.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "Categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Integer categoryId;
    
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;
    
    // Constructor por defecto
    public Category() {
    }
    
    // Constructor con nombre
    public Category(String name) {
        this.name = name;
    }
    
    // Getters y Setters
    public Integer getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Category category = (Category) o;
        
        if (categoryId != null ? !categoryId.equals(category.categoryId) : category.categoryId != null) return false;
        return name != null ? name.equals(category.name) : category.name == null;
    }
    
    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
