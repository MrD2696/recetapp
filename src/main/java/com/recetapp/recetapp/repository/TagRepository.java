package com.recetapp.recetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.Tag;

/**
 * Repositorio para la entidad Tag.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    
    /**
     * Busca un tag por su nombre.
     * 
     * @param name Nombre del tag
     * @return Optional con el tag encontrado
     */
    Optional<Tag> findByName(String name);
    
    /**
     * Busca tags que contengan el nombre especificado (búsqueda parcial).
     * 
     * @param name Nombre parcial del tag
     * @return Lista de tags que coinciden
     */
    List<Tag> findByNameContainingIgnoreCase(String name);
    
    /**
     * Verifica si existe un tag con el nombre especificado.
     * 
     * @param name Nombre del tag
     * @return true si existe, false en caso contrario
     */
    boolean existsByName(String name);
    
    /**
     * Busca tags ordenados por nombre.
     * 
     * @return Lista de tags ordenados alfabéticamente
     */
    List<Tag> findAllByOrderByNameAsc();
    
    /**
     * Cuenta cuántas recetas tiene cada tag.
     * 
     * @return Lista de arrays con [tagId, tagName, recipeCount]
     */
    @Query("SELECT t.tagId, t.name, COUNT(rt.recipeId) as recipeCount " +
           "FROM Tag t " +
           "LEFT JOIN RecipeTag rt ON t.tagId = rt.tagId " +
           "GROUP BY t.tagId, t.name " +
           "ORDER BY recipeCount DESC")
    List<Object[]> findTagsWithRecipeCount();
}
