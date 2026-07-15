package com.recetapp.recetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.Meal;

/**
 * Repositorio para la entidad Meal.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
    
    /**
     * Busca un tipo de comida por su nombre.
     * 
     * @param name Nombre del tipo de comida
     * @return Optional con el tipo de comida encontrado
     */
    Optional<Meal> findByName(String name);
    
    /**
     * Busca tipos de comida que contengan el nombre especificado (búsqueda parcial).
     * 
     * @param name Nombre parcial del tipo de comida
     * @return Lista de tipos de comida que coinciden
     */
    List<Meal> findByNameContainingIgnoreCase(String name);
    
    /**
     * Verifica si existe un tipo de comida con el nombre especificado.
     * 
     * @param name Nombre del tipo de comida
     * @return true si existe, false en caso contrario
     */
    boolean existsByName(String name);
    
    /**
     * Busca tipos de comida ordenados por nombre.
     * 
     * @return Lista de tipos de comida ordenados alfabéticamente
     */
    List<Meal> findAllByOrderByNameAsc();
    
    /**
     * Cuenta cuántas recetas tiene cada tipo de comida.
     * 
     * @return Lista de arrays con [mealId, mealName, recipeCount]
     */
    @Query("SELECT m.mealId, m.name, COUNT(rm.recipeId) as recipeCount " +
           "FROM Meal m " +
           "LEFT JOIN RecipeMeal rm ON m.mealId = rm.mealId " +
           "GROUP BY m.mealId, m.name " +
           "ORDER BY recipeCount DESC")
    List<Object[]> findMealsWithRecipeCount();
}
