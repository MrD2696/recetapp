package com.recetapp.recetapp.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Modelo para representar una receta en el sistema.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Entity
@Table(name = "Recipes")
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeId")
    private Integer recipeId;
    
    @Column(name = "userId", nullable = false)
    private Integer userId;
    
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;
    
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "preparationTime", length = 50)
    private String preparationTime;
    
    @Column(name = "servings")
    private Integer servings;
    
    @Column(name = "ingredientsCount")
    private Integer ingredientsCount;
    
    @Column(name = "proced", columnDefinition = "NVARCHAR(MAX)")
    private String proced;
    
    // Relación con User (opcional, para consultas)
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    // Relaciones con categorías, tags y comidas
    @OneToMany(mappedBy = "recipe")
    private Set<RecipeCategory> recipeCategories = new HashSet<>();
    
    @OneToMany(mappedBy = "recipe")
    private Set<RecipeTag> recipeTags = new HashSet<>();
    
    @OneToMany(mappedBy = "recipe")
    private Set<RecipeMeal> recipeMeals = new HashSet<>();
    
    // Constructor por defecto
    public Recipe() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor con campos obligatorios
    public Recipe(Integer userId, String title, String description) {
        this();
        this.userId = userId;
        this.title = title;
        this.description = description;
    }
    
    // Constructor completo
    public Recipe(Integer userId, String title, String description, String preparationTime, 
                  Integer servings, Integer ingredientsCount, String proced) {
        this(userId, title, description);
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.ingredientsCount = ingredientsCount;
        this.proced = proced;
    }
    
    // Getters y Setters
    public Integer getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getPreparationTime() {
        return preparationTime;
    }
    
    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }
    
    public Integer getServings() {
        return servings;
    }
    
    public void setServings(Integer servings) {
        this.servings = servings;
    }
    
    public Integer getIngredientsCount() {
        return ingredientsCount;
    }
    
    public void setIngredientsCount(Integer ingredientsCount) {
        this.ingredientsCount = ingredientsCount;
    }
    
    public String getProced() {
        return proced;
    }
    
    public void setProced(String proced) {
        this.proced = proced;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    // Getters y Setters para las relaciones
    public Set<RecipeCategory> getRecipeCategories() {
        return recipeCategories;
    }
    
    public void setRecipeCategories(Set<RecipeCategory> recipeCategories) {
        this.recipeCategories = recipeCategories;
    }
    
    public Set<RecipeTag> getRecipeTags() {
        return recipeTags;
    }
    
    public void setRecipeTags(Set<RecipeTag> recipeTags) {
        this.recipeTags = recipeTags;
    }
    
    public Set<RecipeMeal> getRecipeMeals() {
        return recipeMeals;
    }
    
    public void setRecipeMeals(Set<RecipeMeal> recipeMeals) {
        this.recipeMeals = recipeMeals;
    }
    
    // Métodos de conveniencia para agregar/remover relaciones
    public void addCategory(Category category) {
        RecipeCategory recipeCategory = new RecipeCategory(this.recipeId, category.getCategoryId());
        recipeCategory.setRecipe(this);
        this.recipeCategories.add(recipeCategory);
    }
    
    public void removeCategory(Category category) {
        this.recipeCategories.removeIf(rc -> rc.getCategoryId().equals(category.getCategoryId()));
    }
    
    public void addTag(Tag tag) {
        RecipeTag recipeTag = new RecipeTag(this.recipeId, tag.getTagId());
        recipeTag.setRecipe(this);
        this.recipeTags.add(recipeTag);
    }
    
    public void removeTag(Tag tag) {
        this.recipeTags.removeIf(rt -> rt.getTagId().equals(tag.getTagId()));
    }
    
    public void addMeal(Meal meal) {
        RecipeMeal recipeMeal = new RecipeMeal(this.recipeId, meal.getMealId());
        recipeMeal.setRecipe(this);
        this.recipeMeals.add(recipeMeal);
    }
    
    public void removeMeal(Meal meal) {
        this.recipeMeals.removeIf(rm -> rm.getMealId().equals(meal.getMealId()));
    }
    
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", preparationTime='" + preparationTime + '\'' +
                ", servings=" + servings +
                ", ingredientsCount=" + ingredientsCount +
                ", proced='" + proced + '\'' +
                ", categoriesCount=" + (recipeCategories != null ? recipeCategories.size() : 0) +
                ", tagsCount=" + (recipeTags != null ? recipeTags.size() : 0) +
                ", mealsCount=" + (recipeMeals != null ? recipeMeals.size() : 0) +
                '}';
    }
}
