# API de Categorías, Meals e Información Nutricional - RecetApp

## Descripción General

Esta documentación describe los endpoints de la API para manejar categorías de recetas, tipos de comida (meals) e información nutricional en el sistema RecetApp.

## Base URL

```
http://localhost:8080/api
```

## Autenticación

Todos los endpoints requieren autenticación. Incluye el token JWT en el header:

```
Authorization: Bearer <token>
```

---

## 1. API de Categorías

### Base Path: `/categories`

#### 1.1 Obtener Todas las Categorías

**GET** `/categories`

**Descripción:** Obtiene la lista completa de categorías disponibles.

**Respuesta Exitosa (200):**
```json
{
  "success": true,
  "message": "Categorías obtenidas exitosamente",
  "data": [
    {
      "categoryId": 1,
      "name": "Desayuno"
    },
    {
      "categoryId": 2,
      "name": "Almuerzo"
    },
    {
      "categoryId": 3,
      "name": "Cena"
    }
  ],
  "timestamp": "2024-01-15T10:30:00",
  "statusCode": 200
}
```

#### 1.2 Obtener Categoría por ID

**GET** `/categories/{id}`

**Parámetros:**
- `id` (path): ID de la categoría

**Respuesta Exitosa (200):**
```json
{
  "success": true,
  "message": "Categoría encontrada",
  "data": {
    "categoryId": 1,
    "name": "Desayuno"
  }
}
```

**Respuesta Error (404):**
```json
{
  "success": false,
  "message": "Categoría no encontrada",
  "statusCode": 404
}
```

#### 1.3 Crear Nueva Categoría

**POST** `/categories`

**Body:**
```json
{
  "name": "Nueva Categoría"
}
```

**Respuesta Exitosa (201):**
```json
{
  "success": true,
  "message": "Categoría creada exitosamente",
  "data": {
    "categoryId": 4,
    "name": "Nueva Categoría"
  },
  "statusCode": 201
}
```

#### 1.4 Actualizar Categoría

**PUT** `/categories/{id}`

**Parámetros:**
- `id` (path): ID de la categoría a actualizar

**Body:**
```json
{
  "name": "Categoría Actualizada"
}
```

#### 1.5 Eliminar Categoría

**DELETE** `/categories/{id}`

**Parámetros:**
- `id` (path): ID de la categoría a eliminar

#### 1.6 Buscar Categorías por Nombre

**GET** `/categories/search?name={nombre}`

**Parámetros:**
- `name` (query): Nombre parcial a buscar

#### 1.7 Estadísticas de Categorías

**GET** `/categories/stats`

**Descripción:** Obtiene estadísticas de categorías con conteo de recetas.

---

## 2. API de Tipos de Comida (Meals)

### Base Path: `/meals`

#### 2.1 Obtener Todos los Tipos de Comida

**GET** `/meals`

**Respuesta Exitosa (200):**
```json
{
  "success": true,
  "message": "Tipos de comida obtenidos exitosamente",
  "data": [
    {
      "mealId": 1,
      "name": "Desayuno"
    },
    {
      "mealId": 2,
      "name": "Almuerzo"
    },
    {
      "mealId": 3,
      "name": "Cena"
    }
  ]
}
```

#### 2.2 Obtener Tipo de Comida por ID

**GET** `/meals/{id}`

#### 2.3 Crear Nuevo Tipo de Comida

**POST** `/meals`

**Body:**
```json
{
  "name": "Nuevo Tipo de Comida"
}
```

#### 2.4 Actualizar Tipo de Comida

**PUT** `/meals/{id}`

#### 2.5 Eliminar Tipo de Comida

**DELETE** `/meals/{id}`

#### 2.6 Buscar Tipos de Comida por Nombre

**GET** `/meals/search?name={nombre}`

#### 2.7 Estadísticas de Tipos de Comida

**GET** `/meals/stats`

---

## 3. API de Información Nutricional

### Base Path: `/nutritional-info`

#### 3.1 Obtener Toda la Información Nutricional

**GET** `/nutritional-info`

**Respuesta Exitosa (200):**
```json
{
  "success": true,
  "message": "Información nutricional obtenida exitosamente",
  "data": [
    {
      "nutriId": 1,
      "calories": "250",
      "fat": "12g",
      "saturatedFat": "3g",
      "polyunsaturatedFat": "2g",
      "monounsaturatedFat": "6g",
      "carbohydrates": "30g",
      "sugar": "15g",
      "fiber": "5g",
      "protein": "8g",
      "sodium": "400mg"
    }
  ]
}
```

#### 3.2 Obtener Información Nutricional por ID

**GET** `/nutritional-info/{id}`

#### 3.3 Obtener Información Nutricional por Receta

**GET** `/nutritional-info/recipe/{recipeId}`

**Parámetros:**
- `recipeId` (path): ID de la receta

#### 3.4 Crear Información Nutricional

**POST** `/nutritional-info`

**Body:**
```json
{
  "nutriId": 1,
  "calories": "250",
  "fat": "12g",
  "saturatedFat": "3g",
  "polyunsaturatedFat": "2g",
  "monounsaturatedFat": "6g",
  "carbohydrates": "30g",
  "sugar": "15g",
  "fiber": "5g",
  "protein": "8g",
  "sodium": "400mg"
}
```

