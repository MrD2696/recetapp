package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.RecipeMeal;

/**
 * Repositorio para la entidad RecipeMeal.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface RecipeMealRepository extends JpaRepository<RecipeMeal, Integer> {
    
    /**
     * Busca todos los tipos de comida de una receta específica.
     * 
     * @param recipeId ID de la receta
     * @return Lista de relaciones receta-tipo de comida
     */
    List<RecipeMeal> findByRecipeId(Integer recipeId);
    
    /**
     * Busca todas las recetas de un tipo de comida específico.
     * 
     * @param mealId ID del tipo de comida
     * @return Lista de relaciones receta-tipo de comida
     */
    List<RecipeMeal> findByMealId(Integer mealId);
    
    /**
     * Verifica si existe una relación entre una receta y un tipo de comida.
     * 
     * @param recipeId ID de la receta
     * @param mealId ID del tipo de comida
     * @return true si existe la relación, false en caso contrario
     */
    boolean existsByRecipeIdAndMealId(Integer recipeId, Integer mealId);
    
    /**
     * Elimina todos los tipos de comida de una receta específica.
     * 
     * @param recipeId ID de la receta
     */
    @Modifying
    @Query("DELETE FROM RecipeMeal rm WHERE rm.recipeId = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Integer recipeId);
    
    /**
     * Elimina una relación específica entre receta y tipo de comida.
     * 
     * @param recipeId ID de la receta
     * @param mealId ID del tipo de comida
     */
    @Modifying
    @Query("DELETE FROM RecipeMeal rm WHERE rm.recipeId = :recipeId AND rm.mealId = :mealId")
    void deleteByRecipeIdAndMealId(@Param("recipeId") Integer recipeId, @Param("mealId") Integer mealId);
    
    /**
     * Cuenta cuántas recetas tiene un tipo de comida específico.
     * 
     * @param mealId ID del tipo de comida
     * @return Número de recetas del tipo de comida
     */
    long countByMealId(Integer mealId);
}
