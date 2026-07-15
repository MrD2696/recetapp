package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.Recipe;

/**
 * Repositorio para operaciones de base de datos relacionadas con las recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    
    /**
     * Busca todas las recetas de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Lista de recetas del usuario
     */
    List<Recipe> findByUserId(Integer userId);
    
    /**
     * Busca todas las recetas de un usuario específico ordenadas por fecha de creación descendente.
     * 
     * @param userId ID del usuario
     * @return Lista de recetas del usuario ordenadas por fecha de creación
     */
    List<Recipe> findByUserIdOrderByCreatedAtDesc(Integer userId);
    
    /**
     * Busca recetas por título que contengan el texto especificado.
     * 
     * @param title Texto a buscar en el título
     * @return Lista de recetas que coincidan con el título
     */
    List<Recipe> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Busca recetas por título y usuario específico.
     * 
     * @param title Texto a buscar en el título
     * @param userId ID del usuario
     * @return Lista de recetas que coincidan con el título y usuario
     */
    List<Recipe> findByTitleContainingIgnoreCaseAndUserId(String title, Integer userId);
    
    /**
     * Cuenta el número de recetas de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return Número de recetas del usuario
     */
    long countByUserId(Integer userId);
    
    /**
     * Verifica si existe al menos una receta para un usuario específico.
     * 
     * @param userId ID del usuario
     * @return true si el usuario tiene recetas, false en caso contrario
     */
    boolean existsByUserId(Integer userId);
}
