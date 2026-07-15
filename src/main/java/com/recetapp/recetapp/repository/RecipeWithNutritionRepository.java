package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio personalizado para consultas que combinan recetas con información nutricional.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface RecipeWithNutritionRepository {
    
    /**
     * Busca todas las recetas de un usuario con su información nutricional.
     * 
     * @param userId ID del usuario
     * @return Lista de recetas con información nutricional
     */
    @Query(value = 
        "SELECT " +
        "   r.recipeId, r.userId, r.title, r.description, r.createdAt, " +
        "   r.preparationTime, r.servings, r.ingredientsCount, r.proced, " +
        "   n.nutriId, n.calories, n.fat, n.saturatedFat, n.polyunsaturatedFat, " +
        "   n.monounsaturatedFat, n.carbohydrates, n.sugar, n.fiber, n.protein, n.sodium " +
        "FROM Recipes r " +
        "LEFT JOIN NutriInformation n ON r.recipeId = n.recipeId " +
        "WHERE r.userId = :userId " +
        "ORDER BY r.createdAt DESC", 
        nativeQuery = true)
    List<Object[]> findRecipesWithNutritionByUserId(@Param("userId") Integer userId);
    
    /**
     * Busca una receta específica con su información nutricional.
     * 
     * @param recipeId ID de la receta
     * @return Receta con información nutricional
     */
    @Query(value = 
        "SELECT " +
        "   r.recipeId, r.userId, r.title, r.description, r.createdAt, " +
        "   r.preparationTime, r.servings, r.ingredientsCount, r.proced, " +
        "   n.nutriId, n.calories, n.fat, n.saturatedFat, n.polyunsaturatedFat, " +
        "   n.monounsaturatedFat, n.carbohydrates, n.sugar, n.fiber, n.protein, n.sodium " +
        "FROM Recipes r " +
        "LEFT JOIN NutriInformation n ON r.recipeId = n.recipeId " +
        "WHERE r.recipeId = :recipeId", 
        nativeQuery = true)
    Object[] findRecipeWithNutritionById(@Param("recipeId") Integer recipeId);
    
    /**
     * Busca recetas por título que contengan el texto especificado, con información nutricional.
     * 
     * @param title Texto a buscar en el título
     * @param userId ID del usuario (opcional, null para buscar en todas las recetas)
     * @return Lista de recetas con información nutricional
     */
    @Query(value = 
        "SELECT " +
        "   r.recipeId, r.userId, r.title, r.description, r.createdAt, " +
        "   r.preparationTime, r.servings, r.ingredientsCount, r.proced, " +
        "   n.nutriId, n.calories, n.fat, n.saturatedFat, n.polyunsaturatedFat, " +
        "   n.monounsaturatedFat, n.carbohydrates, n.sugar, n.fiber, n.protein, n.sodium " +
        "FROM Recipes r " +
        "LEFT JOIN NutriInformation n ON r.recipeId = n.recipeId " +
        "WHERE r.title LIKE %:title% " +
        "   AND (:userId IS NULL OR r.userId = :userId) " +
        "ORDER BY r.createdAt DESC", 
        nativeQuery = true)
    List<Object[]> findRecipesWithNutritionByTitle(@Param("title") String title, @Param("userId") Integer userId);
}
