# API de Recetas con Comentarios - RecetApp

## 📋 **Descripción General**

Esta documentación describe los nuevos endpoints de la API de RecetApp que permiten obtener recetas junto con sus comentarios asociados, proporcionando una respuesta completa que incluye toda la información de la receta, información nutricional, categorías, tipos de comida y comentarios.

## 🚀 **Endpoints Disponibles**

### **1. Obtener Recetas de un Usuario con Comentarios**

**Endpoint:** `GET /api/recipes/user/{userId}/with-comments`

**Descripción:** Obtiene todas las recetas de un usuario específico, incluyendo comentarios, información nutricional, categorías y tipos de comida.

**Parámetros:**
- `userId` (path): ID del usuario del cual obtener las recetas

**Respuesta de Ejemplo:**
```json
{
    "success": true,
    "message": "Recipes with comments retrieved successfully",
    "data": [
        {
            "recipeId": 7,
            "userId": 2,
            "title": "Pasta Carbonara",
            "description": "Traditional Italian pasta with eggs, cheese, pancetta, and black pepper",
            "createdAt": "2025-08-31T00:10:54.04",
            "preparationTime": "35 minutes",
            "servings": 3,
            "ingredientsCount": 7,
            "proced": "1. Cook pasta al dente. 2. Cook pancetta until crispy. 3. Mix eggs and cheese. 4. Combine all ingredients. 5. Add black pepper to taste.",
            "Information Nutritional": [
                {
                    "nutriId": 2,
                    "calories": "550 kcal",
                    "fat": "18 g",
                    "saturatedFat": "7 g",
                    "polyunsaturatedFat": "3 g",
                    "monounsaturatedFat": "5 g",
                    "carbohydrates": "58 g",
                    "sugar": "10 g",
                    "fiber": "5 g",
                    "protein": "22 g",
                    "sodium": "500 mg"
                }
            ],
            "Categories": [
                {
                    "categoryId": 12,
                    "name": "Cocina Italiana"
                }
            ],
            "Meals": [
                {
                    "mealId": 8,
                    "name": "Brunch"
                },
                {
                    "mealId": 11,
                    "name": "Vegan"
                }
            ],
            "Comments": [
                {
                    "commentId": 2,
                    "recipeId": 7,
                    "userId": 3,
                    "content": "¡Excelente receta!",
                    "createdAt": "2025-08-31T03:02:05.957",
                    "username": null,
                    "userEmail": null,
                    "recipeTitle": null
                },
                {
                    "commentId": 1,
                    "recipeId": 7,
                    "userId": 2,
                    "content": "¡Excelente receta! Me encantó cómo quedó. La recomiendo 100%.",
                    "createdAt": "2025-08-31T02:57:30.887",
                    "username": null,
                    "userEmail": null,
                    "recipeTitle": null
                }
            ]
        }
    ],
    "timestamp": "2025-08-31T03:02:31.01",
    "statusCode": 200
}
```

---

### **2. Obtener una Receta Específica con Comentarios**

**Endpoint:** `GET /api/recipes/{recipeId}/with-comments`

**Descripción:** Obtiene una receta específica por su ID, incluyendo todos sus comentarios, información nutricional, categorías y tipos de comida.

**Parámetros:**
- `recipeId` (path): ID de la receta a obtener

**Respuesta:** Similar a la anterior, pero con una sola receta en el array `data`.

---

## 🔧 **Implementación Técnica**

### **DTOs Utilizados**

#### **RecipeWithCommentsDTO**
```java
public class RecipeWithCommentsDTO {
    private Integer recipeId;
    private Integer userId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String preparationTime;
    private Integer servings;
    private Integer ingredientsCount;
    private String proced;
    
    @JsonProperty("Information Nutritional")
    private List<NutritionalInfoDTO> nutritionalInfo;
    
    @JsonProperty("Categories")
    private List<CategoryDTO> categories;
    
    @JsonProperty("Meals")
    private List<MealDTO> meals;
    
    @JsonProperty("Comments")
    private List<CommentDTO> comments;
    
    // Constructores, getters y setters
}
```

### **Servicios**

#### **RecipeService**
- `getRecipesWithCommentsByUserId(Integer userId)`: Obtiene todas las recetas de un usuario con comentarios
- `getRecipeWithCommentsById(Integer recipeId)`: Obtiene una receta específica con comentarios

#### **CommentService**
- `getRecipeComments(Integer recipeId)`: Obtiene todos los comentarios de una receta

### **Repositorios**

#### **RecipeWithNutritionRepository**
- Utiliza consultas SQL nativas para obtener recetas con información nutricional
- Los comentarios se obtienen a través del `CommentService`

---

## 📱 **Casos de Uso**

### **1. Dashboard de Usuario**
- Mostrar todas las recetas del usuario con sus comentarios
- Permitir ver la interacción social en cada receta

### **2. Vista Detallada de Receta**
- Mostrar una receta completa con todos sus comentarios
- Permitir a los usuarios ver opiniones y feedback

### **3. Análisis de Engagement**
- Contar comentarios por receta
- Identificar recetas más populares

---

## 🚨 **Consideraciones Importantes**

### **Performance**
- Los comentarios se obtienen de forma lazy (solo cuando se solicitan)
- Se recomienda usar paginación para recetas con muchos comentarios

### **Seguridad**
- Los endpoints no requieren autenticación específica
- Los comentarios se obtienen de forma pública

### **Manejo de Errores**
- Si una receta no existe, se retorna un error 400
- Si no hay comentarios, se retorna un array vacío

---

## 🔄 **Flujo de Datos**

1. **Cliente** solicita recetas con comentarios
2. **Controller** recibe la petición
3. **Service** coordina la obtención de datos
4. **Repository** obtiene datos básicos de la receta
5. **CommentService** obtiene comentarios asociados
6. **Service** combina toda la información
7. **Controller** retorna respuesta estructurada

---

## 📊 **Ejemplos de Uso con cURL**

### **Obtener Recetas de Usuario con Comentarios**
```bash
curl -X GET "http://localhost:8080/api/recipes/user/2/with-comments" \
  -H "Content-Type: application/json"
```

### **Obtener Receta Específica con Comentarios**
```bash
curl -X GET "http://localhost:8080/api/recipes/7/with-comments" \
  -H "Content-Type: application/json"
```

---

## 🎯 **Próximas Mejoras**

1. **Paginación de Comentarios**: Implementar paginación para recetas con muchos comentarios
2. **Filtros**: Agregar filtros por fecha, usuario, etc.
3. **Ordenamiento**: Permitir ordenar comentarios por diferentes criterios
4. **Cache**: Implementar cache para mejorar performance
5. **Real-time**: Agregar WebSockets para comentarios en tiempo real

---

## 📞 **Soporte**

Para cualquier pregunta o problema con estos endpoints, contactar al equipo de desarrollo de RecetApp.

**Autor:** Edgar Islas  
**Versión:** 1.0  
**Fecha:** 2025-08-31
