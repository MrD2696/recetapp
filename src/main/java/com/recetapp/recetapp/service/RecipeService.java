package com.recetapp.recetapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recetapp.recetapp.constants.ExceptionConstants;
import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.dto.CategoryDTO;
import com.recetapp.recetapp.dto.CommentDTO;
import com.recetapp.recetapp.dto.MealDTO;
import com.recetapp.recetapp.dto.NutritionalInfoDTO;
import com.recetapp.recetapp.dto.RecipeWithCommentsDTO;
import com.recetapp.recetapp.dto.RecipeWithNutritionDTO;
import com.recetapp.recetapp.exception.NutritionException;
import com.recetapp.recetapp.exception.RecipeException;
import com.recetapp.recetapp.model.Category;
import com.recetapp.recetapp.model.Meal;
import com.recetapp.recetapp.model.NutriInformation;
import com.recetapp.recetapp.model.Recipe;
import com.recetapp.recetapp.repository.CategoryRepository;
import com.recetapp.recetapp.repository.MealRepository;
import com.recetapp.recetapp.repository.NutriInformationRepository;
import com.recetapp.recetapp.repository.RecipeCategoryRepository;
import com.recetapp.recetapp.repository.RecipeImageRepository;
import com.recetapp.recetapp.repository.RecipeMealRepository;
import com.recetapp.recetapp.repository.RecipeRepository;
import com.recetapp.recetapp.repository.RecipeWithNutritionRepository;

