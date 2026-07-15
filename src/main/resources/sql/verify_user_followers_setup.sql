-- Script completo para verificar la configuración de UserFollowers
-- Ejecutar este script en la base de datos QA para diagnosticar problemas

PRINT '=== VERIFICACIÓN COMPLETA DE USERFOLLOWERS ===';
PRINT '';

-- 1. Verificar si la secuencia existe
PRINT '1. Verificando secuencia UserFollowersIdSequence...';
IF EXISTS (SELECT * FROM sys.sequences WHERE name = 'UserFollowersIdSequence')
BEGIN
    SELECT 
        name,
        start_value,
        increment,
        minimum_value,
        maximum_value,
        is_cycling
    FROM sys.sequences 
    WHERE name = 'UserFollowersIdSequence';
    PRINT '✓ Secuencia UserFollowersIdSequence existe';
END
ELSE
BEGIN
    PRINT '✗ ERROR: La secuencia UserFollowersIdSequence NO existe';
    PRINT 'Ejecuta primero: create_user_followers_sequence.sql';
END

PRINT '';

-- 2. Verificar si la tabla existe
PRINT '2. Verificando tabla UserFollowers...';
IF EXISTS (SELECT * FROM sysobjects WHERE name='UserFollowers' AND xtype='U')
BEGIN
    PRINT '✓ Tabla UserFollowers existe';
    
    -- Verificar estructura de columnas
    PRINT '';
    PRINT 'Estructura de columnas:';
    SELECT 
        COLUMN_NAME,
        DATA_TYPE,
        IS_NULLABLE,
        COLUMN_DEFAULT
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'UserFollowers'
    ORDER BY ORDINAL_POSITION;
    
    -- Verificar constraints
    PRINT '';
    PRINT 'Constraints de la tabla:';
    SELECT 
        CONSTRAINT_NAME,
        CONSTRAINT_TYPE
    FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
    WHERE TABLE_NAME = 'UserFollowers';
    
    -- Verificar claves foráneas
    PRINT '';
    PRINT 'Claves foráneas:';
    SELECT 
        fk.name AS FK_NAME,
        OBJECT_NAME(fk.parent_object_id) AS TABLE_NAME,
        COL_NAME(fkc.parent_object_id, fkc.parent_column_id) AS COLUMN_NAME,
        OBJECT_NAME(fk.referenced_object_id) AS REFERENCED_TABLE_NAME,
        COL_NAME(fkc.referenced_object_id, fkc.referenced_column_id) AS REFERENCED_COLUMN_NAME
    FROM sys.foreign_keys fk
    INNER JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id
    WHERE OBJECT_NAME(fk.parent_object_id) = 'UserFollowers';
    
END
ELSE
BEGIN
    PRINT '✗ ERROR: La tabla UserFollowers NO existe';
    PRINT 'Ejecuta primero: create_user_followers_table.sql';
END

PRINT '';
PRINT '=== FIN DE VERIFICACIÓN ===';

-- 3. Verificar si hay datos en la tabla
IF EXISTS (SELECT * FROM sysobjects WHERE name='UserFollowers' AND xtype='U')
BEGIN
    PRINT '';
    PRINT '3. Verificando datos en la tabla...';
    DECLARE @rowCount INT;
    SELECT @rowCount = COUNT(*) FROM UserFollowers;
    PRINT 'Número de registros en UserFollowers: ' + CAST(@rowCount AS VARCHAR);
    
    IF @rowCount > 0
    BEGIN
        PRINT 'Primeros 5 registros:';
        SELECT TOP 5 * FROM UserFollowers;
    END
END
