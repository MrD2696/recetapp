-- Script para crear la secuencia UserFollowersIdSequence en SQL Server
-- Ejecutar este script en la base de datos QA antes de crear la tabla UserFollowers

-- Crear la secuencia para el ID de UserFollowers
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'UserFollowersIdSequence')
BEGIN
    CREATE SEQUENCE UserFollowersIdSequence
    AS BIGINT
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CYCLE;
    
    PRINT 'Secuencia UserFollowersIdSequence creada exitosamente';
END
ELSE
BEGIN
    PRINT 'La secuencia UserFollowersIdSequence ya existe';
END

-- Verificar la secuencia
SELECT 
    name,
    start_value,
    increment,
    minimum_value,
    maximum_value,
    is_cycling
FROM sys.sequences 
WHERE name = 'UserFollowersIdSequence';