#### 3.5 Actualizar Información Nutricional

**PUT** `/nutritional-info/{id}`

#### 3.6 Eliminar Información Nutricional

**DELETE** `/nutritional-info/{id}`

#### 3.7 Eliminar Información Nutricional por Receta

**DELETE** `/nutritional-info/recipe/{recipeId}`

#### 3.8 Buscar Información Nutricional

**GET** `/nutritional-info/search?nutrientName={nombre}`

---

## 4. API de Vinculación de Recetas

### Base Path: `/recipe-links`

#### 4.1 Vincular Categorías a una Receta

**POST** `/recipe-links/{recipeId}/categories`

**Parámetros:**
- `recipeId` (path): ID de la receta

**Body:**
```json
[1, 2, 3]
```

**Descripción:** Lista de IDs de categorías a vincular.

#### 4.2 Vincular Tipos de Comida a una Receta

**POST** `/recipe-links/{recipeId}/meals`

**Parámetros:**
- `recipeId` (path): ID de la receta

**Body:**
```json
[1, 2]
```

**Descripción:** Lista de IDs de tipos de comida a vincular.

#### 4.3 Vincular Todo a una Receta

**POST** `/recipe-links/{recipeId}/all`

**Parámetros:**
- `recipeId` (path): ID de la receta

**Body:**
```json
{
  "categoryIds": [1, 2, 3],
  "mealIds": [1, 2]
}
```

#### 4.4 Obtener Categorías de una Receta

**GET** `/recipe-links/{recipeId}/categories`

#### 4.5 Obtener Tipos de Comida de una Receta

**GET** `/recipe-links/{recipeId}/meals`

#### 4.6 Desvincular Todas las Categorías

**DELETE** `/recipe-links/{recipeId}/categories`

#### 4.7 Desvincular Todos los Tipos de Comida

**DELETE** `/recipe-links/{recipeId}/meals`

#### 4.8 Desvincular Todo

**DELETE** `/recipe-links/{recipeId}/all`

---

## 5. Ejemplos de Uso

### 5.1 Crear una Receta con Categorías y Tipos de Comida

```bash
# 1. Crear la receta básica
POST /api/recipes
{
  "userId": 1,
  "title": "Ensalada César",
  "description": "Ensalada fresca y saludable",
  "preparationTime": "15 minutos",
  "servings": 2,
  "ingredientsCount": 8,
  "proced": "1. Lavar la lechuga...",
  "categoryIds": [2, 7],  // Almuerzo + Vegetariano
  "mealIds": [2]          // Solo Almuerzo
}

# 2. O alternativamente, vincular después de crear
POST /api/recipe-links/123/all
{
  "categoryIds": [2, 7],
  "mealIds": [2]
}
```

### 5.2 Agregar Información Nutricional

```bash
# Agregar información nutricional a la receta
POST /api/nutritional-info
{
  "nutriId": 123,
  "calories": "180",
  "fat": "8g",
  "saturatedFat": "2g",
  "carbohydrates": "15g",
  "protein": "12g",
  "fiber": "6g"
}
```

### 5.3 Consultar Receta Completa

```bash
# Obtener receta con toda su información
GET /api/recipes/123

# Obtener solo las categorías
GET /api/recipe-links/123/categories

# Obtener solo los tipos de comida
GET /api/recipe-links/123/meals

# Obtener información nutricional
GET /api/nutritional-info/recipe/123
```

---

## 6. Códigos de Estado HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **400 Bad Request**: Datos de entrada inválidos
- **401 Unauthorized**: No autenticado
- **403 Forbidden**: No autorizado
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error interno del servidor

---

## 7. Validaciones

### 7.1 Categorías
- El nombre es obligatorio
- No se permiten nombres duplicados
- No se puede eliminar si está siendo usada por recetas

### 7.2 Tipos de Comida
- El nombre es obligatorio
- No se permiten nombres duplicados
- No se puede eliminar si está siendo usado por recetas

### 7.3 Información Nutricional
- Los valores deben ser válidos
- Se valida que la receta exista

### 7.4 Vinculaciones
- Se valida que la receta exista
- Se valida que las categorías y tipos de comida existan
- Se previenen duplicados automáticamente

---

## 8. Consideraciones de Rendimiento

- Las consultas incluyen índices para búsquedas eficientes
- Se implementa paginación para listas grandes
- Las operaciones de vinculación son transaccionales
- Se cachean las consultas frecuentes

---

## 9. Próximos Pasos

1. **Implementar paginación** en endpoints de listado
2. **Agregar filtros avanzados** para búsquedas complejas
3. **Implementar cache** para mejorar rendimiento
4. **Agregar validaciones** más robustas
5. **Crear tests unitarios** y de integración
6. **Documentar con Swagger/OpenAPI**

---

## 10. Soporte

Para soporte técnico o preguntas sobre la API, contacta al equipo de desarrollo de RecetApp.

**Autor:** Edgar Islas  
**Versión:** 1.0  
**Fecha:** Enero 2024
