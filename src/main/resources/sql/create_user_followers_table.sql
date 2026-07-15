-- Script para verificar y corregir la tabla UserFollowers en SQL Server
-- Ejecutar este script en la base de datos QA

-- Verificar si la tabla existe
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserFollowers' AND xtype='U')
BEGIN
    PRINT 'La tabla UserFollowers NO existe. Debes crearla primero con la estructura correcta.';
    PRINT 'Usa el siguiente comando:';
    PRINT '';
    PRINT 'CREATE TABLE UserFollowers (';
    PRINT '    id BIGINT PRIMARY KEY DEFAULT NEXT VALUE FOR UserFollowersIdSequence,';
    PRINT '    follower_id INT NOT NULL,';
    PRINT '    followed_id INT NOT NULL,';
    PRINT '    createdAt DATETIME NOT NULL DEFAULT GETDATE(),';
    PRINT '    CONSTRAINT UC_UserFollowers UNIQUE (follower_id, followed_id),';
    PRINT '    FOREIGN KEY (follower_id) REFERENCES Users(userId) ON DELETE CASCADE,';
    PRINT '    FOREIGN KEY (followed_id) REFERENCES Users(userId) ON DELETE NO ACTION';
    PRINT ');';
    RETURN;
END

-- Verificar si la columna createdAt existe
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('UserFollowers') AND name = 'createdAt')
BEGIN
    PRINT 'ERROR: La columna createdAt NO existe en la tabla UserFollowers';
    PRINT 'La tabla debe tener la columna createdAt (camelCase), no created_at';
    PRINT '';
    PRINT 'Para corregir esto, ejecuta:';
    PRINT 'ALTER TABLE UserFollowers ADD createdAt DATETIME NOT NULL DEFAULT GETDATE();';
    PRINT '';
    PRINT 'O si ya tienes created_at, renómbrala:';
    PRINT 'EXEC sp_rename ''UserFollowers.created_at'', ''createdAt'', ''COLUMN'';';
END
ELSE
BEGIN
    PRINT 'La columna createdAt existe correctamente en la tabla UserFollowers';
END

-- Verificar la estructura completa de la tabla
PRINT '';
PRINT 'Estructura actual de la tabla UserFollowers:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'UserFollowers'
ORDER BY ORDINAL_POSITION;

-- Verificar las constraints
PRINT '';
PRINT 'Constraints de la tabla UserFollowers:';
SELECT 
    CONSTRAINT_NAME,
    CONSTRAINT_TYPE
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
WHERE TABLE_NAME = 'UserFollowers';
