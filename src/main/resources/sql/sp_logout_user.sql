-- =============================================
-- Procedimiento Almacenado: sp_LogoutUser
-- Descripción: Realiza el logout de un usuario
-- Parámetros:
--   @Username: Nombre de usuario a hacer logout
-- Retorna:
--   @ResultCode: 0 = Éxito, 1 = Usuario no encontrado
--   @ResultMessage: Mensaje descriptivo del resultado
--   @SessionsTerminated: Número de sesiones terminadas
-- =============================================
CREATE PROCEDURE sp_LogoutUser
    @Username NVARCHAR(50),
    @ResultCode INT OUTPUT,
    @ResultMessage NVARCHAR(255) OUTPUT,
    @SessionsTerminated INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @UserId INT;
    DECLARE @LogoutTime DATETIME2 = GETDATE();
    
    -- Inicializar variables de salida
    SET @ResultCode = 1;
    SET @ResultMessage = '';
    SET @SessionsTerminated = 0;
    
    BEGIN TRY
        BEGIN TRANSACTION;
        
        -- Buscar el userId por username
        SELECT @UserId = userId
        FROM Users
        WHERE username = @Username;
        
        -- Verificar si el usuario existe
        IF @UserId IS NULL
        BEGIN
            SET @ResultCode = 1;
            SET @ResultMessage = 'Usuario no encontrado: ' + @Username;
            ROLLBACK TRANSACTION;
            RETURN;
        END
        
        -- Terminar todas las sesiones activas del usuario
        UPDATE UserSessions
        SET isActive = 0,
            lastActivity = @LogoutTime
        WHERE userId = @UserId
          AND isActive = 1;
        
        SET @SessionsTerminated = @@ROWCOUNT;
        
        -- Actualizar el usuario: lastLogin y online = false
        UPDATE Users
        SET lastLogin = @LogoutTime,
            online = 0
        WHERE userId = @UserId;
        
        -- Éxito
        SET @ResultCode = 0;
        SET @ResultMessage = 'Logout exitoso para el usuario: ' + @Username;
        
        COMMIT TRANSACTION;
        
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
        
        SET @ResultCode = 1;
        SET @ResultMessage = 'Error durante el logout: ' + ERROR_MESSAGE();
        SET @SessionsTerminated = 0;
    END CATCH
END
GO
