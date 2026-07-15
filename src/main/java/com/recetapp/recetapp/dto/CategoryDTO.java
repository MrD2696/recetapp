package com.recetapp.recetapp.dto;

/**
 * DTO para transferir datos de categorías.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
public class CategoryDTO {
    
    private Integer categoryId;
    private String name;
    
    // Constructor por defecto
    public CategoryDTO() {
    }
    
    // Constructor con campos
    public CategoryDTO(Integer categoryId, String name) {
        this.categoryId = categoryId;
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
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
