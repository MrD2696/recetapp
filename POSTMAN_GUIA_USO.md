# 🚀 **GUÍA COMPLETA DE USO - POSTMAN RECETAPP FOLLOWERS API**

## 📋 **ARCHIVOS NECESARIOS**

1. **`RecetApp_Followers_API.postman_collection.json`** - Colección de endpoints
2. **`RecetApp_Followers_Environment.postman_environment.json`** - Variables de entorno

---

## 🔧 **CONFIGURACIÓN INICIAL**

### **Paso 1: Importar Colección**
1. Abre **Postman**
2. Click en **"Import"** (botón azul)
3. Arrastra el archivo `RecetApp_Followers_API.postman_collection.json`
4. Click en **"Import"**

### **Paso 2: Importar Variables de Entorno**
1. Click en **"Import"** nuevamente
2. Arrastra el archivo `RecetApp_Followers_Environment.postman_environment.json`
3. Click en **"Import"**

### **Paso 3: Seleccionar Entorno**
1. En la esquina superior derecha, selecciona **"RecetApp - Followers Environment"**
2. Verifica que las variables estén cargadas en **"Environment Quick Look"**

---

## 🎯 **FLUJO DE TESTING RECOMENDADO**

### **📱 Escenario 1: Usuario Nuevo (Sin Followers)**

```
1️⃣ Crear Usuario
2️⃣ Login Usuario  
3️⃣ Obtener Perfil (debería mostrar 0 followers)
4️⃣ Obtener Lista de Followers (debería estar vacía)
5️⃣ Obtener Lista de Following (debería estar vacía)
```

### **📱 Escenario 2: Sistema de Followers**

```
1️⃣ Crear Usuario 2
2️⃣ Usuario 1 sigue a Usuario 2
3️⃣ Verificar perfil Usuario 2 (debería mostrar 1 follower)
4️⃣ Verificar perfil Usuario 1 (debería mostrar 1 following)
5️⃣ Obtener lista de followers de Usuario 2
6️⃣ Obtener lista de following de Usuario 1
```

### **📱 Escenario 3: Cache y Rendimiento**

```
1️⃣ Obtener perfil Usuario 1 (primera vez = lento)
2️⃣ Obtener perfil Usuario 1 (segunda vez = rápido, cache)
3️⃣ Usuario 1 deja de seguir a Usuario 2
4️⃣ Obtener perfil Usuario 1 (cache invalido, lento)
5️⃣ Obtener perfil Usuario 1 (nuevo cache, rápido)
```

---

## 🔐 **ENDPOINTS DE AUTENTICACIÓN**

### **POST /api/auth/login**
```json
{
    "username": "{{testUsername}}",
    "password": "{{testPassword}}"
}
```

**✅ Respuesta Exitosa:**
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "sessionId": "abc123-def456-ghi789",
        "user": {
            "userId": 1,
            "username": "testuser",
            "email": "test@recetapp.com"
        }
    }
}
```

**📝 IMPORTANTE:** Copia el `sessionId` y pégalo en la variable `{{sessionId}}` del entorno.

---

## 👥 **ENDPOINTS DE FOLLOWERS**

### **POST /api/follow/{followerId}**
```json
{
    "followedUserId": 2
}
```

**✅ Respuesta Exitosa:**
```json
{
    "success": true,
    "message": "Usuario seguido exitosamente",
    "data": "Follow operation completed"
}
```

**❌ Respuesta de Error:**
```json
{
    "success": false,
    "message": "El usuario ya estaba siendo seguido",
    "data": null
}
```

### **DELETE /api/follow/{followerId}/{followedId}**
**Sin body** - Solo URL con IDs

**✅ Respuesta Exitosa:**
```json
{
    "success": true,
    "message": "Usuario dejado de seguir exitosamente",
    "data": "Unfollow operation completed"
}
```

---

## 📊 **ENDPOINTS DE CONSULTA**

### **GET /api/follow/profile/{userId}?currentUserId={currentUserId}**

**✅ Respuesta Exitosa:**
```json
{
    "success": true,
    "message": "Profile retrieved successfully",
    "data": {
        "userId": 2,
        "username": "usuario2",
        "email": "usuario2@email.com",
        "createdAt": "2025-08-30T14:30:00",
        "lastLogin": "2025-08-30T14:30:00",
        "online": true,
        "followersCount": 1,
        "followingCount": 0,
        "isFollowing": false
    }
}
```

### **GET /api/follow/{userId}/followers**

**✅ Respuesta Exitosa:**
```json
{
    "success": true,
    "message": "Followers retrieved successfully",
    "data": [
        {
            "userId": 1,
            "username": "usuario1",
            "email": "usuario1@email.com",
            "createdAt": "2025-08-30T14:00:00",
            "lastLogin": "2025-08-30T14:30:00",
            "online": true,
            "followersCount": 0,
            "followingCount": 1,
            "isFollowing": false
        }
    ]
}
```

---

## 🔧 **ENDPOINTS DE CACHE**

### **POST /api/follow/cache/clear/{userId}**
Limpia el cache específico de un usuario

### **POST /api/follow/cache/clear/all**
Limpia todo el cache de followers

---

## 📝 **VARIABLES DE ENTORNO DISPONIBLES**

| Variable | Valor por Defecto | Descripción |
|----------|-------------------|-------------|
| `{{base_url}}` | `http://localhost:8080` | URL base de la API |
| `{{sessionId}}` | `` | ID de sesión (se llena con login) |
| `{{followerId}}` | `1` | ID del usuario que sigue |
| `{{followedId}}` | `2` | ID del usuario seguido |
| `{{userId}}` | `1` | ID del usuario para consultas |
| `{{currentUserId}}` | `1` | ID del usuario actual |
| `{{testUsername}}` | `testuser` | Username para testing |
| `{{testEmail}}` | `test@recetapp.com` | Email para testing |
| `{{testPassword}}` | `testpass123` | Password para testing |

