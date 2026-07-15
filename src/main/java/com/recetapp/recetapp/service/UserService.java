package com.recetapp.recetapp.service;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recetapp.recetapp.constants.LogConstants;
import com.recetapp.recetapp.constants.MessageConstants;
import com.recetapp.recetapp.dto.LoginResponse;
import com.recetapp.recetapp.dto.UserDTO;
import com.recetapp.recetapp.model.Recipe;
import com.recetapp.recetapp.model.RecipeCategory;
import com.recetapp.recetapp.model.RecipeMeal;
import com.recetapp.recetapp.model.RecipeTag;
import com.recetapp.recetapp.model.User;
import com.recetapp.recetapp.model.UserFollower;
import com.recetapp.recetapp.model.UserSession;
import com.recetapp.recetapp.repository.NutriInformationRepository;
import com.recetapp.recetapp.repository.RecipeCategoryRepository;
import com.recetapp.recetapp.repository.RecipeImageRepository;
import com.recetapp.recetapp.repository.RecipeMealRepository;
import com.recetapp.recetapp.repository.RecipeRepository;
import com.recetapp.recetapp.repository.RecipeTagRepository;
import com.recetapp.recetapp.repository.UserFollowerRepository;
import com.recetapp.recetapp.repository.UserRepository;
import com.recetapp.recetapp.repository.UserSessionRepository;
import com.recetapp.recetapp.util.BCryptPasswordEncoderUtil;

