package com.recetapp.recetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modelo para representar un tag de recetas en el sistema.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "Tags")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagId")
    private Integer tagId;
    
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;
    
    // Constructor por defecto
    public Tag() {
    }
    
    // Constructor con nombre
    public Tag(String name) {
        this.name = name;
    }
    
    // Getters y Setters
    public Integer getTagId() {
        return tagId;
    }
    
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Tag tag = (Tag) o;
        
        if (tagId != null ? !tagId.equals(tag.tagId) : tag.tagId != null) return false;
        return name != null ? name.equals(tag.name) : tag.name == null;
    }
    
    @Override
    public int hashCode() {
        int result = tagId != null ? tagId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
