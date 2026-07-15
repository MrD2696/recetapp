# Pruebas de la API RecetApp - Categorías, Meals e Información Nutricional

## Configuración Inicial

### 1. Variables de Entorno

Configura estas variables en tu cliente HTTP (Postman, Insomnia, etc.):

```
BASE_URL: http://localhost:8080/api
TOKEN: <tu_token_jwt_aqui>
```

### 2. Headers Comunes

```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

---

## Pruebas de la API de Categorías

### 1. Crear Categorías de Ejemplo

#### 1.1 Crear Categoría "Desayuno"
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Desayuno"
  }'
```

#### 1.2 Crear Categoría "Almuerzo"
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Almuerzo"
  }'
```

#### 1.3 Crear Categoría "Cena"
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Cena"
  }'
```

#### 1.4 Crear Categoría "Vegetariano"
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Vegetariano"
  }'
```

#### 1.5 Crear Categoría "Bajo en Calorías"
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Bajo en Calorías"
  }'
```

### 2. Obtener Categorías

#### 2.1 Listar Todas las Categorías
```bash
curl -X GET "{{BASE_URL}}/categories" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.2 Obtener Categoría por ID
```bash
curl -X GET "{{BASE_URL}}/categories/1" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.3 Buscar Categorías por Nombre
```bash
curl -X GET "{{BASE_URL}}/categories/search?name=Desayuno" \
  -H "Authorization: Bearer {{TOKEN}}"
```

### 3. Actualizar Categorías

#### 3.1 Actualizar Nombre de Categoría
```bash
curl -X PUT "{{BASE_URL}}/categories/1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Desayuno Saludable"
  }'
```

---

## Pruebas de la API de Meals (Tipos de Comida)

### 1. Crear Tipos de Comida de Ejemplo

#### 1.1 Crear Tipo "Desayuno"
```bash
curl -X POST "{{BASE_URL}}/meals" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Desayuno"
  }'
```

#### 1.2 Crear Tipo "Almuerzo"
```bash
curl -X POST "{{BASE_URL}}/meals" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Almuerzo"
  }'
```

#### 1.3 Crear Tipo "Cena"
```bash
curl -X POST "{{BASE_URL}}/meals" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Cena"
  }'
```

#### 1.4 Crear Tipo "Merienda"
```bash
curl -X POST "{{BASE_URL}}/meals" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Merienda"
  }'
```

#### 1.5 Crear Tipo "Snack"
```bash
curl -X POST "{{BASE_URL}}/meals" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Snack"
  }'
```

### 2. Obtener Tipos de Comida

#### 2.1 Listar Todos los Tipos
```bash
curl -X GET "{{BASE_URL}}/meals" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.2 Obtener Tipo por ID
```bash
curl -X GET "{{BASE_URL}}/meals/1" \
  -H "Authorization: Bearer {{TOKEN}}"
```

---

## Pruebas de Vinculación de Recetas

### 1. Vincular Categorías a una Receta

#### 1.1 Vincular Múltiples Categorías
```bash
curl -X POST "{{BASE_URL}}/recipe-links/1/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '[1, 4, 5]'
```

**Nota:** Esto vinculará las categorías "Desayuno", "Vegetariano" y "Bajo en Calorías" a la receta con ID 1.

### 2. Vincular Tipos de Comida a una Receta

#### 2.1 Vincular Tipos de Comida
```bash
curl -X POST "{{BASE_URL}}/recipe-links/1/meals" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '[1, 4]'
```

**Nota:** Esto vinculará los tipos "Desayuno" y "Merienda" a la receta con ID 1.

### 3. Vincular Todo de una Vez

#### 3.1 Vincular Categorías y Tipos Simultáneamente
```bash
curl -X POST "{{BASE_URL}}/recipe-links/1/all" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "categoryIds": [1, 4, 5],
    "mealIds": [1, 4]
  }'
```

### 4. Consultar Vínculos

#### 4.1 Obtener Categorías de una Receta
```bash
curl -X GET "{{BASE_URL}}/recipe-links/1/categories" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 4.2 Obtener Tipos de Comida de una Receta
```bash
curl -X GET "{{BASE_URL}}/recipe-links/1/meals" \
  -H "Authorization: Bearer {{TOKEN}}"
```

---

## Pruebas de Información Nutricional

### 1. Crear Información Nutricional

