package com.recetapp.recetapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.NutritionalInfoDTO;
import com.recetapp.recetapp.model.NutriInformation;
import com.recetapp.recetapp.repository.NutriInformationRepository;
import com.recetapp.recetapp.repository.RecipeRepository;

/**
 * Controlador para manejar la información nutricional de las recetas.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/nutritional-info")
@CrossOrigin(origins = "*")
public class NutritionalInfoController {
    
    @Autowired
    private NutriInformationRepository nutriInformationRepository;
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    /**
     * Obtiene toda la información nutricional.
     * 
     * @return Lista de toda la información nutricional
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<NutritionalInfoDTO>>> getAllNutritionalInfo() {
        try {
            List<NutriInformation> nutritionalInfo = nutriInformationRepository.findAll();
            List<NutritionalInfoDTO> nutritionalInfoDTOs = nutritionalInfo.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Información nutricional obtenida exitosamente", nutritionalInfoDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener información nutricional: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene información nutricional por ID.
     * 
     * @param id ID de la información nutricional
     * @return Información nutricional encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NutritionalInfoDTO>> getNutritionalInfoById(@PathVariable Integer id) {
        try {
            Optional<NutriInformation> nutritionalInfo = nutriInformationRepository.findById(id);
            
            if (nutritionalInfo.isPresent()) {
                NutritionalInfoDTO nutritionalInfoDTO = convertToDTO(nutritionalInfo.get());
                return ResponseEntity.ok(ApiResponse.success("Información nutricional encontrada", nutritionalInfoDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Información nutricional no encontrada", 404));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener información nutricional: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Obtiene información nutricional por ID de receta.
     * 
     * @param recipeId ID de la receta
     * @return Información nutricional de la receta
     */
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse<NutritionalInfoDTO>> getNutritionalInfoByRecipeId(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            Optional<NutriInformation> nutritionalInfo = nutriInformationRepository.findByRecipeId(recipeId);
            if (nutritionalInfo.isPresent()) {
                NutritionalInfoDTO nutritionalInfoDTO = convertToDTO(nutritionalInfo.get());
                return ResponseEntity.ok(ApiResponse.success("Información nutricional de la receta obtenida exitosamente", nutritionalInfoDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("La receta no tiene información nutricional", 404));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener información nutricional de la receta: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Crea nueva información nutricional.
     * 
     * @param nutritionalInfoDTO DTO con los datos de la información nutricional
     * @return Información nutricional creada
     */
    @PostMapping
    public ResponseEntity<ApiResponse<NutritionalInfoDTO>> createNutritionalInfo(@RequestBody NutritionalInfoDTO nutritionalInfoDTO) {
        try {
            // Validar que la receta existe
            if (nutritionalInfoDTO.getNutriId() == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El ID de la receta es obligatorio", 400));
            }
            
            if (!recipeRepository.existsById(nutritionalInfoDTO.getNutriId())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("La receta especificada no existe", 400));
            }
            
            // Crear y guardar la información nutricional
            NutriInformation newNutritionalInfo = convertToEntity(nutritionalInfoDTO);
            newNutritionalInfo.setRecipeId(nutritionalInfoDTO.getNutriId()); // Usar nutriId como recipeId
            NutriInformation savedNutritionalInfo = nutriInformationRepository.save(newNutritionalInfo);
            
            NutritionalInfoDTO savedNutritionalInfoDTO = convertToDTO(savedNutritionalInfo);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Información nutricional creada exitosamente", savedNutritionalInfoDTO, 201));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear información nutricional: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Actualiza información nutricional existente.
     * 
     * @param id ID de la información nutricional a actualizar
     * @param nutritionalInfoDTO DTO con los nuevos datos
     * @return Información nutricional actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NutritionalInfoDTO>> updateNutritionalInfo(
            @PathVariable Integer id, 
            @RequestBody NutritionalInfoDTO nutritionalInfoDTO) {
        try {
            Optional<NutriInformation> existingNutritionalInfo = nutriInformationRepository.findById(id);
            if (!existingNutritionalInfo.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Información nutricional no encontrada", 404));
            }
            
            // Actualizar la información nutricional
            NutriInformation nutritionalInfoToUpdate = existingNutritionalInfo.get();
            nutritionalInfoToUpdate.setCalories(nutritionalInfoDTO.getCalories());
            nutritionalInfoToUpdate.setFat(nutritionalInfoDTO.getFat());
            nutritionalInfoToUpdate.setSaturatedFat(nutritionalInfoDTO.getSaturatedFat());
            nutritionalInfoToUpdate.setPolyunsaturatedFat(nutritionalInfoDTO.getPolyunsaturatedFat());
            nutritionalInfoToUpdate.setMonounsaturatedFat(nutritionalInfoDTO.getMonounsaturatedFat());
            nutritionalInfoToUpdate.setCarbohydrates(nutritionalInfoDTO.getCarbohydrates());
            nutritionalInfoToUpdate.setSugar(nutritionalInfoDTO.getSugar());
            nutritionalInfoToUpdate.setFiber(nutritionalInfoDTO.getFiber());
            nutritionalInfoToUpdate.setProtein(nutritionalInfoDTO.getProtein());
            nutritionalInfoToUpdate.setSodium(nutritionalInfoDTO.getSodium());
            
            NutriInformation updatedNutritionalInfo = nutriInformationRepository.save(nutritionalInfoToUpdate);
            
            NutritionalInfoDTO updatedNutritionalInfoDTO = convertToDTO(updatedNutritionalInfo);
            
            return ResponseEntity.ok(ApiResponse.success("Información nutricional actualizada exitosamente", updatedNutritionalInfoDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al actualizar información nutricional: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina información nutricional.
     * 
     * @param id ID de la información nutricional a eliminar
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNutritionalInfo(@PathVariable Integer id) {
        try {
            Optional<NutriInformation> nutritionalInfo = nutriInformationRepository.findById(id);
            if (!nutritionalInfo.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Información nutricional no encontrada", 404));
            }
            
            nutriInformationRepository.deleteById(id);
            
            return ResponseEntity.ok(ApiResponse.success("Información nutricional eliminada exitosamente", 
                "Información nutricional con ID " + id + " ha sido eliminada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar información nutricional: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Elimina toda la información nutricional de una receta.
     * 
     * @param recipeId ID de la receta
     * @return Respuesta de confirmación
     */
    @DeleteMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse<String>> deleteNutritionalInfoByRecipeId(@PathVariable Integer recipeId) {
        try {
            // Verificar que la receta existe
            if (!recipeRepository.existsById(recipeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Receta no encontrada", 404));
            }
            
            Optional<NutriInformation> nutritionalInfo = nutriInformationRepository.findByRecipeId(recipeId);
            if (!nutritionalInfo.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("No hay información nutricional para eliminar", 
                    "La receta no tiene información nutricional asociada"));
            }
            
            nutriInformationRepository.deleteByRecipeId(recipeId);
            
            return ResponseEntity.ok(ApiResponse.success("Información nutricional eliminada exitosamente", 
                "Se eliminó la información nutricional de la receta"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar información nutricional de la receta: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Busca información nutricional por nombre de nutriente.
     * 
     * @param nutrientName Nombre del nutriente a buscar
     * @return Lista de información nutricional que coincide
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NutritionalInfoDTO>>> searchNutritionalInfoByNutrientName(
            @RequestParam String nutrientName) {
        try {
            if (nutrientName == null || nutrientName.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El nombre del nutriente es obligatorio", 400));
            }
            
            // Buscar en todos los campos de nutrientes
            List<NutriInformation> allNutritionalInfo = nutriInformationRepository.findAll();
            List<NutritionalInfoDTO> matchingNutritionalInfo = allNutritionalInfo.stream()
                    .filter(nutri -> {
                        String searchTerm = nutrientName.trim().toLowerCase();
                        return (nutri.getCalories() != null && nutri.getCalories().toLowerCase().contains(searchTerm)) ||
                               (nutri.getFat() != null && nutri.getFat().toLowerCase().contains(searchTerm)) ||
                               (nutri.getProtein() != null && nutri.getProtein().toLowerCase().contains(searchTerm)) ||
                               (nutri.getCarbohydrates() != null && nutri.getCarbohydrates().toLowerCase().contains(searchTerm)) ||
                               (nutri.getSugar() != null && nutri.getSugar().toLowerCase().contains(searchTerm)) ||
                               (nutri.getFiber() != null && nutri.getFiber().toLowerCase().contains(searchTerm));
                    })
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success("Búsqueda completada", matchingNutritionalInfo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error en la búsqueda: " + e.getMessage(), 500));
        }
    }
    
    /**
     * Convierte una entidad NutriInformation a DTO.
     */
    private NutritionalInfoDTO convertToDTO(NutriInformation nutriInformation) {
        NutritionalInfoDTO dto = new NutritionalInfoDTO();
        dto.setNutriId(nutriInformation.getNutriId());
        dto.setCalories(nutriInformation.getCalories());
        dto.setFat(nutriInformation.getFat());
        dto.setSaturatedFat(nutriInformation.getSaturatedFat());
        dto.setPolyunsaturatedFat(nutriInformation.getPolyunsaturatedFat());
        dto.setMonounsaturatedFat(nutriInformation.getMonounsaturatedFat());
        dto.setCarbohydrates(nutriInformation.getCarbohydrates());
        dto.setSugar(nutriInformation.getSugar());
        dto.setFiber(nutriInformation.getFiber());
        dto.setProtein(nutriInformation.getProtein());
        dto.setSodium(nutriInformation.getSodium());
        return dto;
    }
    
    /**
     * Convierte un DTO a entidad NutriInformation.
     */
    private NutriInformation convertToEntity(NutritionalInfoDTO dto) {
        NutriInformation entity = new NutriInformation();
        entity.setNutriId(dto.getNutriId());
        entity.setCalories(dto.getCalories());
        entity.setFat(dto.getFat());
        entity.setSaturatedFat(dto.getSaturatedFat());
        entity.setPolyunsaturatedFat(dto.getPolyunsaturatedFat());
        entity.setMonounsaturatedFat(dto.getMonounsaturatedFat());
        entity.setCarbohydrates(dto.getCarbohydrates());
        entity.setSugar(dto.getSugar());
        entity.setFiber(dto.getFiber());
        entity.setProtein(dto.getProtein());
        entity.setSodium(dto.getSodium());
        return entity;
    }
}
