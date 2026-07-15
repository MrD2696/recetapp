-- =====================================================
-- Script para crear tablas de Categorías, Tags y Comidas
-- RecetApp - Sistema de Recetas
-- =====================================================

-- =====================================================
-- Creación de Secuencias
-- =====================================================

-- Categories
CREATE SEQUENCE CategoryIdSequence
    AS INT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 CYCLE;

CREATE SEQUENCE RecipeCategoryIdSequence
    AS INT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 CYCLE;

-- Tags
CREATE SEQUENCE TagIdSequence
    AS INT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 CYCLE;

CREATE SEQUENCE RecipeTagIdSequence
    AS INT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 CYCLE;

-- Meals
CREATE SEQUENCE MealIdSequence
    AS INT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 CYCLE;

CREATE SEQUENCE RecipeMealIdSequence
    AS INT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 CYCLE;

-- =====================================================
-- Creación de Tablas
-- =====================================================

-- Tabla Categories
CREATE TABLE Categories (
    categoryId INT PRIMARY KEY DEFAULT NEXT VALUE FOR CategoryIdSequence,
    name NVARCHAR(100) NOT NULL UNIQUE
);

-- Tabla Tags
CREATE TABLE Tags (
    tagId INT PRIMARY KEY DEFAULT NEXT VALUE FOR TagIdSequence,
    name NVARCHAR(100) NOT NULL UNIQUE
);

-- Tabla Meals
CREATE TABLE Meals (
    mealId INT PRIMARY KEY DEFAULT NEXT VALUE FOR MealIdSequence,
    name NVARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- Creación de Tablas de Relación
-- =====================================================

-- Relación Recipes <-> Categories
CREATE TABLE RecipeCategories (
    recipeCategoryId INT PRIMARY KEY DEFAULT NEXT VALUE FOR RecipeCategoryIdSequence,
    recipeId INT NOT NULL,
    categoryId INT NOT NULL,
    FOREIGN KEY (recipeId) REFERENCES Recipes(recipeId) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES Categories(categoryId) ON DELETE CASCADE,
    CONSTRAINT UC_RecipeCategories UNIQUE (recipeId, categoryId)
);

-- Relación Recipes <-> Tags
CREATE TABLE RecipeTags (
    recipeTagId INT PRIMARY KEY DEFAULT NEXT VALUE FOR RecipeTagIdSequence,
    recipeId INT NOT NULL,
    tagId INT NOT NULL,
    FOREIGN KEY (recipeId) REFERENCES Recipes(recipeId) ON DELETE CASCADE,
    FOREIGN KEY (tagId) REFERENCES Tags(tagId) ON DELETE CASCADE,
    CONSTRAINT UC_RecipeTags UNIQUE (recipeId, tagId)
);

-- Relación Recipes <-> Meals
CREATE TABLE RecipeMeals (
    recipeMealId INT PRIMARY KEY DEFAULT NEXT VALUE FOR RecipeMealIdSequence,
    recipeId INT NOT NULL,
    mealId INT NOT NULL,
    FOREIGN KEY (recipeId) REFERENCES Recipes(recipeId) ON DELETE CASCADE,
    FOREIGN KEY (mealId) REFERENCES Meals(mealId) ON DELETE CASCADE,
    CONSTRAINT UC_RecipeMeals UNIQUE (recipeId, mealId)
);

-- =====================================================
-- Creación de Índices para Mejorar el Rendimiento
-- =====================================================

-- Índices para búsquedas por nombre
CREATE INDEX IX_Categories_Name ON Categories(name);
CREATE INDEX IX_Tags_Name ON Tags(name);
CREATE INDEX IX_Meals_Name ON Meals(name);

-- Índices para las relaciones
CREATE INDEX IX_RecipeCategories_RecipeId ON RecipeCategories(recipeId);
CREATE INDEX IX_RecipeCategories_CategoryId ON RecipeCategories(categoryId);
CREATE INDEX IX_RecipeTags_RecipeId ON RecipeTags(recipeId);
CREATE INDEX IX_RecipeTags_TagId ON RecipeTags(tagId);
CREATE INDEX IX_RecipeMeals_RecipeId ON RecipeMeals(recipeId);
CREATE INDEX IX_RecipeMeals_MealId ON RecipeMeals(mealId);

-- =====================================================
-- Datos de Ejemplo (Opcional)
-- =====================================================

-- Insertar algunas categorías comunes
INSERT INTO Categories (name) VALUES 
('Desayuno'),
('Almuerzo'),
('Cena'),
('Postre'),
('Aperitivo'),
('Bebida'),
('Vegetariano'),
('Vegano'),
('Sin Gluten'),
('Bajo en Calorías');

-- Insertar algunos tags comunes
INSERT INTO Tags (name) VALUES 
('Fácil'),
('Rápido'),
('Saludable'),
('Tradicional'),
('Internacional'),
('Picante'),
('Dulce'),
('Salado'),
('Fresco'),
('Horneado');

-- Insertar algunos tipos de comida
INSERT INTO Meals (name) VALUES 
('Desayuno'),
('Almuerzo'),
('Cena'),
('Merienda'),
('Snack'),
('Brunch'),
('Tea Time'),
('Cóctel');

-- =====================================================
-- Verificación de la Creación
-- =====================================================

-- Verificar que las tablas se crearon correctamente
SELECT 'Categories' as TableName, COUNT(*) as RecordCount FROM Categories
UNION ALL
SELECT 'Tags', COUNT(*) FROM Tags
UNION ALL
SELECT 'Meals', COUNT(*) FROM Meals
UNION ALL
SELECT 'RecipeCategories', COUNT(*) FROM RecipeCategories
UNION ALL
SELECT 'RecipeTags', COUNT(*) FROM RecipeTags
UNION ALL
SELECT 'RecipeMeals', COUNT(*) FROM RecipeMeals;

-- Verificar las secuencias
SELECT 'CategoryIdSequence' as SequenceName, NEXT VALUE FOR CategoryIdSequence as NextValue
UNION ALL
SELECT 'TagIdSequence', NEXT VALUE FOR TagIdSequence
UNION ALL
SELECT 'MealIdSequence', NEXT VALUE FOR MealIdSequence;