#### 1.1 Crear Información Nutricional Básica
```bash
curl -X POST "{{BASE_URL}}/nutritional-info" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "nutriId": 1,
    "calories": "180",
    "fat": "8g",
    "saturatedFat": "2g",
    "polyunsaturatedFat": "1g",
    "monounsaturatedFat": "4g",
    "carbohydrates": "15g",
    "sugar": "8g",
    "fiber": "6g",
    "protein": "12g",
    "sodium": "300mg"
  }'
```

#### 1.2 Crear Información Nutricional para Otra Receta
```bash
curl -X POST "{{BASE_URL}}/nutritional-info" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "nutriId": 2,
    "calories": "320",
    "fat": "15g",
    "saturatedFat": "5g",
    "polyunsaturatedFat": "3g",
    "monounsaturatedFat": "6g",
    "carbohydrates": "25g",
    "sugar": "12g",
    "fiber": "8g",
    "protein": "18g",
    "sodium": "450mg"
  }'
```

### 2. Consultar Información Nutricional

#### 2.1 Obtener Toda la Información Nutricional
```bash
curl -X GET "{{BASE_URL}}/nutritional-info" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.2 Obtener Información por ID
```bash
curl -X GET "{{BASE_URL}}/nutritional-info/1" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.3 Obtener Información por Receta
```bash
curl -X GET "{{BASE_URL}}/nutritional-info/recipe/1" \
  -H "Authorization: Bearer {{TOKEN}}"
```

### 3. Actualizar Información Nutricional

#### 3.1 Actualizar Calorías
```bash
curl -X PUT "{{BASE_URL}}/nutritional-info/1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "nutriId": 1,
    "calories": "200",
    "fat": "8g",
    "saturatedFat": "2g",
    "polyunsaturatedFat": "1g",
    "monounsaturatedFat": "4g",
    "carbohydrates": "15g",
    "sugar": "8g",
    "fiber": "6g",
    "protein": "12g",
    "sodium": "300mg"
  }'
```

---

## Pruebas de Flujo Completo

### Escenario: Crear Receta Completa con Categorías, Meals e Información Nutricional

#### Paso 1: Verificar que Existen Categorías y Meals
```bash
# Verificar categorías
curl -X GET "{{BASE_URL}}/categories" \
  -H "Authorization: Bearer {{TOKEN}}"

# Verificar meals
curl -X GET "{{BASE_URL}}/meals" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### Paso 2: Crear Receta (usando el endpoint existente)
```bash
curl -X POST "{{BASE_URL}}/recipes" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "userId": 1,
    "title": "Ensalada César Vegetariana",
    "description": "Ensalada fresca y saludable perfecta para el almuerzo",
    "preparationTime": "15 minutos",
    "servings": 2,
    "ingredientsCount": 8,
    "proced": "1. Lavar la lechuga romana...",
    "categoryIds": [2, 4],
    "mealIds": [2]
  }'
```

#### Paso 3: Agregar Información Nutricional
```bash
curl -X POST "{{BASE_URL}}/nutritional-info" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "nutriId": 3,
    "calories": "150",
    "fat": "6g",
    "saturatedFat": "1g",
    "polyunsaturatedFat": "2g",
    "monounsaturatedFat": "2g",
    "carbohydrates": "12g",
    "sugar": "6g",
    "fiber": "8g",
    "protein": "10g",
    "sodium": "350mg"
  }'
```

#### Paso 4: Verificar Receta Completa
```bash
# Obtener receta con detalles
curl -X GET "{{BASE_URL}}/recipes/3" \
  -H "Authorization: Bearer {{TOKEN}}"

# Obtener categorías de la receta
curl -X GET "{{BASE_URL}}/recipe-links/3/categories" \
  -H "Authorization: Bearer {{TOKEN}}"

# Obtener tipos de comida de la receta
curl -X GET "{{BASE_URL}}/recipe-links/3/meals" \
  -H "Authorization: Bearer {{TOKEN}}"

# Obtener información nutricional
curl -X GET "{{BASE_URL}}/nutritional-info/recipe/3" \
  -H "Authorization: Bearer {{TOKEN}}"
```

---

## Pruebas de Validación y Errores

### 1. Probar Validaciones de Categorías

#### 1.1 Crear Categoría sin Nombre
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{}'
```

**Respuesta Esperada:** 400 Bad Request

