package com.recetapp.recetapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.ApiResponse;
import com.recetapp.recetapp.dto.UserDTO;
import com.recetapp.recetapp.service.UserService;

/**
 * Controller for managing user-related API endpoints.
 * This class handles HTTP requests related to user operations such as retrieval, creation, 
 * and updating user profiles.
 * <p>
 * All endpoints are prefixed with /api/users, allowing for a modular and easily 

 * maintainable API structure.
 * </p>
 *
 * @author Edgar Islas
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow CORS for testing
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // GET /api/users - Get all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/users - Getting all users"));
        
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.getAllUsers()"));
            List<UserDTO> users = userService.getAllUsers();
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Successfully retrieved " + users.size() + " users"));
            ApiResponse<List<UserDTO>> response = ApiResponse.success(
                MessageConstants.USERS_RETRIEVED_SUCCESSFULLY, 
                users, 
                HttpStatus.OK.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending successful response with " + users.size() + " users"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error getting all users: " + e.getMessage()));
            ApiResponse<List<UserDTO>> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending error response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // GET /api/users/{userId} - Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/users/" + userId + " - Getting user by ID"));
        
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.getUserById(" + userId + ")"));
            UserDTO user = userService.getUserById(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Successfully retrieved user: " + user.getUsername()));
            ApiResponse<UserDTO> response = ApiResponse.success(
                MessageConstants.USER_RETRIEVED_SUCCESSFULLY, 
                user, 
                HttpStatus.OK.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending successful response for user: " + user.getUsername()));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "User not found with ID: " + userId));
            ApiResponse<UserDTO> response = ApiResponse.error(
                e.getMessage(), 
                HttpStatus.NOT_FOUND.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 404 response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Unexpected error getting user by ID " + userId + ": " + e.getMessage()));
            ApiResponse<UserDTO> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println("[UserController] Sending 500 response: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // POST /api/users - Create new user
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "POST /api/users - Creating new user"));
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Request body: username=" + userDTO.getUsername() + ", email=" + userDTO.getEmail()));

        // Validar que password y confirmPassword coincidan antes de llamar al servicio
        if (userDTO.getPassword() == null || userDTO.getConfirmPassword() == null || !userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            ApiResponse<UserDTO> response = ApiResponse.error(
                MessageConstants.PASSWORDS_DO_NOT_MATCH,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.createUser()"));
            UserDTO createdUser = userService.createUser(userDTO);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Successfully created user with ID: " + createdUser.getUserId()));
            ApiResponse<UserDTO> response = ApiResponse.success(
                "Usuario registrado exitosamente",
                createdUser,
                HttpStatus.CREATED.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 201 response for created user: " + createdUser.getUsername()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Validation error creating user: " + e.getMessage()));
            ApiResponse<UserDTO> response = ApiResponse.error(
                e.getMessage(), 
                HttpStatus.BAD_REQUEST.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 400 response: " + e.getMessage()));
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Unexpected error creating user: " + e.getMessage()));
            ApiResponse<UserDTO> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 500 response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // PUT /api/users/{userId} - Update user
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "PUT /api/users/" + userId + " - Updating user"));
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Request body: username=" + userDTO.getUsername() + ", email=" + userDTO.getEmail()));
        
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.updateUser(" + userId + ")"));
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Successfully updated user with ID: " + updatedUser.getUserId()));
            ApiResponse<UserDTO> response = ApiResponse.success(
                MessageConstants.USER_UPDATED_SUCCESSFULLY, 
                updatedUser, 
                HttpStatus.OK.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 200 response for updated user: " + updatedUser.getUsername()));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error updating user with ID " + userId + ": " + e.getMessage()));
            ApiResponse<UserDTO> response = ApiResponse.error(
                e.getMessage(), 
                HttpStatus.NOT_FOUND.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 404 response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Unexpected error updating user with ID " + userId + ": " + e.getMessage()));
            ApiResponse<UserDTO> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 500 response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // DELETE /api/users/{userId} - Delete user
        @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer userId) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "DELETE /api/users/" + userId + " - Deleting user"));
        
        try {
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Calling UserService.deleteUser(" + userId + ")"));
            userService.deleteUser(userId);
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Successfully deleted user with ID: " + userId));
            
            // Crear respuesta con mensaje de confirmación en lugar de null
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.USER_DELETED_SUCCESSFULLY,
                "User with ID " + userId + " has been successfully deleted",
                HttpStatus.OK.value()
            );
            
            System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 200 response for deleted user"));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Error deleting user with ID " + userId + ": " + e.getMessage()));
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(), 
                HttpStatus.NOT_FOUND.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 404 response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Unexpected error deleting user with ID " + userId + ": " + e.getMessage()));
            ApiResponse<String> response = ApiResponse.error(
                MessageConstants.INTERNAL_SERVER_ERROR, 
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            System.err.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 500 response: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Endpoint para validar usuario con código de activación
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateUser(@RequestBody ValidateUserRequest request) {
        try {
            userService.validateUser(request.getEmail(), request.getCode());
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.USER_VALIDATED_SUCCESSFULLY,
                null,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Endpoint para solicitar recuperación de contraseña
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            userService.forgotPassword(request.getEmail());
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.PASSWORD_RESET_CODE_SENT,
                null,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Endpoint para validar código de recuperación
    @PostMapping("/validate-reset")
    public ResponseEntity<ApiResponse<String>> validateReset(@RequestBody ValidateUserRequest request) {
        try {
            userService.validateResetCode(request.getEmail(), request.getCode());
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.PASSWORD_RESET_CODE_VALIDATED,
                null,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Endpoint para cambiar la contraseña
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request.getEmail(), request.getPassword(), request.getConfirmPassword());
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.PASSWORD_RESET_SUCCESS,
                null,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Endpoint para reenviar código de activación
    @PostMapping("/resend-activation-code")
    public ResponseEntity<ApiResponse<String>> resendActivationCode(@RequestBody ForgotPasswordRequest request) {
        try {
            userService.resendActivationCode(request.getEmail());
            ApiResponse<String> response = ApiResponse.success(
                MessageConstants.ACTIVATION_CODE_RESENT,
                null,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<String> response = ApiResponse.error(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Endpoint para validar si el usuario es nuevo (llama al procedure)
    @GetMapping("/validate-new-user/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateNewUser(@PathVariable Integer userId) {
        try {
            Map<String, Object> result = userService.validateNewUserProcedure(userId);
            ApiResponse<Map<String, Object>> response = ApiResponse.success(
                "User new status validated",
                result,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Map<String, Object>> response = ApiResponse.error(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DTO para la petición de validación
    public static class ValidateUserRequest {
        private String email;
        private String code;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }

    // DTO para forgot-password
    public static class ForgotPasswordRequest {
        private String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    // DTO para reset-password
    public static class ResetPasswordRequest {
        private String email;
        private String password;
        private String confirmPassword;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getConfirmPassword() { return confirmPassword; }
        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    }
    
    // Test endpoint to verify API is working
    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "GET /api/users/test - Testing API endpoint"));
        
        ApiResponse<String> response = ApiResponse.success(
            MessageConstants.API_WORKING_CORRECTLY, 
            "API is running successfully", 
            HttpStatus.OK.value()
        );
        
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Test endpoint working correctly"));
        System.out.println(LogConstants.buildLogMessage(LogConstants.CONTROLLER_PREFIX, "Sending 200 response for test endpoint"));
        return ResponseEntity.ok(response);
    }
}
