package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.RecipeCategory;

/**
 * Repositorio para la entidad RecipeCategory.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
    
    /**
     * Busca todas las categorías de una receta específica.
     * 
     * @param recipeId ID de la receta
     * @return Lista de relaciones receta-categoría
     */
    List<RecipeCategory> findByRecipeId(Integer recipeId);
    
    /**
     * Busca todas las recetas de una categoría específica.
     * 
     * @param categoryId ID de la categoría
     * @return Lista de relaciones receta-categoría
     */
    List<RecipeCategory> findByCategoryId(Integer categoryId);
    
    /**
     * Verifica si existe una relación entre una receta y una categoría.
     * 
     * @param recipeId ID de la receta
     * @param categoryId ID de la categoría
     * @return true si existe la relación, false en caso contrario
     */
    boolean existsByRecipeIdAndCategoryId(Integer recipeId, Integer categoryId);
    
    /**
     * Elimina todas las categorías de una receta específica.
     * 
     * @param recipeId ID de la receta
     */
    @Modifying
    @Query("DELETE FROM RecipeCategory rc WHERE rc.recipeId = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Integer recipeId);
    
    /**
     * Elimina una relación específica entre receta y categoría.
     * 
     * @param recipeId ID de la receta
     * @param categoryId ID de la categoría
     */
    @Modifying
    @Query("DELETE FROM RecipeCategory rc WHERE rc.recipeId = :recipeId AND rc.categoryId = :categoryId")
    void deleteByRecipeIdAndCategoryId(@Param("recipeId") Integer recipeId, @Param("categoryId") Integer categoryId);
    
    /**
     * Cuenta cuántas recetas tiene una categoría específica.
     * 
     * @param categoryId ID de la categoría
     * @return Número de recetas en la categoría
     */
    long countByCategoryId(Integer categoryId);
}
