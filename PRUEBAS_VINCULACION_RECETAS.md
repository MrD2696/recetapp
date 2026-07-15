# Pruebas de Vinculación de Categorías y Meals a Recetas

## Endpoints Implementados

### 1. **Vincular Categorías a una Receta**
```bash
POST /api/recipes/{recipeId}/categories
Content-Type: application/json

[1, 4, 6]
```

**Ejemplo:**
```bash
curl -X POST http://localhost:8080/api/recipes/13/categories \
  -H "Content-Type: application/json" \
  -d "[1, 4, 6]"
```

**Respuesta esperada:**
```json
{
    "success": true,
    "message": "Categories linked successfully to recipe 13",
    "data": "Recipe 13 now has 3 categories linked",
    "timestamp": "2025-01-30T...",
    "statusCode": 200
}
```

### 2. **Desvincular Categorías de una Receta**
```bash
DELETE /api/recipes/{recipeId}/categories
```

**Ejemplo:**
```bash
curl -X DELETE http://localhost:8080/api/recipes/13/categories
```

**Respuesta esperada:**
```json
{
    "success": true,
    "message": "Categories unlinked successfully from recipe 13",
    "data": "All categories have been removed from recipe 13",
    "timestamp": "2025-01-30T...",
    "statusCode": 200
}
```

### 3. **Vincular Meals a una Receta**
```bash
POST /api/recipes/{recipeId}/meals
Content-Type: application/json

[3, 8, 5]
```

**Ejemplo:**
```bash
curl -X POST http://localhost:8080/api/recipes/13/meals \
  -H "Content-Type: application/json" \
  -d "[3, 8, 5]"
```

**Respuesta esperada:**
```json
{
    "success": true,
    "message": "Meals linked successfully to recipe 13",
    "data": "Recipe 13 now has 3 meals linked",
    "timestamp": "2025-01-30T...",
    "statusCode": 200
}
```

### 4. **Desvincular Meals de una Receta**
```bash
DELETE /api/recipes/{recipeId}/meals
```

**Ejemplo:**
```bash
curl -X DELETE http://localhost:8080/api/recipes/13/meals
```

**Respuesta esperada:**
```json
{
    "success": true,
    "message": "Meals unlinked successfully from recipe 13",
    "data": "All meals have been removed from recipe 13",
    "timestamp": "2025-01-30T...",
    "statusCode": 200
}
```

## 5. **Verificar que las Recetas Incluyan Categorías y Meals**

### Obtener Recetas por Usuario
```bash
GET /api/recipes/user/{userId}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/api/recipes/user/11
```

**Respuesta esperada (con categorías y meals):**
```json
{
    "success": true,
    "message": "Recipes retrieved successfully",
    "data": [
        {
            "recipeId": 13,
            "userId": 11,
            "title": "Pasta Carbonara Clásica",
            "description": "Traditional Italian pasta with eggs, cheese, and pancetta",
            "createdAt": "2025-08-29T23:38:50.273",
            "preparationTime": "30 minutes",
            "servings": 2,
            "ingredientsCount": 6,
            "proced": "1. Cook pasta al dente. 2. Cook pancetta until crispy. 3. Mix eggs and cheese. 4. Combine all ingredients.",
            "Information Nutritional": [
                {
                    "nutriId": 6,
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
                    "categoryId": 1,
                    "name": "Desayuno"
                },
                {
                    "categoryId": 4,
                    "name": "Almuerzo"
                },
                {
                    "categoryId": 6,
                    "name": "Postre"
                }
            ],
            "Meals": [
                {
                    "mealId": 3,
                    "name": "Desayuno"
                },
                {
                    "mealId": 8,
                    "name": "Brunch"
                },
                {
                    "mealId": 5,
                    "name": "Cena"
                }
            ]
        }
    ],
    "timestamp": "2025-01-30T...",
    "statusCode": 200
}
```

## Escenario Completo de Prueba

### Paso 1: Vincular Categorías
```bash
curl -X POST http://localhost:8080/api/recipes/13/categories \
  -H "Content-Type: application/json" \
  -d "[1, 4, 6]"
```

### Paso 2: Vincular Meals
```bash
curl -X POST http://localhost:8080/api/recipes/13/meals \
  -H "Content-Type: application/json" \
  -d "[3, 8, 5]"
```

### Paso 3: Verificar la Receta Completa
```bash
curl -X GET http://localhost:8080/api/recipes/13
```

### Paso 4: Verificar Todas las Recetas del Usuario
```bash
curl -X GET http://localhost:8080/api/recipes/user/11
```

### Paso 5: Limpiar (Opcional)
```bash
# Desvincular categorías
curl -X DELETE http://localhost:8080/api/recipes/13/categories

# Desvincular meals
curl -X DELETE http://localhost:8080/api/recipes/13/meals
```

## Notas Importantes

1. **Los IDs de categorías disponibles** en tu BD:
   - 1: Desayuno
   - 4: Almuerzo
   - 5: Cena
   - 6: Postre
   - 7: Aperitivo
   - 8: Bebida
   - 12: Bajo en Calorías
   - 13: PRUEBAS

2. **Los IDs de meals disponibles** en tu BD:
   - 1: Vegetariano
   - 3: Desayuno
   - 4: Almuerzo
   - 5: Cena
   - 6: Merienda
   - 7: Snack
   - 8: Brunch
   - 9: Tea Time
   - 10: Cóctel
   - 11: PRUEBAS

3. **Comportamiento**: Al vincular nuevas categorías/meals, se eliminan las vinculaciones anteriores y se crean las nuevas.

4. **Validación**: Solo se vinculan categorías y meals que existan en la base de datos.

## Verificación en Base de Datos

Después de ejecutar las pruebas, puedes verificar en la BD:

```sql
-- Ver categorías vinculadas a la receta 13
SELECT rc.*, c.name as category_name 
FROM RecipeCategories rc 
JOIN Categories c ON rc.categoryId = c.categoryId 
WHERE rc.recipeId = 13;

-- Ver meals vinculados a la receta 13
SELECT rm.*, m.name as meal_name 
FROM RecipeMeals rm 
JOIN Meals m ON rm.mealId = m.mealId 
WHERE rm.recipeId = 13;
```
