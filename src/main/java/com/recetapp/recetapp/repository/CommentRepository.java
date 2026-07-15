package com.recetapp.recetapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.recetapp.recetapp.model.Comment;

/**
 * Repositorio para operaciones de base de datos relacionadas con los comentarios.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    
    /**
     * Busca todos los comentarios de una receta específica ordenados por fecha de creación.
     * 
     * @param recipeId ID de la receta
     * @return Lista de comentarios de la receta
     */
    List<Comment> findByRecipeIdOrderByCreatedAtDesc(Integer recipeId);
    
    /**
     * Busca todos los comentarios de una receta específica con paginación.
     * 
     * @param recipeId ID de la receta
     * @param pageable Configuración de paginación
     * @return Página de comentarios de la receta
     */
    Page<Comment> findByRecipeIdOrderByCreatedAtDesc(Integer recipeId, Pageable pageable);
    
    /**
     * Busca todos los comentarios de un usuario específico ordenados por fecha de creación.
     * 
     * @param userId ID del usuario
     * @return Lista de comentarios del usuario
     */
    List<Comment> findByUserIdOrderByCreatedAtDesc(Integer userId);
    
    /**
     * Busca todos los comentarios de un usuario específico con paginación.
     * 
     * @param userId ID del usuario
     * @param pageable Configuración de paginación
     * @return Página de comentarios del usuario
     */
    Page<Comment> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
    
    /**
     * Cuenta cuántos comentarios tiene una receta.
     * 
     * @param recipeId ID de la receta
     * @return Número de comentarios de la receta
     */
    long countByRecipeId(Integer recipeId);
    
    /**
     * Cuenta cuántos comentarios ha hecho un usuario.
     * 
     * @param userId ID del usuario
     * @return Número de comentarios del usuario
     */
    long countByUserId(Integer userId);
    
    /**
     * Busca comentarios con información de usuario usando JOIN.
     * 
     * @param recipeId ID de la receta
     * @return Lista de comentarios con información de usuario
     */
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.recipeId = :recipeId ORDER BY c.createdAt DESC")
    List<Comment> findByRecipeIdWithUser(@Param("recipeId") Integer recipeId);
    
    /**
     * Busca comentarios con información de receta usando JOIN.
     * 
     * @param userId ID del usuario
     * @return Lista de comentarios con información de receta
     */
    @Query("SELECT c FROM Comment c JOIN FETCH c.recipe WHERE c.userId = :userId ORDER BY c.createdAt DESC")
    List<Comment> findByUserIdWithRecipe(@Param("userId") Integer userId);
    
    /**
     * Busca comentarios por contenido (búsqueda de texto).
     * 
     * @param content Contenido a buscar
     * @return Lista de comentarios que contienen el texto
     */
    @Query("SELECT c FROM Comment c WHERE LOWER(c.content) LIKE LOWER(CONCAT('%', :content, '%')) ORDER BY c.createdAt DESC")
    List<Comment> findByContentContainingIgnoreCase(@Param("content") String content);
    
    /**
     * Busca comentarios recientes (últimos X días).
     * 
     * @param days Número de días hacia atrás
     * @return Lista de comentarios recientes
     */
    @Query(value = "SELECT c.* FROM Comments c WHERE c.createdAt >= DATEADD(day, -:days, GETDATE()) ORDER BY c.createdAt DESC", nativeQuery = true)
    List<Comment> findRecentComments(@Param("days") int days);
    
    /**
     * Busca comentarios recientes con información de usuario (últimos X días).
     * 
     * @param days Número de días hacia atrás
     * @return Lista de comentarios recientes con información de usuario
     */
    @Query(value = "SELECT c.commentId, c.recipeId, c.userId, c.content, c.createdAt, u.username, u.email " +
                   "FROM Comments c " +
                   "JOIN Users u ON c.userId = u.userId " +
                   "WHERE c.createdAt >= DATEADD(day, -:days, GETDATE()) " +
                   "ORDER BY c.createdAt DESC", nativeQuery = true)
    List<Object[]> findRecentCommentsWithUser(@Param("days") int days);
    
    /**
     * Busca comentarios de una receta con información de usuario y paginación.
     * 
     * @param recipeId ID de la receta
     * @param pageable Configuración de paginación
     * @return Página de comentarios con información de usuario
     */
    @Query("SELECT c FROM Comment c WHERE c.recipeId = :recipeId ORDER BY c.createdAt DESC")
    Page<Comment> findByRecipeIdWithUserPaged(@Param("recipeId") Integer recipeId, Pageable pageable);
}
