# Guía de Uso: Categorías, Tags y Tipos de Comida en RecetApp

## Descripción General

Se han agregado nuevas funcionalidades al sistema RecetApp para permitir la categorización y etiquetado de recetas. Esto incluye:

- **Categorías**: Clasificación principal de recetas (ej: Desayuno, Almuerzo, Postre)
- **Tags**: Etiquetas descriptivas (ej: Fácil, Rápido, Saludable)
- **Tipos de Comida**: Clasificación por momento del día (ej: Desayuno, Cena, Merienda)

## Estructura de Base de Datos

### Tablas Principales
- `Categories` - Almacena las categorías disponibles
- `Tags` - Almacena los tags disponibles  
- `Meals` - Almacena los tipos de comida disponibles

### Tablas de Relación
- `RecipeCategories` - Relaciona recetas con categorías
- `RecipeTags` - Relaciona recetas con tags
- `RecipeMeals` - Relaciona recetas con tipos de comida

### Secuencias
- `CategoryIdSequence` - Genera IDs únicos para categorías
- `TagIdSequence` - Genera IDs únicos para tags
- `MealIdSequence` - Genera IDs únicos para tipos de comida
- `RecipeCategoryIdSequence` - Genera IDs únicos para relaciones receta-categoría
- `RecipeTagIdSequence` - Genera IDs únicos para relaciones receta-tag
- `RecipeMealIdSequence` - Genera IDs únicos para relaciones receta-tipo de comida

## Entidades Java Creadas

### Entidades Principales
1. **Category.java** - Representa una categoría de receta
2. **Tag.java** - Representa un tag de receta
3. **Meal.java** - Representa un tipo de comida

### Entidades de Relación
1. **RecipeCategory.java** - Relación entre receta y categoría
2. **RecipeTag.java** - Relación entre receta y tag
3. **RecipeMeal.java** - Relación entre receta y tipo de comida

### Entidad Actualizada
- **Recipe.java** - Se agregaron las relaciones con categorías, tags y tipos de comida

## Repositorios Creados

### Repositorios Principales
1. **CategoryRepository.java** - Operaciones CRUD para categorías
2. **TagRepository.java** - Operaciones CRUD para tags
3. **MealRepository.java** - Operaciones CRUD para tipos de comida

### Repositorios de Relación
1. **RecipeCategoryRepository.java** - Gestión de relaciones receta-categoría
2. **RecipeTagRepository.java** - Gestión de relaciones receta-tag
3. **RecipeMealRepository.java** - Gestión de relaciones receta-tipo de comida

## Funcionalidades Disponibles

### Gestión de Categorías
- Crear nueva categoría
- Buscar categoría por nombre
- Listar todas las categorías
- Verificar existencia de categoría
- Contar recetas por categoría

### Gestión de Tags
- Crear nuevo tag
- Buscar tag por nombre
- Listar todos los tags
- Verificar existencia de tag
- Contar recetas por tag

### Gestión de Tipos de Comida
- Crear nuevo tipo de comida
- Buscar tipo de comida por nombre
- Listar todos los tipos de comida
- Verificar existencia de tipo de comida
- Contar recetas por tipo de comida

### Gestión de Relaciones
- Asignar categorías a recetas
- Asignar tags a recetas
- Asignar tipos de comida a recetas
- Remover relaciones
- Consultar relaciones existentes

## Ejemplos de Uso

### Crear una Nueva Categoría
```java
@Autowired
private CategoryRepository categoryRepository;

Category nuevaCategoria = new Category("Postres Caseros");
categoryRepository.save(nuevaCategoria);
```

### Asignar Categoría a una Receta
```java
Recipe receta = recipeRepository.findById(1).orElse(null);
Category categoria = categoryRepository.findByName("Postre").orElse(null);

if (receta != null && categoria != null) {
    receta.addCategory(categoria);
    recipeRepository.save(receta);
}
```

### Buscar Recetas por Categoría
```java
List<RecipeCategory> recetasEnCategoria = recipeCategoryRepository.findByCategoryId(categoriaId);
```

### Contar Recetas por Tag
```java
long cantidadRecetas = recipeTagRepository.countByTagId(tagId);
```

## Configuración de Base de Datos

### Ejecutar Scripts SQL
1. Ejecutar el archivo `src/main/resources/sql/create_categories_tags_meals_tables.sql`
2. Verificar que las tablas se crearon correctamente
3. Verificar que las secuencias están funcionando

### Verificación
```sql
-- Verificar tablas creadas
SELECT 'Categories' as TableName, COUNT(*) as RecordCount FROM Categories
UNION ALL
SELECT 'Tags', COUNT(*) FROM Tags
UNION ALL
SELECT 'Meals', COUNT(*) FROM Meals;

-- Verificar secuencias
SELECT 'CategoryIdSequence' as SequenceName, NEXT VALUE FOR CategoryIdSequence as NextValue;
```

## Ventajas del Sistema

1. **Flexibilidad**: Las recetas pueden tener múltiples categorías, tags y tipos de comida
2. **Búsqueda Avanzada**: Permite filtrar recetas por múltiples criterios
3. **Organización**: Mejora la organización y navegación del sistema
4. **Escalabilidad**: Fácil agregar nuevas categorías, tags o tipos de comida
5. **Integridad**: Relaciones con claves foráneas y restricciones de unicidad

## Consideraciones Técnicas

### Rendimiento
- Se han creado índices en las columnas más consultadas
- Las consultas utilizan JOINs optimizados
- Se implementó paginación en los repositorios

### Seguridad
- Validación de datos en las entidades
- Manejo de excepciones en los repositorios
- Transacciones para operaciones complejas

### Mantenimiento
- Código documentado con JavaDoc
- Estructura modular y reutilizable
- Fácil extensión para nuevas funcionalidades

## Próximos Pasos

1. **Implementar Servicios**: Crear servicios de negocio para las nuevas entidades
2. **Crear Controladores**: Implementar endpoints REST para la gestión
3. **Agregar Validaciones**: Implementar validaciones de negocio
4. **Crear DTOs**: Definir objetos de transferencia de datos
5. **Implementar Tests**: Crear pruebas unitarias y de integración
6. **Documentar API**: Generar documentación de la API REST

## Soporte

Para cualquier duda o problema con la implementación, revisar:
- Logs de la aplicación
- Documentación de Spring Data JPA
- Scripts SQL de creación de tablas
- Entidades y repositorios creados
