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
 * Modelo para representar la relación entre recetas y tags.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "RecipeTags")
public class RecipeTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeTagId")
    private Integer recipeTagId;
    
    @Column(name = "recipeId", nullable = false)
    private Integer recipeId;
    
    @Column(name = "tagId", nullable = false)
    private Integer tagId;
    
    // Relación con Recipe (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "recipeId", insertable = false, updatable = false)
    private Recipe recipe;
    
    // Relación con Tag (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "tagId", insertable = false, updatable = false)
    private Tag tag;
    
    // Constructor por defecto
    public RecipeTag() {
    }
    
    // Constructor con campos obligatorios
    public RecipeTag(Integer recipeId, Integer tagId) {
        this.recipeId = recipeId;
        this.tagId = tagId;
    }
    
    // Getters y Setters
    public Integer getRecipeTagId() {
        return recipeTagId;
    }
    
    public void setRecipeTagId(Integer recipeTagId) {
        this.recipeTagId = recipeTagId;
    }
    
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public Integer getTagId() {
        return tagId;
    }
    
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    public Tag getTag() {
        return tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    @Override
    public String toString() {
        return "RecipeTag{" +
                "recipeTagId=" + recipeTagId +
                ", recipeId=" + recipeId +
                ", tagId=" + tagId +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        RecipeTag that = (RecipeTag) o;
        
        if (recipeId != null ? !recipeId.equals(that.recipeId) : that.recipeId != null) return false;
        return tagId != null ? tagId.equals(that.tagId) : that.tagId != null;
    }
    
    @Override
    public int hashCode() {
        int result = recipeId != null ? recipeId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }
}
