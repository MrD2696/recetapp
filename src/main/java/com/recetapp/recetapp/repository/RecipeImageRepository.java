package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.RecipeImage;

/**
 * Repositorio para operaciones con imágenes de recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Integer> {
    
    /**
     * Busca todas las imágenes de una receta específica.
     * 
     * @param recipeId ID de la receta
     * @return Lista de imágenes de la receta
     */
    List<RecipeImage> findByRecipeIdOrderByUploadedAtDesc(Integer recipeId);
    
    /**
     * Busca todas las imágenes subidas por un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de imágenes del usuario
     */
    List<RecipeImage> findByUserIdOrderByUploadedAtDesc(Integer userId);
    
    /**
     * Busca imágenes de una receta específica subidas por un usuario específico.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return Lista de imágenes de la receta del usuario
     */
    List<RecipeImage> findByRecipeIdAndUserIdOrderByUploadedAtDesc(Integer recipeId, Integer userId);
    
    /**
     * Verifica si existe al menos una imagen para una receta.
     * 
     * @param recipeId ID de la receta
     * @return true si existe al menos una imagen
     */
    boolean existsByRecipeId(Integer recipeId);
    
    /**
     * Cuenta el número de imágenes de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Número de imágenes
     */
    long countByRecipeId(Integer recipeId);
    
    /**
     * Elimina todas las imágenes de una receta.
     * 
     * @param recipeId ID de la receta
     */
    void deleteByRecipeId(Integer recipeId);
    
    /**
     * Elimina todas las imágenes de un usuario.
     * 
     * @param userId ID del usuario
     */
    void deleteByUserId(Integer userId);
    
    /**
     * Busca la imagen principal (más reciente) de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Imagen principal de la receta
     */
    @Query("SELECT ri FROM RecipeImage ri WHERE ri.recipeId = :recipeId ORDER BY ri.uploadedAt DESC")
    RecipeImage findMainImageByRecipeId(@Param("recipeId") Integer recipeId);
}
