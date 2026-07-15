package com.recetapp.recetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.Favorite;

/**
 * Repositorio para operaciones de base de datos relacionadas con los favoritos.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    
    /**
     * Busca si una receta específica está marcada como favorita por un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return Optional con el favorito si existe
     */
    Optional<Favorite> findByRecipeIdAndUserId(Integer recipeId, Integer userId);
    
    /**
     * Verifica si una receta está marcada como favorita por un usuario.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     * @return true si la receta está en favoritos, false en caso contrario
     */
    boolean existsByRecipeIdAndUserId(Integer recipeId, Integer userId);
    
    /**
     * Busca todos los favoritos de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de favoritos del usuario
     */
    List<Favorite> findByUserId(Integer userId);
    
    /**
     * Busca todos los favoritos de un usuario específico ordenados por fecha de creación descendente.
     * 
     * @param userId ID del usuario
     * @return Lista de favoritos del usuario ordenados por fecha
     */
    List<Favorite> findByUserIdOrderByCreatedAtDesc(Integer userId);
    
    /**
     * Cuenta cuántos favoritos tiene un usuario.
     * 
     * @param userId ID del usuario
     * @return Número de favoritos del usuario
     */
    long countByUserId(Integer userId);
    
    /**
     * Busca todos los favoritos de una receta específica.
     * 
     * @param recipeId ID de la receta
     * @return Lista de favoritos de la receta
     */
    List<Favorite> findByRecipeId(Integer recipeId);
    
    /**
     * Cuenta cuántos usuarios han marcado una receta como favorita.
     * 
     * @param recipeId ID de la receta
     * @return Número de usuarios que han marcado la receta como favorita
     */
    long countByRecipeId(Integer recipeId);
    
    /**
     * Elimina un favorito específico.
     * 
     * @param recipeId ID de la receta
     * @param userId ID del usuario
     */
    void deleteByRecipeIdAndUserId(Integer recipeId, Integer userId);
    
    /**
     * Busca favoritos con información de receta usando JOIN.
     * 
     * @param userId ID del usuario
     * @return Lista de favoritos con información de receta
     */
    @Query("SELECT f FROM Favorite f JOIN FETCH f.recipe WHERE f.userId = :userId ORDER BY f.createdAt DESC")
    List<Favorite> findByUserIdWithRecipe(@Param("userId") Integer userId);
}
