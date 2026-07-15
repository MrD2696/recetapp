package com.recetapp.recetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.NutriInformation;

/**
 * Repositorio para operaciones de base de datos relacionadas con la información nutricional.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface NutriInformationRepository extends JpaRepository<NutriInformation, Integer> {
    
    /**
     * Busca la información nutricional por ID de receta.
     * 
     * @param recipeId ID de la receta
     * @return Información nutricional de la receta
     */
    Optional<NutriInformation> findByRecipeId(Integer recipeId);
    
    /**
     * Busca información nutricional para múltiples recetas.
     * 
     * @param recipeIds Lista de IDs de recetas
     * @return Lista de información nutricional
     */
    List<NutriInformation> findByRecipeIdIn(List<Integer> recipeIds);
    
    /**
     * Verifica si existe información nutricional para una receta específica.
     * 
     * @param recipeId ID de la receta
     * @return true si existe información nutricional, false en caso contrario
     */
    boolean existsByRecipeId(Integer recipeId);
    
    /**
     * Elimina la información nutricional de una receta específica.
     * 
     * @param recipeId ID de la receta
     */
    void deleteByRecipeId(Integer recipeId);
}
