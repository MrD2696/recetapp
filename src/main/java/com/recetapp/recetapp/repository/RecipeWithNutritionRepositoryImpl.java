package com.recetapp.recetapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Implementación del repositorio personalizado para consultas que combinan recetas con información nutricional.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Repository
public class RecipeWithNutritionRepositoryImpl implements RecipeWithNutritionRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Object[]> findRecipesWithNutritionByUserId(Integer userId) {
        String sql = "SELECT " +
            "   r.recipeId, r.userId, r.title, r.description, r.createdAt, " +
            "   r.preparationTime, r.servings, r.ingredientsCount, r.proced, " +
            "   n.nutriId, n.calories, n.fat, n.saturatedFat, n.polyunsaturatedFat, " +
            "   n.monounsaturatedFat, n.carbohydrates, n.sugar, n.fiber, n.protein, n.sodium " +
            "FROM Recipes r " +
            "LEFT JOIN NutriInformation n ON r.recipeId = n.recipeId " +
            "WHERE r.userId = :userId " +
            "ORDER BY r.createdAt DESC";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        return results;
    }
    
    @Override
    public Object[] findRecipeWithNutritionById(Integer recipeId) {
        String sql = "SELECT " +
            "   r.recipeId, r.userId, r.title, r.description, r.createdAt, " +
            "   r.preparationTime, r.servings, r.ingredientsCount, r.proced, " +
            "   n.nutriId, n.calories, n.fat, n.saturatedFat, n.polyunsaturatedFat, " +
            "   n.monounsaturatedFat, n.carbohydrates, n.sugar, n.fiber, n.protein, n.sodium " +
            "FROM Recipes r " +
            "LEFT JOIN NutriInformation n ON r.recipeId = n.recipeId " +
            "WHERE r.recipeId = :recipeId";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("recipeId", recipeId);
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        
        if (results.isEmpty()) {
            return null;
        }
        
        return results.get(0);
    }
    
    @Override
    public List<Object[]> findRecipesWithNutritionByTitle(String title, Integer userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("   r.recipeId, r.userId, r.title, r.description, r.createdAt, ");
        sql.append("   r.preparationTime, r.servings, r.ingredientsCount, r.proced, ");
        sql.append("   n.nutriId, n.calories, n.fat, n.saturatedFat, n.polyunsaturatedFat, ");
        sql.append("   n.monounsaturatedFat, n.carbohydrates, n.sugar, n.fiber, n.protein, n.sodium ");
        sql.append("FROM Recipes r ");
        sql.append("LEFT JOIN NutriInformation n ON r.recipeId = n.recipeId ");
        sql.append("WHERE r.title LIKE :title ");
        
        if (userId != null) {
            sql.append("   AND r.userId = :userId ");
        }
        
        sql.append("ORDER BY r.createdAt DESC");
        
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("title", "%" + title + "%");
        
        if (userId != null) {
            query.setParameter("userId", userId);
        }
        
        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        return results;
    }
}
