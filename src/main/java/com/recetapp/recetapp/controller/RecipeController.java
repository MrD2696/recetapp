package com.recetapp.recetapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.CategoryDTO;
import com.recetapp.recetapp.dto.MealDTO;
import com.recetapp.recetapp.dto.RecipeWithCommentsDTO;
import com.recetapp.recetapp.dto.RecipeWithNutritionDTO;
import com.recetapp.recetapp.model.Category;
import com.recetapp.recetapp.model.Meal;
import com.recetapp.recetapp.model.NutriInformation;
import com.recetapp.recetapp.model.Recipe;
import com.recetapp.recetapp.model.RecipeCategory;
import com.recetapp.recetapp.model.RecipeMeal;
import com.recetapp.recetapp.repository.CategoryRepository;
import com.recetapp.recetapp.repository.MealRepository;
import com.recetapp.recetapp.repository.RecipeCategoryRepository;
import com.recetapp.recetapp.repository.RecipeMealRepository;
import com.recetapp.recetapp.repository.RecipeRepository;
import com.recetapp.recetapp.service.RecipeService;

/**
 * Controlador para manejar operaciones relacionadas con recetas e información nutricional.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    
    @Autowired
    private RecipeService recipeService;
    
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
    
    /**
     * Obtiene todas las recetas de un usuario con su información nutricional.
     * 
     * @param userId ID del usuario
     * @return Lista de recetas con información nutricional
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<RecipeWithNutritionDTO>>> getRecipesByUserId(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/recipes/user/" + userId + " - Retrieving user recipes"));
        
        try {
            List<RecipeWithNutritionDTO> recipes = recipeService.getRecipesWithNutritionByUserId(userId);
            
            ApiResponse<List<RecipeWithNutritionDTO>> response = ApiResponse.success(
                MessageConstants.RECIPES_RETRIEVED_SUCCESSFULLY, 
                recipes
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Retrieved " + recipes.size() + " recipes for user " + userId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error retrieving recipes for user " + userId + ": " + e.getMessage()));
            
            ApiResponse<List<RecipeWithNutritionDTO>> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_RECIPES + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene una receta específica con su información nutricional.
     * 
     * @param recipeId ID de la receta
     * @return Receta con información nutricional
     */
    @GetMapping("/{recipeId}")
    public ResponseEntity<ApiResponse<RecipeWithNutritionDTO>> getRecipeById(@PathVariable Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/recipes/" + recipeId + " - Retrieving specific recipe"));
        
        try {
            RecipeWithNutritionDTO recipe = recipeService.getRecipeWithNutritionById(recipeId);
            
            ApiResponse<RecipeWithNutritionDTO> response = ApiResponse.success(
                MessageConstants.RECIPE_RETRIEVED_SUCCESSFULLY, 
                recipe
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Recipe retrieved: " + recipe.getTitle()));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error retrieving recipe " + recipeId + ": " + e.getMessage()));
            
            ApiResponse<RecipeWithNutritionDTO> response = ApiResponse.error(
                MessageConstants.ERROR_RETRIEVING_RECIPE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Busca recetas por título con información nutricional.
     * 
     * @param title Texto a buscar en el título
     * @param userId ID del usuario (opcional)
     * @return Lista de recetas que coincidan con la búsqueda
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RecipeWithNutritionDTO>>> searchRecipesByTitle(
            @RequestParam String title, 
            @RequestParam(required = false) Integer userId) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/recipes/search?title=" + title + "&userId=" + userId + " - Searching recipes"));
        
        try {
            List<RecipeWithNutritionDTO> recipes = recipeService.searchRecipesByTitle(title, userId);
            
            ApiResponse<List<RecipeWithNutritionDTO>> response = ApiResponse.success(
                MessageConstants.SEARCH_COMPLETED_SUCCESSFULLY, 
                recipes
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Found " + recipes.size() + " recipes with title '" + title + "'"));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error searching recipes with title '" + title + "': " + e.getMessage()));
            
            ApiResponse<List<RecipeWithNutritionDTO>> response = ApiResponse.error(
                MessageConstants.ERROR_IN_SEARCH + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Crea una nueva receta.
     * 
     * @param recipe Datos de la receta a crear
     * @return Receta creada
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Recipe>> createRecipe(@RequestBody Recipe recipe) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/recipes - Creating new recipe: " + recipe.getTitle()));
        
        try {
            Recipe createdRecipe = recipeService.createRecipe(recipe);
            
            ApiResponse<Recipe> response = ApiResponse.success(
                MessageConstants.RECIPE_CREATED_SUCCESSFULLY, 
                createdRecipe
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Recipe created with ID: " + createdRecipe.getRecipeId()));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error creating recipe: " + e.getMessage()));
            
            ApiResponse<Recipe> response = ApiResponse.error(
                MessageConstants.ERROR_CREATING_RECIPE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Actualiza una receta existente.
     * 
     * @param recipeId ID de la receta
     * @param recipe Datos actualizados de la receta
     * @return Receta actualizada
     */
    @PutMapping("/{recipeId}")
    public ResponseEntity<ApiResponse<Recipe>> updateRecipe(
            @PathVariable Integer recipeId, 
            @RequestBody Recipe recipe) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "PUT /api/recipes/" + recipeId + " - Updating recipe"));
        
        try {
            Recipe updatedRecipe = recipeService.updateRecipe(recipeId, recipe);
            
            ApiResponse<Recipe> response = ApiResponse.success(
                MessageConstants.RECIPE_UPDATED_SUCCESSFULLY, 
                updatedRecipe
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Recipe updated: " + recipeId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error updating recipe " + recipeId + ": " + e.getMessage()));
            
            ApiResponse<Recipe> response = ApiResponse.error(
                MessageConstants.ERROR_UPDATING_RECIPE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Elimina una receta y su información nutricional asociada.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<ApiResponse<String>> deleteRecipe(@PathVariable Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "DELETE /api/recipes/" + recipeId + " - Deleting recipe"));
        
        try {
            recipeService.deleteRecipe(recipeId);
            
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.RECIPE_DELETED_SUCCESSFULLY, 
                "Recipe with ID " + recipeId + " has been successfully deleted"
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Recipe deleted: " + recipeId));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error deleting recipe " + recipeId + ": " + e.getMessage()));
            
            ApiResponse<String> response = ApiResponse.error(
                MessageConstants.ERROR_DELETING_RECIPE + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Guarda o actualiza la información nutricional de una receta.
     * 
     * @param nutriInformation Información nutricional a guardar
     * @return Información nutricional guardada
     */
    @PostMapping("{recipeId}/nutrition")
    public ResponseEntity<ApiResponse<NutriInformation>> saveNutritionalInfo(@RequestBody NutriInformation nutriInformation) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/recipes/nutrition - Saving nutritional information for recipe: " + nutriInformation.getRecipeId()));
        
        try {
            NutriInformation savedNutrition = recipeService.saveNutritionalInfo(nutriInformation);
            
            ApiResponse<NutriInformation> response = ApiResponse.success(
                MessageConstants.NUTRITION_INFO_CREATED_SUCCESSFULLY, 
                savedNutrition
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Nutritional information saved for recipe: " + nutriInformation.getRecipeId()));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error saving nutritional information: " + e.getMessage()));
            
            ApiResponse<NutriInformation> response = ApiResponse.error(
                MessageConstants.ERROR_CREATING_NUTRITION + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Actualiza la información nutricional de una receta existente.
     * 
     * @param recipeId ID de la receta
     * @param nutriInformation Información nutricional actualizada
     * @return Información nutricional actualizada
     */
    @PutMapping("/{recipeId}/nutrition")
    public ResponseEntity<ApiResponse<NutriInformation>> updateNutritionalInfo(
            @PathVariable Integer recipeId,
            @RequestBody NutriInformation nutriInformation) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "PUT /api/recipes/" + recipeId + "/nutrition - Updating nutritional information"));
        
        try {
            NutriInformation updatedNutrition = recipeService.updateNutritionalInfo(recipeId, nutriInformation);
            
            ApiResponse<NutriInformation> response = ApiResponse.success(
                MessageConstants.NUTRITION_INFO_UPDATED_SUCCESSFULLY, 
                updatedNutrition
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Nutritional information updated with ID: " + updatedNutrition.getNutriId()));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error updating nutritional information: " + e.getMessage()));
            
            ApiResponse<NutriInformation> response = ApiResponse.error(
                MessageConstants.ERROR_UPDATING_NUTRITION + ": " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Guarda o actualiza categorías para una receta.
     * 
     * @param recipeId ID de la receta
     * @param categoryData Datos de la categoría a guardar
     * @return Categoría guardada
     */
    @PostMapping("/{recipeId}/categories")
    public ResponseEntity<ApiResponse<CategoryDTO>> saveCategoryForRecipe(
            @PathVariable Integer recipeId,
            @RequestBody CategoryDTO categoryData) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/recipes/" + recipeId + "/categories - Saving category for recipe"));
        System.out.println("Category data received: " + categoryData.getName());
        
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                System.err.println("Recipe with ID " + recipeId + " not found");
                ApiResponse<CategoryDTO> response = ApiResponse.error(
                    "Recipe not found with ID: " + recipeId
                );
                return ResponseEntity.badRequest().body(response);
            }
            System.out.println("Recipe with ID " + recipeId + " exists");
            
            // Verificar si la categoría ya existe por nombre
            Optional<Category> existingCategoryOpt = categoryRepository.findByName(categoryData.getName());
            
            if (existingCategoryOpt.isPresent()) {
                // La categoría ya existe, solo vincularla a la receta
                Category existingCategory = existingCategoryOpt.get();
                System.out.println("Category '" + existingCategory.getName() + "' already exists with ID: " + existingCategory.getCategoryId());
                
                if (!recipeCategoryRepository.existsByRecipeIdAndCategoryId(recipeId, existingCategory.getCategoryId())) {
                    System.out.println("Creating link between recipe " + recipeId + " and category " + existingCategory.getCategoryId());
                    RecipeCategory recipeCategory = new RecipeCategory(recipeId, existingCategory.getCategoryId());
                    RecipeCategory savedRecipeCategory = recipeCategoryRepository.save(recipeCategory);
                    System.out.println("Link created with ID: " + savedRecipeCategory.getRecipeCategoryId());
                } else {
                    System.out.println("Link already exists between recipe " + recipeId + " and category " + existingCategory.getCategoryId());
                }
                
                CategoryDTO responseDTO = new CategoryDTO(existingCategory.getCategoryId(), existingCategory.getName());
                ApiResponse<CategoryDTO> response = ApiResponse.success(
                    "Category '" + existingCategory.getName() + "' already exists and has been linked to recipe " + recipeId,
                    responseDTO
                );
                return ResponseEntity.ok(response);
            } else {
                // Crear nueva categoría
                System.out.println("Category '" + categoryData.getName() + "' does not exist, creating new one");
                Category newCategory = new Category();
                newCategory.setName(categoryData.getName());
                Category savedCategory = categoryRepository.save(newCategory);
                System.out.println("New category created with ID: " + savedCategory.getCategoryId());
                
                // Vincular la nueva categoría a la receta
                System.out.println("Creating link between recipe " + recipeId + " and new category " + savedCategory.getCategoryId());
                RecipeCategory recipeCategory = new RecipeCategory(recipeId, savedCategory.getCategoryId());
                RecipeCategory savedRecipeCategory = recipeCategoryRepository.save(recipeCategory);
                System.out.println("Link created with ID: " + savedRecipeCategory.getRecipeCategoryId());
                
                CategoryDTO responseDTO = new CategoryDTO(savedCategory.getCategoryId(), savedCategory.getName());
                ApiResponse<CategoryDTO> response = ApiResponse.success(
                    "New category '" + savedCategory.getName() + "' created and linked to recipe " + recipeId,
                    responseDTO
                );
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error saving category for recipe " + recipeId + ": " + e.getMessage()));
            e.printStackTrace(); // Agregar stack trace completo
            
            ApiResponse<CategoryDTO> response = ApiResponse.error(
                "Error saving category: " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Guarda o actualiza meals para una receta.
     * 
     * @param recipeId ID de la receta
     * @param mealData Datos del meal a guardar
     * @return Meal guardado
     */
    @PostMapping("/{recipeId}/meals")
    public ResponseEntity<ApiResponse<MealDTO>> saveMealForRecipe(
            @PathVariable Integer recipeId,
            @RequestBody MealDTO mealData) {
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/recipes/" + recipeId + "/meals - Saving meal for recipe"));
        System.out.println("Meal data received: " + mealData.getName());
        
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                System.err.println("Recipe with ID " + recipeId + " not found");
                ApiResponse<MealDTO> response = ApiResponse.error(
                    "Recipe not found with ID: " + recipeId
                );
                return ResponseEntity.badRequest().body(response);
            }
            System.out.println("Recipe with ID " + recipeId + " exists");
            
            // Verificar si el meal ya existe por nombre
            Optional<Meal> existingMealOpt = mealRepository.findByName(mealData.getName());
            
            if (existingMealOpt.isPresent()) {
                // El meal ya existe, solo vincularlo a la receta
                Meal existingMeal = existingMealOpt.get();
                System.out.println("Meal '" + existingMeal.getName() + "' already exists with ID: " + existingMeal.getMealId());
                
                if (!recipeMealRepository.existsByRecipeIdAndMealId(recipeId, existingMeal.getMealId())) {
                    System.out.println("Creating link between recipe " + recipeId + " and meal " + existingMeal.getMealId());
                    RecipeMeal recipeMeal = new RecipeMeal(recipeId, existingMeal.getMealId());
                    RecipeMeal savedRecipeMeal = recipeMealRepository.save(recipeMeal);
                    System.out.println("Link created with ID: " + savedRecipeMeal.getRecipeMealId());
                } else {
                    System.out.println("Link already exists between recipe " + recipeId + " and meal " + existingMeal.getMealId());
                }
                
                MealDTO responseDTO = new MealDTO(existingMeal.getMealId(), existingMeal.getName());
                ApiResponse<MealDTO> response = ApiResponse.success(
                    "Meal '" + existingMeal.getName() + "' already exists and has been linked to recipe " + recipeId,
                    responseDTO
                );
                return ResponseEntity.ok(response);
            } else {
                // Crear nuevo meal
                System.out.println("Meal '" + mealData.getName() + "' does not exist, creating new one");
                Meal newMeal = new Meal();
                newMeal.setName(mealData.getName());
                Meal savedMeal = mealRepository.save(newMeal);
                System.out.println("New meal created with ID: " + savedMeal.getMealId());
                
                // Vincular el nuevo meal a la receta
                System.out.println("Creating link between recipe " + recipeId + " and new meal " + savedMeal.getMealId());
                RecipeMeal recipeMeal = new RecipeMeal(recipeId, savedMeal.getMealId());
                RecipeMeal savedRecipeMeal = recipeMealRepository.save(recipeMeal);
                System.out.println("Link created with ID: " + savedRecipeMeal.getRecipeMealId());
                
                MealDTO responseDTO = new MealDTO(savedMeal.getMealId(), savedMeal.getName());
                ApiResponse<MealDTO> response = ApiResponse.success(
                    "New meal '" + savedMeal.getName() + "' created and linked to recipe " + recipeId,
                    responseDTO
                );
                return ResponseEntity.ok(response);
            }
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error saving meal for recipe " + recipeId + ": " + e.getMessage()));
            e.printStackTrace(); // Agregar stack trace completo
            
            ApiResponse<MealDTO> response = ApiResponse.error(
                "Error saving meal: " + e.getMessage()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene todas las recetas de un usuario con comentarios.
     * 
     * @param userId ID del usuario
     * @return Respuesta con la lista de recetas con comentarios
     */
    @GetMapping("/user/{userId}/with-comments")
    public ResponseEntity<ApiResponse<List<RecipeWithCommentsDTO>>> getRecipesWithCommentsByUserId(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/recipes/user/" + userId + "/with-comments - Getting recipes with comments for user"));
        
        try {
            List<RecipeWithCommentsDTO> recipes = recipeService.getRecipesWithCommentsByUserId(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Found " + recipes.size() + " recipes with comments for user " + userId));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipes with comments retrieved successfully", 
                recipes
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipes with comments for user " + userId + ": " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipes with comments: " + e.getMessage()
            ));
        }
    }
    
    /**
     * Obtiene una receta específica con comentarios.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta con la receta y sus comentarios
     */
    @GetMapping("/{recipeId}/with-comments")
    public ResponseEntity<ApiResponse<RecipeWithCommentsDTO>> getRecipeWithCommentsById(@PathVariable Integer recipeId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
            "GET /api/recipes/" + recipeId + "/with-comments - Getting recipe with comments"));
        
        try {
            RecipeWithCommentsDTO recipe = recipeService.getRecipeWithCommentsById(recipeId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Recipe with comments found: " + recipe.getTitle()));
            
            return ResponseEntity.ok(ApiResponse.success(
                "Recipe with comments retrieved successfully", 
                recipe
            ));
            
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, 
                "Error getting recipe with comments by ID " + recipeId + ": " + e.getMessage()));
            
            return ResponseEntity.badRequest().body(ApiResponse.error(
                "Error getting recipe with comments: " + e.getMessage()
            ));
        }
    }
}