---

## 🧪 **TESTS AUTOMÁTICOS**

La colección incluye tests automáticos que verifican:

✅ **Status Code**: 200, 201, o 400  
✅ **Estructura de Response**: success, message, data  
✅ **Logging**: Console logs para debugging  

---

## 🚨 **CASOS DE ERROR COMUNES**

### **Error 400: Usuario no encontrado**
```json
{
    "success": false,
    "message": "Usuario no encontrado con ID: 999",
    "data": null
}
```

### **Error 400: No puede seguirse a sí mismo**
```json
{
    "success": false,
    "message": "El usuario 1 no puede seguirse a sí mismo",
    "data": null
}
```

### **Error 400: Ya está siguiendo al usuario**
```json
{
    "success": false,
    "message": "El usuario ya estaba siendo seguido",
    "data": null
}
```

---

## 📊 **MONITOREO DE CACHE**

### **Verificar Cache Hit/Miss en Logs:**
```
[CacheService] Cache miss para followers count del usuario 1, consultando base de datos
[CacheService] Cache hit para followers count del usuario 1
```

### **Verificar Invalidación de Cache:**
```
[FollowService] Usuario 1 ahora sigue al usuario 2
[CacheService] Cache de followers limpiado completamente
```

---

## 🔄 **FLUJO COMPLETO DE TESTING**

### **🔄 Test 1: Sistema Básico**
```
1. Crear Usuario 1
2. Crear Usuario 2  
3. Login Usuario 1
4. Verificar perfiles iniciales (0 followers)
5. Usuario 1 sigue a Usuario 2
6. Verificar cambios en perfiles
7. Logout Usuario 1
```

### **🔄 Test 2: Cache y Rendimiento**
```
1. Login Usuario 1
2. Obtener perfil Usuario 2 (primera vez)
3. Obtener perfil Usuario 2 (segunda vez - cache)
4. Usuario 1 deja de seguir a Usuario 2
5. Obtener perfil Usuario 2 (cache invalido)
6. Verificar que cache se regenera
```

### **🔄 Test 3: Múltiples Usuarios**
```
1. Crear Usuario 3
2. Usuario 1 sigue a Usuario 3
3. Usuario 2 sigue a Usuario 1
4. Verificar conteos en todos los perfiles
5. Obtener listas de followers/following
6. Verificar relaciones bidireccionales
```

---

## 💡 **TIPS Y TRUCOS**

### **🔄 Actualizar Variables Automáticamente:**
```javascript
// En el test del login, agregar:
if (pm.response.code === 200) {
    const response = pm.response.json();
    if (response.success && response.data.sessionId) {
        pm.environment.set("sessionId", response.data.sessionId);
    }
}
```

### **🔄 Verificar Cache en Acción:**
1. Ejecuta el mismo endpoint 2 veces
2. Primera vez: lento (cache miss)
3. Segunda vez: rápido (cache hit)
4. Cambia datos (follow/unfollow)
5. Vuelve a ejecutar: lento (cache invalido)

### **🔄 Monitorear Logs de la Aplicación:**
Los logs mostrarán:
- `[CacheService] Cache miss` = Consulta a BD
- `[CacheService] Cache hit` = Datos desde cache
- `[FollowService]` = Operaciones de followers

---

## 🎯 **MÉTRICAS DE RENDIMIENTO**

### **⏱️ Tiempos Esperados:**
- **Primera consulta**: 100-500ms (BD + Cache)
- **Consultas subsecuentes**: 1-5ms (Cache)
- **Después de cambios**: 100-500ms (Cache invalido)
- **Regeneración de cache**: 100-500ms (BD + Cache)

### **📊 Reducción de Carga en BD:**
- **Sin cache**: 100% de consultas a BD
- **Con cache**: 10-20% de consultas a BD
- **Mejora**: 80-90% de reducción

---

## 🚀 **PRÓXIMOS PASOS**

1. **✅ Importar colección y entorno**
2. **✅ Configurar variables de entorno**
3. **✅ Ejecutar flujo básico de testing**
4. **✅ Verificar funcionamiento del cache**
5. **✅ Probar casos de error**
6. **✅ Monitorear logs de rendimiento**

---

## 📞 **SOPORTE**

Si encuentras problemas:

1. **Verifica logs de la aplicación**
2. **Confirma que la app esté corriendo en puerto 8080**
3. **Verifica que las variables de entorno estén configuradas**
4. **Revisa que la base de datos esté accesible**

---

**🎉 ¡Disfruta probando tu API de Followers optimizada con cache! 🚀**
