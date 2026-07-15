package com.recetapp.recetapp;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.recetapp.recetapp.dto.NutritionalInfoDTO;
import com.recetapp.recetapp.dto.RecipeWithNutritionDTO;
import com.recetapp.recetapp.model.NutriInformation;
import com.recetapp.recetapp.model.Recipe;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class EntityTests {

    @Test
    void testRecipeEntityCreation() {
        // Test que verifica que se puede crear una entidad Recipe
        Recipe recipe = new Recipe();
        recipe.setUserId(1);
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test Description");
        recipe.setPreparationTime("30 minutes");
        recipe.setServings(4);
        recipe.setIngredientsCount(5);
        recipe.setProced("Test procedure");
        
        assertNotNull(recipe);
        assertEquals("Test Recipe", recipe.getTitle());
        assertEquals(1, recipe.getUserId());
        assertEquals(4, recipe.getServings());
    }
    
    @Test
    void testNutriInformationEntityCreation() {
        // Test que verifica que se puede crear una entidad NutriInformation
        NutriInformation nutriInfo = new NutriInformation();
        nutriInfo.setRecipeId(1);
        nutriInfo.setCalories("300 kcal");
        nutriInfo.setFat("10 g");
        nutriInfo.setProtein("20 g");
        nutriInfo.setCarbohydrates("30 g");
        
        assertNotNull(nutriInfo);
        assertEquals(1, nutriInfo.getRecipeId());
        assertEquals("300 kcal", nutriInfo.getCalories());
        assertEquals("10 g", nutriInfo.getFat());
    }
    
    @Test
    void testRecipeWithNutritionDTOCreation() {
        // Test que verifica que se puede crear un DTO
        RecipeWithNutritionDTO dto = new RecipeWithNutritionDTO();
        dto.setRecipeId(1);
        dto.setUserId(1);
        dto.setTitle("Test Recipe");
        
        // Crear información nutricional
        NutritionalInfoDTO nutritionalInfo = new NutritionalInfoDTO();
        nutritionalInfo.setCalories("300 kcal");
        nutritionalInfo.setFat("10 g");
        nutritionalInfo.setProtein("20 g");
        
        List<NutritionalInfoDTO> nutritionalList = new ArrayList<>();
        nutritionalList.add(nutritionalInfo);
        dto.setNutritionalInfo(nutritionalList);
        
        assertNotNull(dto);
        assertEquals(1, dto.getRecipeId());
        assertEquals("Test Recipe", dto.getTitle());
        assertNotNull(dto.getNutritionalInfo());
        assertEquals(1, dto.getNutritionalInfo().size());
        assertEquals("300 kcal", dto.getNutritionalInfo().get(0).getCalories());
    }
}
