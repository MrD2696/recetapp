package com.recetapp.recetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.Category;

/**
 * Repositorio para la entidad Category.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    /**
     * Busca una categoría por su nombre.
     * 
     * @param name Nombre de la categoría
     * @return Optional con la categoría encontrada
     */
    Optional<Category> findByName(String name);
    
    /**
     * Busca categorías que contengan el nombre especificado (búsqueda parcial).
     * 
     * @param name Nombre parcial de la categoría
     * @return Lista de categorías que coinciden
     */
    List<Category> findByNameContainingIgnoreCase(String name);
    
    /**
     * Verifica si existe una categoría con el nombre especificado.
     * 
     * @param name Nombre de la categoría
     * @return true si existe, false en caso contrario
     */
    boolean existsByName(String name);
    
    /**
     * Busca categorías ordenadas por nombre.
     * 
     * @return Lista de categorías ordenadas alfabéticamente
     */
    List<Category> findAllByOrderByNameAsc();
    
    /**
     * Cuenta cuántas recetas tiene cada categoría.
     * 
     * @return Lista de arrays con [categoryId, categoryName, recipeCount]
     */
    @Query("SELECT c.categoryId, c.name, COUNT(rc.recipeId) as recipeCount " +
           "FROM Category c " +
           "LEFT JOIN RecipeCategory rc ON c.categoryId = rc.categoryId " +
           "GROUP BY c.categoryId, c.name " +
           "ORDER BY recipeCount DESC")
    List<Object[]> findCategoriesWithRecipeCount();
}