#### 1.2 Crear Categoría Duplicada
```bash
curl -X POST "{{BASE_URL}}/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{
    "name": "Desayuno"
  }'
```

**Respuesta Esperada:** 400 Bad Request

### 2. Probar Validaciones de Vinculación

#### 2.1 Vincular Categoría Inexistente
```bash
curl -X POST "{{BASE_URL}}/recipe-links/1/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '[999]'
```

**Respuesta Esperada:** 400 Bad Request

#### 2.2 Vincular a Receta Inexistente
```bash
curl -X POST "{{BASE_URL}}/recipe-links/999/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '[1]'
```

**Respuesta Esperada:** 404 Not Found

---

## Pruebas de Eliminación

### 1. Eliminar Vínculos

#### 1.1 Eliminar Todas las Categorías de una Receta
```bash
curl -X DELETE "{{BASE_URL}}/recipe-links/1/categories" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 1.2 Eliminar Todos los Tipos de Comida de una Receta
```bash
curl -X DELETE "{{BASE_URL}}/recipe-links/1/meals" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 1.3 Eliminar Todos los Vínculos
```bash
curl -X DELETE "{{BASE_URL}}/recipe-links/1/all" \
  -H "Authorization: Bearer {{TOKEN}}"
```

### 2. Eliminar Información Nutricional

#### 2.1 Eliminar por ID
```bash
curl -X DELETE "{{BASE_URL}}/nutritional-info/1" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.2 Eliminar por Receta
```bash
curl -X DELETE "{{BASE_URL}}/nutritional-info/recipe/1" \
  -H "Authorization: Bearer {{TOKEN}}"
```

---

## Verificación de Resultados

### 1. Verificar Estado Final

Después de ejecutar todas las pruebas, verifica que:

1. **Las categorías se crearon correctamente:**
   ```bash
   curl -X GET "{{BASE_URL}}/categories" \
     -H "Authorization: Bearer {{TOKEN}}"
   ```

2. **Los tipos de comida se crearon correctamente:**
   ```bash
   curl -X GET "{{BASE_URL}}/meals" \
     -H "Authorization: Bearer {{TOKEN}}"
   ```

3. **Las recetas tienen sus vínculos:**
   ```bash
   curl -X GET "{{BASE_URL}}/recipe-links/1/categories" \
     -H "Authorization: Bearer {{TOKEN}}"
   
   curl -X GET "{{BASE_URL}}/recipe-links/1/meals" \
     -H "Authorization: Bearer {{TOKEN}}"
   ```

4. **La información nutricional está asociada:**
   ```bash
   curl -X GET "{{BASE_URL}}/nutritional-info/recipe/1" \
     -H "Authorization: Bearer {{TOKEN}}"
   ```

### 2. Estadísticas

#### 2.1 Estadísticas de Categorías
```bash
curl -X GET "{{BASE_URL}}/categories/stats" \
  -H "Authorization: Bearer {{TOKEN}}"
```

#### 2.2 Estadísticas de Meals
```bash
curl -X GET "{{BASE_URL}}/meals/stats" \
  -H "Authorization: Bearer {{TOKEN}}"
```

---

## Notas Importantes

1. **Orden de Ejecución:** Ejecuta las pruebas en el orden indicado para evitar errores de dependencias.

2. **IDs Dinámicos:** Los IDs en los ejemplos son referenciales. Ajusta según los IDs reales que devuelva tu sistema.

3. **Autenticación:** Asegúrate de tener un token JWT válido antes de ejecutar las pruebas.

4. **Base de Datos:** Verifica que las tablas necesarias estén creadas y que el script SQL se haya ejecutado correctamente.

5. **Logs:** Revisa los logs del servidor para identificar cualquier error o problema.

---

## Solución de Problemas Comunes

### Error 404 - Not Found
- Verifica que el endpoint esté correctamente mapeado
- Confirma que el controlador esté registrado en Spring

### Error 500 - Internal Server Error
- Revisa los logs del servidor
- Verifica que las entidades y repositorios estén correctamente configurados

### Error de Validación
- Confirma que los datos de entrada cumplan con las validaciones
- Verifica que los campos obligatorios estén presentes

### Error de Base de Datos
- Confirma que las tablas existan
- Verifica la conectividad a la base de datos
- Revisa que las secuencias estén creadas

---

**Autor:** Edgar Islas  
**Versión:** 1.0  
**Fecha:** Enero 2024
