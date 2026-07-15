package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.RecipeTag;

/**
 * Repositorio para la entidad RecipeTag.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag, Integer> {
    
    /**
     * Busca todos los tags de una receta específica.
     * 
     * @param recipeId ID de la receta
     * @return Lista de relaciones receta-tag
     */
    List<RecipeTag> findByRecipeId(Integer recipeId);
    
    /**
     * Busca todas las recetas de un tag específico.
     * 
     * @param tagId ID del tag
     * @return Lista de relaciones receta-tag
     */
    List<RecipeTag> findByTagId(Integer tagId);
    
    /**
     * Verifica si existe una relación entre una receta y un tag.
     * 
     * @param recipeId ID de la receta
     * @param tagId ID del tag
     * @return true si existe la relación, false en caso contrario
     */
    boolean existsByRecipeIdAndTagId(Integer recipeId, Integer tagId);
    
    /**
     * Elimina todos los tags de una receta específica.
     * 
     * @param recipeId ID de la receta
     */
    @Modifying
    @Query("DELETE FROM RecipeTag rt WHERE rt.recipeId = :recipeId")
    void deleteByRecipeId(@Param("recipeId") Integer recipeId);
    
    /**
     * Elimina una relación específica entre receta y tag.
     * 
     * @param recipeId ID de la receta
     * @param tagId ID del tag
     */
    @Modifying
    @Query("DELETE FROM RecipeTag rt WHERE rt.recipeId = :recipeId AND rt.tagId = :tagId")
    void deleteByRecipeIdAndTagId(@Param("recipeId") Integer recipeId, @Param("tagId") Integer tagId);
    
    /**
     * Cuenta cuántas recetas tiene un tag específico.
     * 
     * @param tagId ID del tag
     * @return Número de recetas con el tag
     */
    long countByTagId(Integer tagId);
}
