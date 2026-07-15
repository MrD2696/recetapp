# Solución al Error de UserFollowers

## Problema Identificado

El error indica que la columna `created_at` no existe en la tabla `UserFollowers`:

```
SQL Error: 207, SQLState: S0001
Invalid column name 'created_at'
```

## Causa del Problema

La entidad Java estaba configurada para usar `created_at` (con guión bajo), pero tu tabla SQL Server usa `createdAt` (camelCase).

## Solución Implementada

### 1. Entidad Java Corregida ✅

- **Archivo**: `src/main/java/com/recetapp/recetapp/model/UserFollower.java`
- **Cambios realizados**:
  - Columna `created_at` → `createdAt`
  - Constraint único `unique_follow` → `UC_UserFollowers`
  - Estrategia de ID: `IDENTITY` → `SEQUENCE` con `UserFollowersIdSequence`

### 2. Scripts SQL Creados ✅

#### `create_user_followers_sequence.sql`
- Crea la secuencia `UserFollowersIdSequence` necesaria para el ID

#### `create_user_followers_table.sql`
- Verifica la estructura de la tabla existente
- Sugiere correcciones si es necesario

#### `verify_user_followers_setup.sql`
- Script completo de diagnóstico
- Verifica secuencia, tabla, constraints y claves foráneas

## Pasos para Resolver

### Paso 1: Ejecutar Scripts SQL
1. **Conectar a tu base de datos QA**
2. **Ejecutar en orden**:
   ```sql
   -- 1. Crear secuencia (si no existe)
   EXECUTE create_user_followers_sequence.sql
   
   -- 2. Verificar tabla
   EXECUTE create_user_followers_table.sql
   
   -- 3. Diagnóstico completo
   EXECUTE verify_user_followers_setup.sql
   ```

### Paso 2: Verificar Estructura de Tabla
Tu tabla debe tener esta estructura exacta:
```sql
CREATE TABLE UserFollowers (
    id BIGINT PRIMARY KEY DEFAULT NEXT VALUE FOR UserFollowersIdSequence,
    follower_id INT NOT NULL,
    followed_id INT NOT NULL,
    createdAt DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UC_UserFollowers UNIQUE (follower_id, followed_id),
    FOREIGN KEY (follower_id) REFERENCES Users(userId) ON DELETE CASCADE,
    FOREIGN KEY (followed_id) REFERENCES Users(userId) ON DELETE NO ACTION
);
```

### Paso 3: Reiniciar Aplicación
Después de verificar la base de datos, reinicia tu aplicación Spring Boot.

## Verificación

Para confirmar que el problema está resuelto:

1. **Ejecuta el endpoint**: `POST /api/follow/12` (usuario 12 siguiendo a usuario 13)
2. **Verifica logs**: No debe aparecer el error de columna `created_at`
3. **Verifica base de datos**: Debe crearse el registro en `UserFollowers`

## Archivos Modificados

- ✅ `UserFollower.java` - Entidad corregida
- ✅ `create_user_followers_sequence.sql` - Script para secuencia
- ✅ `create_user_followers_table.sql` - Script para tabla
- ✅ `verify_user_followers_setup.sql` - Script de diagnóstico

## Notas Importantes

- **Nomenclatura**: SQL Server es sensible a mayúsculas/minúsculas
- **Secuencia**: La entidad ahora usa `GenerationType.SEQUENCE` en lugar de `IDENTITY`
- **Constraints**: Los nombres de constraints deben coincidir exactamente

## Si el Problema Persiste

1. Verifica que la secuencia `UserFollowersIdSequence` exista
2. Confirma que la tabla tenga exactamente la estructura mostrada arriba
3. Verifica que no haya conflictos de nombres de constraints
4. Revisa los logs de Hibernate para más detalles