/**
 * Servicio para manejar operaciones relacionadas con recetas e información nutricional.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private NutriInformationRepository nutriInformationRepository;
    
    @Autowired
    private RecipeWithNutritionRepository recipeWithNutritionRepository;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private RecipeImageRepository recipeImageRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;
    
    @Autowired
    private RecipeMealRepository recipeMealRepository;
    
    /**
     * Obtiene todas las recetas de un usuario con su información nutricional.
     * 
     * @param userId ID del usuario
     * @return Lista de recetas con información nutricional
     */
    public List<RecipeWithNutritionDTO> getRecipesWithNutritionByUserId(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.GETTING_RECIPES_FOR_USER, userId)));
        
        try {
            List<Object[]> results = recipeWithNutritionRepository.findRecipesWithNutritionByUserId(userId);
            List<RecipeWithNutritionDTO> recipes = new ArrayList<>();
            
            for (Object[] result : results) {
                RecipeWithNutritionDTO recipe = mapToRecipeWithNutritionDTO(result);
                recipes.add(recipe);
            }
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.FOUND_RECIPES_FOR_USER, recipes.size(), userId)));
            return recipes;
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_GETTING_RECIPES_FOR_USER, userId, e.getMessage())));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_GETTING_RECIPES, e.getMessage()), e);
        }
    }
    
    /**
     * Obtiene una receta específica con su información nutricional.
     * 
     * @param recipeId ID de la receta
     * @return Receta con información nutricional
     */
    public RecipeWithNutritionDTO getRecipeWithNutritionById(Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.GETTING_RECIPE_BY_ID, recipeId)));
        
        try {
            Object[] result = recipeWithNutritionRepository.findRecipeWithNutritionById(recipeId);
            
            if (result == null) {
                System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.RECIPE_NOT_FOUND_LOG, recipeId)));
                throw new RecipeException(ExceptionConstants.format(ExceptionConstants.RECIPE_NOT_FOUND, recipeId));
            }
            
            RecipeWithNutritionDTO recipe = mapToRecipeWithNutritionDTO(result);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.RECIPE_FOUND, recipe.getTitle())));
            return recipe;
            
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_GETTING_RECIPE, recipeId, e.getMessage())));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_GETTING_RECIPES, e.getMessage()), e);
        }
    }
    
    /**
     * Busca recetas por título con información nutricional.
     * 
     * @param title Texto a buscar en el título
     * @param userId ID del usuario (opcional, null para buscar en todas las recetas)
     * @return Lista de recetas con información nutricional
     */
    public List<RecipeWithNutritionDTO> searchRecipesByTitle(String title, Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.SEARCHING_RECIPES_BY_TITLE, title, userId)));
        
        try {
            List<Object[]> results = recipeWithNutritionRepository.findRecipesWithNutritionByTitle(title, userId);
            List<RecipeWithNutritionDTO> recipes = new ArrayList<>();
            
            for (Object[] result : results) {
                RecipeWithNutritionDTO recipe = mapToRecipeWithNutritionDTO(result);
                recipes.add(recipe);
            }
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.FOUND_RECIPES_BY_TITLE, recipes.size(), title)));
            return recipes;
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_SEARCHING_RECIPES_BY_TITLE, title, e.getMessage())));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_SEARCHING_RECIPES, e.getMessage()), e);
        }
    }
    
    /**
     * Crea una nueva receta.
     * 
     * @param recipe Receta a crear
     * @return Receta creada
     */
    public Recipe createRecipe(Recipe recipe) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.CREATING_NEW_RECIPE, recipe.getTitle())));
        
        try {
            if (recipe.getCreatedAt() == null) {
                recipe.setCreatedAt(LocalDateTime.now());
            }
            
            Recipe savedRecipe = recipeRepository.save(recipe);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.RECIPE_CREATED_SUCCESSFULLY, savedRecipe.getRecipeId())));
            return savedRecipe;
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_CREATING_RECIPE_LOG, e.getMessage())));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_CREATING_RECIPE, e.getMessage()), e);
        }
    }
    
    /**
     * Actualiza una receta existente.
     * 
     * @param recipeId ID de la receta
     * @param recipe Datos actualizados de la receta
     * @return Receta actualizada
     */
    public Recipe updateRecipe(Integer recipeId, Recipe recipe) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.UPDATING_RECIPE, recipeId)));
        
        try {
            Recipe existingRecipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RecipeException(ExceptionConstants.format(ExceptionConstants.RECIPE_NOT_FOUND, recipeId)));
            
            // Actualizar campos
            existingRecipe.setTitle(recipe.getTitle());
            existingRecipe.setDescription(recipe.getDescription());
            existingRecipe.setPreparationTime(recipe.getPreparationTime());
            existingRecipe.setServings(recipe.getServings());
            existingRecipe.setIngredientsCount(recipe.getIngredientsCount());
            existingRecipe.setProced(recipe.getProced());
            
            Recipe updatedRecipe = recipeRepository.save(existingRecipe);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.RECIPE_UPDATED_SUCCESSFULLY, recipeId)));
            return updatedRecipe;
            
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_UPDATING_RECIPE_LOG, recipeId, e.getMessage())));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_UPDATING_RECIPE, e.getMessage()), e);
        }
    }
    
    /**
     * Elimina una receta y su información nutricional asociada.
     * 
     * @param recipeId ID de la receta
     */
    public void deleteRecipe(Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.DELETING_RECIPE, recipeId)));
        
        try {
            // Eliminar información nutricional primero
            if (nutriInformationRepository.existsByRecipeId(recipeId)) {
                nutriInformationRepository.deleteByRecipeId(recipeId);
                System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.NUTRITIONAL_INFO_DELETED, recipeId)));
            }
            
            // Eliminar imágenes de la receta
            if (recipeImageRepository.existsByRecipeId(recipeId)) {
                long imageCount = recipeImageRepository.countByRecipeId(recipeId);
                recipeImageRepository.deleteByRecipeId(recipeId);
                System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.IMAGES_DELETED_FOR_RECIPE, imageCount, recipeId)));
            }
            
            // Eliminar la receta
            recipeRepository.deleteById(recipeId);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.RECIPE_DELETED_SUCCESSFULLY, recipeId)));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_DELETING_RECIPE_LOG, recipeId, e.getMessage())));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_DELETING_RECIPE, e.getMessage()), e);
        }
    }
    
    /**
     * Guarda o actualiza la información nutricional de una receta.
     * 
     * @param nutriInformation Información nutricional a guardar
     * @return Información nutricional guardada
     */
    public NutriInformation saveNutritionalInfo(NutriInformation nutriInformation) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.SAVING_NUTRITIONAL_INFO, nutriInformation.getRecipeId())));
        
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(nutriInformation.getRecipeId())) {
                throw new NutritionException(ExceptionConstants.format(ExceptionConstants.RECIPE_NOT_FOUND, nutriInformation.getRecipeId()));
            }
            
            NutriInformation savedNutrition = nutriInformationRepository.save(nutriInformation);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.NUTRITIONAL_INFO_SAVED));
            return savedNutrition;
            
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_SAVING_NUTRITIONAL_INFO_LOG, e.getMessage())));
            throw new NutritionException(ExceptionConstants.format(ExceptionConstants.ERROR_SAVING_NUTRITIONAL_INFO, e.getMessage()), e);
        }
    }
    
    /**
     * Actualiza la información nutricional de una receta existente.
     * 
     * @param recipeId ID de la receta
     * @param nutriInformation Información nutricional actualizada
     * @return Información nutricional actualizada
     */
    public NutriInformation updateNutritionalInfo(Integer recipeId, NutriInformation nutriInformation) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.UPDATING_NUTRITIONAL_INFO, recipeId)));
        
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                throw new RuntimeException("Receta no encontrada con ID: " + recipeId);
            }
            
            // Verificar que la información nutricional existe
            NutriInformation existingNutrition = nutriInformationRepository.findByRecipeId(recipeId)
                    .orElseThrow(() -> new NutritionException(ExceptionConstants.format(ExceptionConstants.NUTRITIONAL_INFO_NOT_FOUND, recipeId)));
            
            // Actualizar campos
            existingNutrition.setCalories(nutriInformation.getCalories());
            existingNutrition.setFat(nutriInformation.getFat());
            existingNutrition.setSaturatedFat(nutriInformation.getSaturatedFat());
            existingNutrition.setPolyunsaturatedFat(nutriInformation.getPolyunsaturatedFat());
            existingNutrition.setMonounsaturatedFat(nutriInformation.getMonounsaturatedFat());
            existingNutrition.setCarbohydrates(nutriInformation.getCarbohydrates());
            existingNutrition.setSugar(nutriInformation.getSugar());
            existingNutrition.setFiber(nutriInformation.getFiber());
            existingNutrition.setProtein(nutriInformation.getProtein());
            existingNutrition.setSodium(nutriInformation.getSodium());
            
            NutriInformation updatedNutrition = nutriInformationRepository.save(existingNutrition);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.NUTRITIONAL_INFO_UPDATED));
            return updatedNutrition;
            
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, LogConstants.format(LogConstants.ERROR_UPDATING_NUTRITIONAL_INFO_LOG, e.getMessage())));
            throw new NutritionException(ExceptionConstants.format(ExceptionConstants.ERROR_UPDATING_NUTRITIONAL_INFO, e.getMessage()), e);
        }
    }
    
    /**
     * Obtiene todas las recetas de un usuario con comentarios.
     * 
     * @param userId ID del usuario
     * @return Lista de recetas con comentarios
     */
    public List<RecipeWithCommentsDTO> getRecipesWithCommentsByUserId(Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
            "Getting recipes with comments for user " + userId));
        
        try {
            List<Object[]> results = recipeWithNutritionRepository.findRecipesWithNutritionByUserId(userId);
            List<RecipeWithCommentsDTO> recipes = new ArrayList<>();
            
            for (Object[] result : results) {
                RecipeWithCommentsDTO recipe = mapToRecipeWithCommentsDTO(result);
                recipes.add(recipe);
            }
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
                "Found " + recipes.size() + " recipes with comments for user " + userId));
            return recipes;
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
                "Error getting recipes with comments for user " + userId + ": " + e.getMessage()));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_GETTING_RECIPES, e.getMessage()), e);
        }
    }
    
    /**
     * Obtiene una receta específica con comentarios.
     * 
     * @param recipeId ID de la receta
     * @return Receta con comentarios
     */
    public RecipeWithCommentsDTO getRecipeWithCommentsById(Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
            "Getting recipe with comments by ID " + recipeId));
        
        try {
            Object[] result = recipeWithNutritionRepository.findRecipeWithNutritionById(recipeId);
            
            if (result == null) {
                System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
                    "Recipe not found with ID: " + recipeId));
                throw new RecipeException(ExceptionConstants.format(ExceptionConstants.RECIPE_NOT_FOUND, recipeId));
            }
            
            RecipeWithCommentsDTO recipe = mapToRecipeWithCommentsDTO(result);
            System.out.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
                "Recipe found with comments: " + recipe.getTitle()));
            return recipe;
            
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.RECIPE_SERVICE_PREFIX, 
                "Error getting recipe with comments by ID " + recipeId + ": " + e.getMessage()));
            throw new RecipeException(ExceptionConstants.format(ExceptionConstants.ERROR_GETTING_RECIPES, e.getMessage()), e);
        }
    }
    
    /**
     * Mapea un array de objetos de la consulta SQL a un DTO con comentarios.
     * 
     * @param result Array de objetos de la consulta
     * @return DTO mapeado con comentarios
     */
    private RecipeWithCommentsDTO mapToRecipeWithCommentsDTO(Object[] result) {
        RecipeWithCommentsDTO dto = new RecipeWithCommentsDTO();
        
        // Mapear campos de la receta
        dto.setRecipeId((Integer) result[0]);
        dto.setUserId((Integer) result[1]);
        dto.setTitle((String) result[2]);
        dto.setDescription((String) result[3]);
        
        // Convertir java.sql.Timestamp a LocalDateTime
        if (result[4] instanceof java.sql.Timestamp) {
            java.sql.Timestamp timestamp = (java.sql.Timestamp) result[4];
            dto.setCreatedAt(timestamp.toLocalDateTime());
        } else if (result[4] != null) {
            dto.setCreatedAt((LocalDateTime) result[4]);
        }
        
        dto.setPreparationTime((String) result[5]);
        dto.setServings((Integer) result[6]);
        dto.setIngredientsCount((Integer) result[7]);
        dto.setProced((String) result[8]);
        
        // Crear información nutricional solo si existe
        if (result[9] != null) { // nutriId
            NutritionalInfoDTO nutritionalInfo = new NutritionalInfoDTO(
                (Integer) result[9],   // nutriId
                (String) result[10],   // calories
                (String) result[11],   // fat
                (String) result[12],   // saturatedFat
                (String) result[13],   // polyunsaturatedFat
                (String) result[14],   // monounsaturatedFat
                (String) result[15],   // carbohydrates
                (String) result[16],   // sugar
                (String) result[17],   // fiber
                (String) result[18],   // protein
                (String) result[19]    // sodium
            );
            
            List<NutritionalInfoDTO> nutritionalList = new ArrayList<>();
            nutritionalList.add(nutritionalInfo);
            dto.setNutritionalInfo(nutritionalList);
        } else {
            dto.setNutritionalInfo(new ArrayList<>());
        }
        
        // Obtener categorías vinculadas a la receta
        Integer recipeId = (Integer) result[0];
        List<CategoryDTO> categories = getCategoriesForRecipe(recipeId);
        dto.setCategories(categories);
        
        // Obtener meals vinculados a la receta
        List<MealDTO> meals = getMealsForRecipe(recipeId);
        dto.setMeals(meals);
        
        // Obtener comentarios de la receta
        List<CommentDTO> comments = commentService.getRecipeComments(recipeId);
        dto.setComments(comments);
        
        return dto;
    }
    
    /**
     * Mapea un array de objetos de la consulta SQL a un DTO.
     * 
     * @param result Array de objetos de la consulta
     * @return DTO mapeado
     */
    private RecipeWithNutritionDTO mapToRecipeWithNutritionDTO(Object[] result) {
        RecipeWithNutritionDTO dto = new RecipeWithNutritionDTO();
        
        // Mapear campos de la receta
        dto.setRecipeId((Integer) result[0]);
        dto.setUserId((Integer) result[1]);
        dto.setTitle((String) result[2]);
        dto.setDescription((String) result[3]);
        
        // Convertir java.sql.Timestamp a LocalDateTime
        if (result[4] instanceof java.sql.Timestamp) {
            java.sql.Timestamp timestamp = (java.sql.Timestamp) result[4];
            dto.setCreatedAt(timestamp.toLocalDateTime());
        } else if (result[4] != null) {
            dto.setCreatedAt((LocalDateTime) result[4]);
        }
        
        dto.setPreparationTime((String) result[5]);
        dto.setServings((Integer) result[6]);
        dto.setIngredientsCount((Integer) result[7]);
        dto.setProced((String) result[8]);
        
        // Crear información nutricional solo si existe
        if (result[9] != null) { // nutriId
            NutritionalInfoDTO nutritionalInfo = new NutritionalInfoDTO(
                (Integer) result[9],   // nutriId
                (String) result[10],   // calories
                (String) result[11],   // fat
                (String) result[12],   // saturatedFat
                (String) result[13],   // polyunsaturatedFat
                (String) result[14],   // monounsaturatedFat
                (String) result[15],   // carbohydrates
                (String) result[16],   // sugar
                (String) result[17],   // fiber
                (String) result[18],   // protein
                (String) result[19]    // sodium
            );
            
            List<NutritionalInfoDTO> nutritionalList = new ArrayList<>();
            nutritionalList.add(nutritionalInfo);
            dto.setNutritionalInfo(nutritionalList);
        } else {
            dto.setNutritionalInfo(new ArrayList<>());
        }
        
        // Obtener categorías vinculadas a la receta
        Integer recipeId = (Integer) result[0];
        List<CategoryDTO> categories = getCategoriesForRecipe(recipeId);
        dto.setCategories(categories);
        
        // Obtener meals vinculados a la receta
        List<MealDTO> meals = getMealsForRecipe(recipeId);
        dto.setMeals(meals);
        
        return dto;
    }
    
    /**
     * Obtiene las categorías vinculadas a una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de categorías
     */
    private List<CategoryDTO> getCategoriesForRecipe(Integer recipeId) {
        try {
            return recipeCategoryRepository.findByRecipeId(recipeId)
                .stream()
                .map(recipeCategory -> {
                    Category category = categoryRepository.findById(recipeCategory.getCategoryId()).orElse(null);
                    if (category != null) {
                        return new CategoryDTO(category.getCategoryId(), category.getName());
                    }
                    return null;
                })
                .filter(categoryDTO -> categoryDTO != null)
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error getting categories for recipe " + recipeId + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Obtiene los meals vinculados a una receta.
     * 
     * @param recipeId ID de la receta
     * @return Lista de meals
     */
    private List<MealDTO> getMealsForRecipe(Integer recipeId) {
        try {
            return recipeMealRepository.findByRecipeId(recipeId)
                .stream()
                .map(recipeMeal -> {
                    Meal meal = mealRepository.findById(recipeMeal.getMealId()).orElse(null);
                    if (meal != null) {
                        return new MealDTO(meal.getMealId(), meal.getName());
                    }
                    return null;
                })
                .filter(mealDTO -> mealDTO != null)
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error getting meals for recipe " + recipeId + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