/**
 * Service class for managing user operations.
 * This class provides methods for retrieving, creating, updating, and deleting
 * user profiles.
 * 
 * @author Edgar Islas
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    private RecipeTagRepository recipeTagRepository;

    @Autowired
    private RecipeMealRepository recipeMealRepository;

    @Autowired
    private RecipeImageRepository recipeImageRepository;

    @Autowired
    private NutriInformationRepository nutriInformationRepository;

    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Get all users
    public List<UserDTO> getAllUsers() {
        System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX, "Getting all users..."));
        try {
            List<User> users = userRepository.findAll();
            System.out.println(
                    LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX, "Found " + users.size() + " users"));

            List<UserDTO> userDTOs = users.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Successfully converted " + userDTOs.size() + " users to DTOs"));
            return userDTOs;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Error getting all users: " + e.getMessage()));
            throw e;
        }
    }

    // Get user by ID
    public UserDTO getUserById(Integer userId) {
        System.out.println(
                LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX, "Getting user by ID: " + userId));
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException(MessageConstants.USER_NOT_FOUND + userId));

            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "User found: " + user.getUsername() + " (ID: " + userId + ")"));
            UserDTO userDTO = convertToDTO(user);
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Successfully converted user to DTO"));
            return userDTO;
        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "User not found with ID: " + userId));
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Error getting user by ID " + userId + ": " + e.getMessage()));
            throw e;
        }
    }

    // Create new user
    public UserDTO createUser(UserDTO userDTO) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                "Creating new user: " + userDTO.getUsername()));

        try {
            // Validar que password y confirmPassword coincidan
            if (userDTO.getPassword() == null || userDTO.getConfirmPassword() == null
                    || !userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                throw new RuntimeException("Las contraseñas no coinciden o están vacías");
            }
            // Validate that username doesn't exist
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                        "Username already exists: " + userDTO.getUsername()));
                throw new RuntimeException(MessageConstants.USERNAME_ALREADY_EXISTS + userDTO.getUsername());
            }
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Username validation passed: " + userDTO.getUsername()));

            // Validate that email doesn't exist
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                        "Email already exists: " + userDTO.getEmail()));
                throw new RuntimeException(MessageConstants.EMAIL_ALREADY_EXISTS + userDTO.getEmail());
            }
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Email validation passed: " + userDTO.getEmail()));

            // Encode password using BCrypt
            String passwordHash = BCryptPasswordEncoderUtil.encodePassword(userDTO.getPassword());
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Encoded password using BCrypt for user: " + userDTO.getUsername()));

            // Generar código de activación de 4 dígitos
            String activationCode = String.format("%04d", new java.util.Random().nextInt(10000));

            User user = new User(userDTO.getUsername(), passwordHash, userDTO.getEmail());
            user.setCode(activationCode);
            user.setValidateUser(false);
            user.setIsNew(1); // Usuario nuevo
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Created user entity, saving to database..."));

            User savedUser = userRepository.save(user);
            // Enviar correo de activación
            try {
                sendActivationEmail(savedUser.getEmail(), activationCode);
            } catch (Exception e) {
                System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                        "Error al enviar correo de activación: " + e.getMessage()));
            }
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "User saved successfully with ID: " + savedUser.getUserId()));

            UserDTO result = convertToDTO(savedUser);
            System.out.println("[UserService] User creation completed successfully");
            return result;
        } catch (RuntimeException e) {
            System.err.println("[UserService] Validation error creating user: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("[UserService] Error creating user: " + e.getMessage());
            throw e;
        }
    }

    // Update user
    public UserDTO updateUser(Integer userId, UserDTO userDTO) {
        System.out.println("[UserService] Updating user with ID: " + userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException(MessageConstants.USER_NOT_FOUND + userId));

            System.out.println("[UserService] User found for update: " + user.getUsername() + " (ID: " + userId + ")");

            // Validar que la contraseña enviada sea igual a la actual
            if (userDTO.getPassword() == null
                    || !BCryptPasswordEncoderUtil.verifyPassword(userDTO.getPassword(), user.getPasswordHash())) {
                throw new RuntimeException(MessageConstants.INVALID_PASSWORD);
            }
            System.out.println("[UserService] Password validation passed for update");

            // Validar que haya cambios en username o email
            if (user.getUsername().equals(userDTO.getUsername()) && user.getEmail().equals(userDTO.getEmail())) {
                throw new RuntimeException(MessageConstants.NO_CHANGES_FOUND);
            }
            System.out.println("[UserService] Username validation passed for update");

            // Validate that new email is not used by another user
            if (!user.getEmail().equals(userDTO.getEmail()) &&
                    userRepository.existsByEmail(userDTO.getEmail())) {
                System.err.println("[UserService] New email already exists: " + userDTO.getEmail());
                throw new RuntimeException(MessageConstants.EMAIL_ALREADY_EXISTS + userDTO.getEmail());
            }
            System.out.println("[UserService] Email validation passed for update");

            System.out.println("[UserService] Updating user fields...");
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());

            User updatedUser = userRepository.save(user);
            System.out.println("[UserService] User updated successfully");

            UserDTO result = convertToDTO(updatedUser);
            System.out.println("[UserService] User update completed successfully");
            return result;
        } catch (RuntimeException e) {
            System.err.println("[UserService] Error updating user: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("[UserService] Unexpected error updating user: " + e.getMessage());
            throw e;
        }
    }

    // Delete user
    @Transactional
    public void deleteUser(Integer userId) {
        System.out.println("[UserService] Deleting user with ID: " + userId);

        try {
            if (!userRepository.existsById(userId)) {
                System.err.println("[UserService] User not found for deletion: " + userId);
                throw new RuntimeException(MessageConstants.USER_NOT_FOUND + userId);
            }

            System.out.println("[UserService] User found for deletion, proceeding with cleanup...");

            // 1. Eliminar todas las sesiones del usuario (activas e inactivas)
            System.out.println("[UserService] Deleting user sessions...");
            List<UserSession> userSessions = userSessionRepository.findByUserId(userId);
            if (!userSessions.isEmpty()) {
                userSessionRepository.deleteAll(userSessions);
                System.out.println(
                        "[UserService] Deleted " + userSessions.size() + " user sessions (active and inactive)");
            } else {
                System.out.println("[UserService] No user sessions found to delete");
            }

            // 2. Eliminar todas las relaciones de followers
            System.out.println("[UserService] Deleting user followers relationships...");
            long followersCount = userFollowerRepository.countByFollowedUserId(userId);
            long followingCount = userFollowerRepository.countByFollowerUserId(userId);

            if (followersCount > 0) {
                List<UserFollower> followers = userFollowerRepository.findByFollowedUserId(userId);
                userFollowerRepository.deleteAll(followers);
                System.out.println("[UserService] Deleted " + followersCount + " follower relationships");
            }

            if (followingCount > 0) {
                List<UserFollower> following = userFollowerRepository.findByFollowerUserId(userId);
                userFollowerRepository.deleteAll(following);
                System.out.println("[UserService] Deleted " + followingCount + " following relationships");
            }

            // 3. Obtener todas las recetas del usuario
            List<Recipe> userRecipes = recipeRepository.findByUserId(userId);
            System.out.println("[UserService] Found " + userRecipes.size() + " recipes to delete");

            // 4. Para cada receta, eliminar dependencias y luego la receta
            for (Recipe recipe : userRecipes) {
                Integer recipeId = recipe.getRecipeId();
                System.out.println("[UserService] Deleting recipe " + recipeId + " and its dependencies...");

                // Eliminar información nutricional
                if (nutriInformationRepository.existsByRecipeId(recipeId)) {
                    nutriInformationRepository.deleteByRecipeId(recipeId);
                    System.out.println("[UserService] Deleted nutritional info for recipe " + recipeId);
                }

                // Eliminar imágenes de la receta
                if (recipeImageRepository.existsByRecipeId(recipeId)) {
                    long imageCount = recipeImageRepository.countByRecipeId(recipeId);
                    recipeImageRepository.deleteByRecipeId(recipeId);
                    System.out.println("[UserService] Deleted " + imageCount + " images for recipe " + recipeId);
                }

                // Eliminar categorías de la receta
                List<RecipeCategory> recipeCategories = recipeCategoryRepository.findByRecipeId(recipeId);
                if (!recipeCategories.isEmpty()) {
                    long categoryCount = recipeCategories.size();
                    recipeCategoryRepository.deleteByRecipeId(recipeId);
                    System.out.println("[UserService] Deleted " + categoryCount + " categories for recipe " + recipeId);
                }

                // Eliminar tags de la receta
                List<RecipeTag> recipeTags = recipeTagRepository.findByRecipeId(recipeId);
                if (!recipeTags.isEmpty()) {
                    long tagCount = recipeTags.size();
                    recipeTagRepository.deleteByRecipeId(recipeId);
                    System.out.println("[UserService] Deleted " + tagCount + " tags for recipe " + recipeId);
                }

                // Eliminar tipos de comida de la receta
                List<RecipeMeal> recipeMeals = recipeMealRepository.findByRecipeId(recipeId);
                if (!recipeMeals.isEmpty()) {
                    long mealCount = recipeMeals.size();
                    recipeMealRepository.deleteByRecipeId(recipeId);
                    System.out.println("[UserService] Deleted " + mealCount + " meals for recipe " + recipeId);
                }

                // Eliminar la receta
                recipeRepository.deleteById(recipeId);
                System.out.println("[UserService] Deleted recipe " + recipeId);
            }

            // 5. Finalmente, eliminar el usuario
            userRepository.deleteById(userId);
            System.out.println("[UserService] User deleted successfully with ID: " + userId);

        } catch (RuntimeException e) {
            System.err.println("[UserService] Error deleting user: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("[UserService] Unexpected error deleting user: " + e.getMessage());
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    // Login method
    public LoginResponse login(String username, String password, String ipAddress, String userAgent,
            String appVersion) {
        System.out.println("[UserService] Attempting login for username: " + username);

        try {
            // Find user by username
            User user = userRepository.findByUsername(username);
            if (user == null) {
                System.err.println("[UserService] User not found: " + username);
                throw new RuntimeException(MessageConstants.USER_NOT_FOUND + username);
            }
            // Validar que el usuario esté activado
            if (user.getValidateUser() == null || !user.getValidateUser()) {
                System.err.println("[UserService] User not validated: " + username);
                throw new RuntimeException(MessageConstants.USER_NOT_VALIDATED);
            }

            System.out.println("[UserService] User found: " + user.getUsername() + " (ID: " + user.getUserId() + ")");

            // Validate password using BCrypt
            String storedPassword = user.getPasswordHash();
            System.out.println("[UserService] Stored BCrypt hash: " + storedPassword);

            if (!BCryptPasswordEncoderUtil.verifyPassword(password, storedPassword)) {
                System.err.println("[UserService] Invalid password for user: " + username);
                throw new RuntimeException("Invalid password");
            }

            System.out.println("[UserService] Password validation successful for user: " + username);

            // Create or manage session (enforces max 2 sessions)
            sessionService.createSession(user, ipAddress, userAgent);
            System.out.println("[UserService] Session created for user: " + username);

            // Update last login time, set online status y appVersion
            user.setLastLogin(LocalDateTime.now());
            user.setOnline(true); // User is now online
            user.setAppVersion(appVersion); // Guardar versión de la app
            userRepository.save(user);
            System.out.println(
                    "[UserService] Last login updated, online status set to true y appVersion guardada para user: "
                            + username);

            // Create login response
            LoginResponse loginResponse = new LoginResponse(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getLastLogin(),
                    user.getOnline());

            System.out.println("[UserService] Login successful for user: " + username);
            return loginResponse;

        } catch (RuntimeException e) {
            System.err.println("[UserService] Login failed for user " + username + ": " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println(
                    "[UserService] Unexpected error during login for user " + username + ": " + e.getMessage());
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    // Logout method using stored procedure
    public void logout(String username) {
        System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                "Processing logout with stored procedure for username: " + username));

        try {
            String sql = "{call sp_LogoutUser(?, ?, ?, ?)}";

            // Ejecutar el procedimiento almacenado usando CallableStatement
            Map<String, Object> result = jdbcTemplate.execute(
                    sql,
                    (org.springframework.jdbc.core.CallableStatementCallback<Map<String, Object>>) cs -> {
                        cs.setString(1, username);
                        cs.registerOutParameter(2, Types.INTEGER);
                        cs.registerOutParameter(3, Types.NVARCHAR);
                        cs.registerOutParameter(4, Types.INTEGER);
                        cs.execute();

                        Map<String, Object> output = new HashMap<>();
                        output.put("ResultCode", cs.getInt(2));
                        output.put("ResultMessage", cs.getString(3));
                        output.put("SessionsTerminated", cs.getInt(4));
                        return output;
                    });

            // Validar que el resultado no sea null
            if (result == null) {
                throw new RuntimeException("Error ejecutando el procedimiento almacenado de logout");
            }

            // Extraer valores del resultado
            Object resultCodeObj = result.get("ResultCode");
            Object resultMessageObj = result.get("ResultMessage");
            Object sessionsTerminatedObj = result.get("SessionsTerminated");

            Integer resultCode = resultCodeObj != null ? (Integer) resultCodeObj : 1;
            String resultMessage = resultMessageObj != null ? (String) resultMessageObj : "Error desconocido";
            Integer sessionsTerminated = sessionsTerminatedObj != null ? (Integer) sessionsTerminatedObj : 0;

            if (resultCode != 0) {
                System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                        "Logout failed: " + resultMessage));
                throw new RuntimeException(resultMessage);
            }

            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Logout successful for user: " + username));
            System.out.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Sessions terminated: " + sessionsTerminated));
            System.out.println(
                    LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX, "Result message: " + resultMessage));

        } catch (RuntimeException e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Logout failed for user " + username + ": " + e.getMessage()));
            throw e;
        } catch (Exception e) {
            System.err.println(LogConstants.buildLogMessage(LogConstants.USER_SERVICE_PREFIX,
                    "Unexpected error during logout for user " + username + ": " + e.getMessage()));
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }

    // Validar usuario con código de activación
    public void validateUser(String emailOrUsername, String code) {
        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsername(emailOrUsername);
        }
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND_EMAIL + emailOrUsername);
        }
        if (user.getCode() == null || !user.getCode().equals(code)) {
            throw new RuntimeException(MessageConstants.ACTIVATION_CODE_INCORRECT);
        }
        user.setValidateUser(true);
        user.setCode(null); // Limpia el código después de validar
        userRepository.save(user);
    }

    // Enviar código de recuperación de contraseña
    public void forgotPassword(String emailOrUsername) {
        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsername(emailOrUsername);
        }
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND_EMAIL + emailOrUsername);
        }
        String resetCode = String.format("%04d", new java.util.Random().nextInt(10000));
        user.setCode(resetCode);
        userRepository.save(user);
        sendPasswordResetEmail(user.getEmail(), resetCode);
    }

    // Validar código de recuperación
    public void validateResetCode(String emailOrUsername, String code) {
        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsername(emailOrUsername);
        }
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND_EMAIL + emailOrUsername);
        }
        if (user.getCode() == null || !user.getCode().equals(code)) {
            throw new RuntimeException(MessageConstants.PASSWORD_RESET_CODE_NOT_VALID);
        }
        // Marca el código como validado (puedes usar un campo temporal, aquí solo lo
        // dejamos pasar)
    }

    // Cambiar contraseña después de validar código
    public void resetPassword(String emailOrUsername, String password, String confirmPassword) {
        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsername(emailOrUsername);
        }
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND_EMAIL + emailOrUsername);
        }
        if (user.getCode() == null) {
            throw new RuntimeException(MessageConstants.PASSWORD_RESET_CODE_NOT_VALID);
        }
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            throw new RuntimeException(MessageConstants.PASSWORDS_DO_NOT_MATCH);
        }
        String passwordHash = BCryptPasswordEncoderUtil.encodePassword(password);
        user.setPasswordHash(passwordHash);
        user.setCode(null); // Limpia el código después de cambiar la contraseña
        userRepository.save(user);
    }

    // Reenviar código de activación
    public void resendActivationCode(String emailOrUsername) {
        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsername(emailOrUsername);
        }
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND_EMAIL + emailOrUsername);
        }
        String activationCode = String.format("%04d", new java.util.Random().nextInt(10000));
        user.setCode(activationCode);
        userRepository.save(user);
        sendActivationEmail(user.getEmail(), activationCode);
    }

    // Validar si el usuario es nuevo usando el procedure ValidateNewUser
    public Map<String, Object> validateNewUserProcedure(Integer userId) {
        String sql = "{call ValidateNewUser(?)}";
        return jdbcTemplate.queryForMap(sql, userId);
    }

    // Buscar el email asociado a un username o email (para login no validado)
    public String findEmailByUsernameOrEmail(String usernameOrEmail) {
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user != null) {
            return user.getEmail();
        }
        user = userRepository.findByEmail(usernameOrEmail);
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    // Convert entity to DTO
    private UserDTO convertToDTO(User user) {
        System.out.println("[UserService] Converting user entity to DTO: " + user.getUsername());
        UserDTO dto = new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getLastLogin(),
                user.getOnline());
        System.out.println("[UserService] User entity converted to DTO successfully");
        return dto;
    }

    // Método para enviar correo de activación
    private void sendActivationEmail(String to, String code) {
        System.out.println("[UserService] Enviando correo de activación a: " + to + " con código: " + code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Código de activación de tu cuenta");
        message.setText("Tu código de activación es: " + code);
        mailSender.send(message);
    }

    // Método para enviar correo de recuperación
    private void sendPasswordResetEmail(String to, String code) {
        System.out.println("[UserService] Enviando correo de recuperación a: " + to + " con código: " + code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password reset code");
        message.setText("Your password reset code is: " + code);
        mailSender.send(message);
    }
}
