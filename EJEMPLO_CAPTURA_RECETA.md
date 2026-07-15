# Ejemplo de Captura de Recetas con Categorías y Tipos de Comida

## Formulario de Creación/Actualización de Receta

### Campos Básicos (Existentes)
- **Título**: Texto libre
- **Descripción**: Área de texto
- **Tiempo de Preparación**: Texto libre (ej: "30 minutos")
- **Porciones**: Número entero
- **Cantidad de Ingredientes**: Número entero
- **Procedimiento**: Área de texto largo

### Nuevos Campos Requeridos

#### 1. Categorías (Selección Múltiple)
```
☐ Desayuno
☐ Almuerzo  
☐ Cena
☐ Postre
☐ Aperitivo
☐ Bebida
☐ Vegetariano
☐ Vegano
☐ Sin Gluten
☐ Bajo en Calorías
☐ [Agregar Nueva Categoría]
```

**Nota**: El usuario puede seleccionar múltiples categorías. Por ejemplo, una receta puede ser "Desayuno" + "Vegetariano" + "Bajo en Calorías".

#### 2. Tipos de Comida (Selección Múltiple)
```
☐ Desayuno
☐ Almuerzo
☐ Cena
☐ Merienda
☐ Snack
☐ Brunch
☐ Tea Time
☐ Cóctel
☐ [Agregar Nuevo Tipo]
```

**Nota**: Similar a las categorías, se pueden seleccionar múltiples tipos. Por ejemplo, "Desayuno" + "Brunch".

### Ejemplo de Formulario JSON

```json
{
  "userId": 1,
  "title": "Ensalada César Vegetariana",
  "description": "Ensalada fresca y saludable perfecta para el almuerzo",
  "preparationTime": "15 minutos",
  "servings": 2,
  "ingredientsCount": 8,
  "proced": "1. Lavar la lechuga...",
  "categoryIds": [2, 7],        // Almuerzo + Vegetariano
  "mealIds": [2]                // Solo Almuerzo
}
```

### Ejemplo de Respuesta del Sistema

```json
{
  "recipeId": 123,
  "userId": 1,
  "title": "Ensalada César Vegetariana",
  "description": "Ensalada fresca y saludable perfecta para el almuerzo",
  "createdAt": "2024-01-15T10:30:00",
  "preparationTime": "15 minutos",
  "servings": 2,
  "ingredientsCount": 8,
  "proced": "1. Lavar la lechuga...",
  "userName": "chef_edgar",
  "categories": [
    {
      "categoryId": 2,
      "name": "Almuerzo"
    },
    {
      "categoryId": 7,
      "name": "Vegetariano"
    }
  ],
  "meals": [
    {
      "mealId": 2,
      "name": "Almuerzo"
    }
  ],
  "tags": [
    "Fresco",
    "Saludable",
    "Rápido"
  ],
  "nutritionalInfo": null
}
```

## Flujo de Captura

### 1. **Paso 1: Información Básica**
- Usuario llena título, descripción, tiempo, porciones, etc.

### 2. **Paso 2: Selección de Categorías**
- Sistema muestra lista de categorías disponibles
- Usuario puede seleccionar múltiples opciones
- Opción de crear nueva categoría si no existe

### 3. **Paso 3: Selección de Tipos de Comida**
- Sistema muestra lista de tipos disponibles
- Usuario puede seleccionar múltiples opciones
- Opción de crear nuevo tipo si no existe

### 4. **Paso 4: Procedimiento**
- Usuario escribe los pasos de la receta

### 5. **Paso 5: Revisión y Guardado**
- Sistema muestra resumen de la receta
- Incluye todas las categorías y tipos seleccionados
- Usuario confirma y guarda

## Ventajas de esta Estructura

### Para el Usuario
1. **Clasificación Clara**: Sabe exactamente dónde clasificar su receta
2. **Búsqueda Fácil**: Puede encontrar recetas por categorías específicas
3. **Flexibilidad**: Una receta puede pertenecer a múltiples categorías

### Para el Sistema
1. **Organización**: Recetas bien categorizadas y organizadas
2. **Búsqueda Avanzada**: Filtros por categorías, tipos de comida
3. **Recomendaciones**: Sistema puede sugerir recetas similares
4. **Analytics**: Estadísticas de uso por categorías

## Implementación en el Frontend

### Componente de Selección Múltiple
```javascript
// Ejemplo en React
const CategorySelector = ({ selectedCategories, onSelectionChange }) => {
  const [categories, setCategories] = useState([]);
  
  return (
    <div className="category-selector">
      <h3>Selecciona las Categorías</h3>
      {categories.map(category => (
        <label key={category.categoryId}>
          <input
            type="checkbox"
            checked={selectedCategories.includes(category.categoryId)}
            onChange={(e) => {
              if (e.target.checked) {
                onSelectionChange([...selectedCategories, category.categoryId]);
              } else {
                onSelectionChange(selectedCategories.filter(id => id !== category.categoryId));
              }
            }}
          />
          {category.name}
        </label>
      ))}
    </div>
  );
};
```

### Validación del Formulario
```javascript
const validateRecipeForm = (formData) => {
  const errors = [];
  
  if (!formData.title) {
    errors.push("El título es obligatorio");
  }
  
  if (!formData.categoryIds || formData.categoryIds.length === 0) {
    errors.push("Debes seleccionar al menos una categoría");
  }
  
  if (!formData.mealIds || formData.mealIds.length === 0) {
    errors.push("Debes seleccionar al menos un tipo de comida");
  }
  
  return errors;
};
```

## Consideraciones Técnicas

### Base de Datos
- Las relaciones se manejan en tablas separadas
- Se mantiene la integridad referencial
- Índices para búsquedas eficientes

### API REST
- Endpoint POST `/api/recipes` para crear
- Endpoint PUT `/api/recipes/{id}` para actualizar
- Endpoint GET `/api/recipes/{id}` para obtener detalles
- Endpoint GET `/api/categories` para listar categorías
- Endpoint GET `/api/meals` para listar tipos de comida

### Validaciones
- Categorías y tipos de comida son obligatorios
- Se valida que los IDs existan en la base de datos
- Se previene duplicados en las relaciones

## Próximos Pasos

1. **Implementar Frontend**: Crear formularios con selección múltiple
2. **Validaciones**: Agregar validaciones del lado del cliente
3. **UX/UI**: Diseñar interfaz intuitiva para la selección
4. **Testing**: Probar flujos de creación y actualización
5. **Documentación**: Crear guías de usuario
