package com.recetapp.recetapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recetapp.recetapp.dto.CategoryDTO;
import com.recetapp.recetapp.dto.MealDTO;
import com.recetapp.recetapp.dto.RecipeCreateUpdateDTO;
import com.recetapp.recetapp.dto.RecipeDetailDTO;
import com.recetapp.recetapp.model.Recipe;
import com.recetapp.recetapp.model.RecipeCategory;
import com.recetapp.recetapp.model.RecipeMeal;
import com.recetapp.recetapp.model.User;
import com.recetapp.recetapp.repository.CategoryRepository;
import com.recetapp.recetapp.repository.MealRepository;
import com.recetapp.recetapp.repository.RecipeCategoryRepository;
import com.recetapp.recetapp.repository.RecipeMealRepository;
import com.recetapp.recetapp.repository.RecipeRepository;
import com.recetapp.recetapp.repository.UserRepository;

/**
 * Servicio para manejar la creación y actualización de recetas con categorías y tipos de comida.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class RecipeManagementService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;
    
    @Autowired
    private RecipeMealRepository recipeMealRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Crea una nueva receta con categorías y tipos de comida.
     * 
     * @param recipeDTO DTO con los datos de la receta
     * @return La receta creada
     */
    @Transactional
    public Recipe createRecipe(RecipeCreateUpdateDTO recipeDTO) {
        // Crear la receta básica
        Recipe recipe = new Recipe();
        recipe.setUserId(recipeDTO.getUserId());
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setPreparationTime(recipeDTO.getPreparationTime());
        recipe.setServings(recipeDTO.getServings());
        recipe.setIngredientsCount(recipeDTO.getIngredientsCount());
        recipe.setProced(recipeDTO.getProced());
        recipe.setCreatedAt(LocalDateTime.now());
        
        // Guardar la receta para obtener el ID
        Recipe savedRecipe = recipeRepository.save(recipe);
        
        // Asignar categorías
        if (recipeDTO.getCategoryIds() != null && !recipeDTO.getCategoryIds().isEmpty()) {
            assignCategoriesToRecipe(savedRecipe.getRecipeId(), recipeDTO.getCategoryIds());
        }
        
        // Asignar tipos de comida
        if (recipeDTO.getMealIds() != null && !recipeDTO.getMealIds().isEmpty()) {
            assignMealsToRecipe(savedRecipe.getRecipeId(), recipeDTO.getMealIds());
        }
        
        return savedRecipe;
    }
    
    /**
     * Actualiza una receta existente con nuevas categorías y tipos de comida.
     * 
     * @param recipeDTO DTO con los datos actualizados de la receta
     * @return La receta actualizada
     */
    @Transactional
    public Recipe updateRecipe(RecipeCreateUpdateDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        
        // Actualizar campos básicos
        existingRecipe.setTitle(recipeDTO.getTitle());
        existingRecipe.setDescription(recipeDTO.getDescription());
        existingRecipe.setPreparationTime(recipeDTO.getPreparationTime());
        existingRecipe.setServings(recipeDTO.getServings());
        existingRecipe.setIngredientsCount(recipeDTO.getIngredientsCount());
        existingRecipe.setProced(recipeDTO.getProced());
        
        // Eliminar categorías y tipos de comida existentes
        recipeCategoryRepository.deleteByRecipeId(existingRecipe.getRecipeId());
        recipeMealRepository.deleteByRecipeId(existingRecipe.getRecipeId());
        
        // Asignar nuevas categorías
        if (recipeDTO.getCategoryIds() != null && !recipeDTO.getCategoryIds().isEmpty()) {
            assignCategoriesToRecipe(existingRecipe.getRecipeId(), recipeDTO.getCategoryIds());
        }
        
        // Asignar nuevos tipos de comida
        if (recipeDTO.getMealIds() != null && !recipeDTO.getMealIds().isEmpty()) {
            assignMealsToRecipe(existingRecipe.getRecipeId(), recipeDTO.getMealIds());
        }
        
        return recipeRepository.save(existingRecipe);
    }
    
    /**
     * Asigna categorías a una receta.
     * 
     * @param recipeId ID de la receta
     * @param categoryIds Lista de IDs de categorías
     */
    private void assignCategoriesToRecipe(Integer recipeId, List<Integer> categoryIds) {
        for (Integer categoryId : categoryIds) {
            if (categoryRepository.existsById(categoryId)) {
                RecipeCategory recipeCategory = new RecipeCategory(recipeId, categoryId);
                recipeCategoryRepository.save(recipeCategory);
            }
        }
    }
    
    /**
     * Asigna tipos de comida a una receta.
     * 
     * @param recipeId ID de la receta
     * @param mealIds Lista de IDs de tipos de comida
     */
    private void assignMealsToRecipe(Integer recipeId, List<Integer> mealIds) {
        for (Integer mealId : mealIds) {
            if (mealRepository.existsById(mealId)) {
                RecipeMeal recipeMeal = new RecipeMeal(recipeId, mealId);
                recipeMealRepository.save(recipeMeal);
            }
        }
    }
    
    /**
     * Obtiene una receta con información completa incluyendo categorías y tipos de comida.
     * 
     * @param recipeId ID de la receta
     * @return DTO con información completa de la receta
     */
    public RecipeDetailDTO getRecipeDetail(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        
        // Obtener categorías
        List<CategoryDTO> categories = recipeCategoryRepository.findByRecipeId(recipeId)
                .stream()
                .map(rc -> new CategoryDTO(rc.getCategory().getCategoryId(), rc.getCategory().getName()))
                .collect(Collectors.toList());
        
        // Obtener tipos de comida
        List<MealDTO> meals = recipeMealRepository.findByRecipeId(recipeId)
                .stream()
                .map(rm -> new MealDTO(rm.getMeal().getMealId(), rm.getMeal().getName()))
                .collect(Collectors.toList());
        
        // Obtener nombre del usuario
        String userName = userRepository.findById(recipe.getUserId())
                .map(User::getUsername)
                .orElse("Usuario desconocido");
        
        // TODO: Implementar generación automática de tags basada en análisis de texto
        List<String> tags = generateTagsFromRecipe(recipe);
        
        return new RecipeDetailDTO(
                recipe.getRecipeId(),
                recipe.getUserId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getCreatedAt(),
                recipe.getPreparationTime(),
                recipe.getServings(),
                recipe.getIngredientsCount(),
                recipe.getProced(),
                userName,
                categories,
                meals,
                tags,
                null // TODO: Implementar información nutricional
        );
    }
    
    /**
     * Genera tags automáticamente basándose en el contenido de la receta.
     * 
     * @param recipe La receta para analizar
     * @return Lista de tags generados
     */
    private List<String> generateTagsFromRecipe(Recipe recipe) {
        // TODO: Implementar lógica de análisis de texto para generar tags
        // Por ahora retornamos una lista vacía
        return new ArrayList<>();
    }
}
