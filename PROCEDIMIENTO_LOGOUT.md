# Procedimiento Almacenado para Logout

## Descripción
Este documento describe el procedimiento almacenado `sp_LogoutUser` que se utiliza para realizar el logout de un usuario en SQL Server.

## Instalación

### 1. Ejecutar el Script SQL
Ejecuta el script `sp_logout_user.sql` en tu base de datos SQL Server:

```sql
-- El script se encuentra en: src/main/resources/sql/sp_logout_user.sql
```

### 2. Verificar la Creación
Puedes verificar que el procedimiento se creó correctamente ejecutando:

```sql
SELECT * FROM sys.procedures WHERE name = 'sp_LogoutUser';
```

## Parámetros del Procedimiento

| Parámetro | Tipo | Dirección | Descripción |
|-----------|------|-----------|-------------|
| `@Username` | NVARCHAR(50) | INPUT | Nombre de usuario a hacer logout |
| `@ResultCode` | INT | OUTPUT | Código de resultado: 0 = Éxito, 1 = Error |
| `@ResultMessage` | NVARCHAR(255) | OUTPUT | Mensaje descriptivo del resultado |
| `@SessionsTerminated` | INT | OUTPUT | Número de sesiones terminadas |

## Funcionalidad

El procedimiento realiza las siguientes operaciones:

1. **Busca el usuario** por username
2. **Termina todas las sesiones activas** del usuario (establece `isActive = 0` y actualiza `lastActivity`)
3. **Actualiza el usuario**:
   - Establece `lastLogin` con la fecha/hora actual
   - Establece `online = 0` (false)

## Ejemplo de Uso en SQL Server

```sql
DECLARE @ResultCode INT;
DECLARE @ResultMessage NVARCHAR(255);
DECLARE @SessionsTerminated INT;

EXEC sp_LogoutUser 
    @Username = 'usuario123',
    @ResultCode = @ResultCode OUTPUT,
    @ResultMessage = @ResultMessage OUTPUT,
    @SessionsTerminated = @SessionsTerminated OUTPUT;

SELECT 
    @ResultCode AS ResultCode,
    @ResultMessage AS ResultMessage,
    @SessionsTerminated AS SessionsTerminated;
```

## Uso desde Java

### Método en UserService

El método `logoutWithStoredProcedure(String username)` está disponible en `UserService`:

```java
@Autowired
private UserService userService;

// Ejemplo de uso
try {
    userService.logoutWithStoredProcedure("usuario123");
    // Logout exitoso
} catch (RuntimeException e) {
    // Manejar error
    System.err.println("Error: " + e.getMessage());
}
```

### Cambiar el método logout para usar el procedimiento almacenado

Si deseas que el método `logout()` use el procedimiento almacenado en lugar de la implementación actual, puedes modificar el método en `UserController` o `AuthController`:

```java
// En lugar de:
userService.logout(logoutRequest.getUsername());

// Usar:
userService.logoutWithStoredProcedure(logoutRequest.getUsername());
```

## Códigos de Retorno

| Código | Significado |
|--------|-------------|
| 0 | Logout exitoso |
| 1 | Error (usuario no encontrado u otro error) |

## Manejo de Errores

El procedimiento maneja errores de la siguiente manera:

- Si el usuario no existe, retorna `ResultCode = 1` con un mensaje descriptivo
- Si ocurre un error durante la ejecución, se hace rollback de la transacción y se retorna el mensaje de error
- Todas las operaciones se realizan dentro de una transacción para garantizar consistencia

## Notas Importantes

1. **Transacciones**: El procedimiento usa transacciones para garantizar que todas las operaciones se completen o se reviertan en caso de error.

2. **Sesiones**: Solo se terminan las sesiones activas (`isActive = 1`). Las sesiones ya inactivas no se modifican.

3. **Timestamp**: El `lastLogin` y `lastActivity` se actualizan con `GETDATE()` del servidor SQL Server.

4. **Concurrencia**: El procedimiento maneja correctamente la concurrencia mediante transacciones.

## Pruebas

Para probar el procedimiento almacenado:

```sql
-- 1. Crear un usuario de prueba (si no existe)
-- 2. Crear algunas sesiones activas para ese usuario
-- 3. Ejecutar el procedimiento
-- 4. Verificar que:
--    - Las sesiones se marcaron como inactivas
--    - El usuario tiene online = 0
--    - El lastLogin se actualizó
```

## Troubleshooting

### Error: "Usuario no encontrado"
- Verifica que el username existe en la tabla `Users`
- Verifica que el username se está pasando correctamente (case-sensitive)

### Error: "Error durante el logout"
- Revisa los logs de SQL Server para más detalles
- Verifica que las tablas `Users` y `UserSessions` existen
- Verifica los permisos del usuario de base de datos
