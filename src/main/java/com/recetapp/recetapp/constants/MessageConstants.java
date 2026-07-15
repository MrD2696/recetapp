package com.recetapp.recetapp.constants;

/**
 * Class that contains message constants used throughout the Recetapp application.
 * <p>
 * This class centralizes all success, error, validation, and HTTP status messages
 * to facilitate reuse and maintenance.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     throw new NotFoundException(MessageConstants.USER_NOT_FOUND + userId);
 * </pre>
 * </p>
 *
 * @author Edgar Islas
 * @version 1.0
 */


public class MessageConstants {
    
    // SUCCESS MESSAGES
    public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";
    public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    public static final String USER_RETRIEVED_SUCCESSFULLY = "User retrieved successfully";
    public static final String USERS_RETRIEVED_SUCCESSFULLY = "Users retrieved successfully";
    public static final String API_WORKING_CORRECTLY = "Users API working correctly";
    
    // ERROR MESSAGES
    public static final String USER_NOT_FOUND = "User not found with ID: ";
    public static final String USERNAME_ALREADY_EXISTS = "User already exists with username: ";
    public static final String EMAIL_ALREADY_EXISTS = "User already exists with email: ";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error occurred";
    public static final String VALIDATION_ERROR = "Validation error";
    public static final String BAD_REQUEST = "Bad request";
    
    // VALIDATION MESSAGES
    public static final String USERNAME_REQUIRED = "Username is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String USERNAME_LENGTH_ERROR = "Username must be between 3 and 50 characters";
    public static final String EMAIL_FORMAT_ERROR = "Invalid email format";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match";
    
    // LOGIN MESSAGES
    public static final String LOGIN_SUCCESSFUL = "Login successful";
    public static final String LOGIN_FAILED = "Login failed";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String USER_NOT_FOUND_LOGIN = "User not found";
    public static final String USER_NOT_VALIDATED = "Account has not been validated. Please check your email and validate your account.";
    
    // LOGOUT MESSAGES
    public static final String LOGOUT_SUCCESSFUL = "Logout successful";
    public static final String LOGOUT_FAILED = "Logout failed";
    
    // ONLINE STATUS MESSAGES
    public static final String USER_ONLINE = "User is now online";
    public static final String USER_OFFLINE = "User is now offline";
    
    // SESSION MESSAGES
    public static final String SESSION_CREATED = "Session created successfully";
    public static final String SESSION_TERMINATED = "Session terminated successfully";
    public static final String MAX_SESSIONS_REACHED = "Maximum sessions reached, oldest session terminated";
    public static final String SESSION_NOT_FOUND = "Session not found";
    public static final String USER_VALIDATED_SUCCESSFULLY = "User validated successfully";
    public static final String USER_NOT_FOUND_EMAIL = "User not found with email: ";
    public static final String ACTIVATION_CODE_INCORRECT = "Activation code incorrect";
    public static final String ACTIVATION_CODE_RESENT = "Activation code resent to your email";
    
    // HTTP STATUS MESSAGES
    public static final String HTTP_200_OK = "OK";
    public static final String HTTP_201_CREATED = "Created";
    public static final String HTTP_204_NO_CONTENT = "No Content";
    public static final String HTTP_400_BAD_REQUEST = "Bad Request";
    public static final String HTTP_401_UNAUTHORIZED = "Unauthorized";
    public static final String HTTP_404_NOT_FOUND = "Not Found";
    public static final String HTTP_500_INTERNAL_SERVER_ERROR = "Internal Server Error";
    
    // FOLLOWERS MESSAGES
    public static final String USER_FOLLOWED_SUCCESSFULLY = "User followed successfully";
    public static final String USER_ALREADY_FOLLOWED = "User is already being followed";
    public static final String USER_UNFOLLOWED_SUCCESSFULLY = "User unfollowed successfully";
    public static final String USER_NOT_FOLLOWING = "User is not being followed";
    public static final String USER_CANNOT_FOLLOW_SELF = "User cannot follow themselves";
    public static final String PROFILE_RETRIEVED_SUCCESSFULLY = "User profile retrieved successfully";
    public static final String FOLLOWING_USERS_RETRIEVED_SUCCESSFULLY = "Following users retrieved successfully";
    public static final String FOLLOWERS_RETRIEVED_SUCCESSFULLY = "Followers retrieved successfully";
    public static final String FOLLOW_CHECK_COMPLETED = "Follow relationship check completed";
    
    // FOLLOWERS ERROR MESSAGES
    public static final String ERROR_FOLLOWING_USER = "Error following user";
    public static final String ERROR_UNFOLLOWING_USER = "Error unfollowing user";
    public static final String ERROR_RETRIEVING_PROFILE = "Error retrieving user profile";
    public static final String ERROR_RETRIEVING_FOLLOWING = "Error retrieving following users";
    public static final String ERROR_RETRIEVING_FOLLOWERS = "Error retrieving followers";
    public static final String ERROR_CHECKING_FOLLOW = "Error checking follow relationship";
    
    // RECIPE MESSAGES
    public static final String RECIPES_RETRIEVED_SUCCESSFULLY = "Recipes retrieved successfully";
    public static final String RECIPE_RETRIEVED_SUCCESSFULLY = "Recipe retrieved successfully";
    public static final String RECIPE_CREATED_SUCCESSFULLY = "Recipe created successfully";
    public static final String RECIPE_UPDATED_SUCCESSFULLY = "Recipe updated successfully";
    public static final String RECIPE_DELETED_SUCCESSFULLY = "Recipe deleted successfully";
    public static final String RECIPE_IMAGE_UPLOADED_SUCCESSFULLY = "Recipe image uploaded successfully";
    public static final String RECIPE_IMAGE_DELETED_SUCCESSFULLY = "Recipe image deleted successfully";
    public static final String RECIPE_IMAGES_RETRIEVED_SUCCESSFULLY = "Recipe images retrieved successfully";
    public static final String USER_IMAGES_RETRIEVED_SUCCESSFULLY = "User images retrieved successfully";
    public static final String MAIN_IMAGE_RETRIEVED_SUCCESSFULLY = "Main image retrieved successfully";
    public static final String NO_IMAGES_FOUND_FOR_RECIPE = "No images found for this recipe";
    
    // RECIPE ERROR MESSAGES
    public static final String ERROR_RETRIEVING_RECIPES = "Error retrieving recipes";
    public static final String ERROR_RETRIEVING_RECIPE = "Error retrieving recipe";
    public static final String ERROR_CREATING_RECIPE = "Error creating recipe";
    public static final String ERROR_UPDATING_RECIPE = "Error updating recipe";
    public static final String ERROR_DELETING_RECIPE = "Error deleting recipe";
    public static final String ERROR_UPLOADING_IMAGE = "Error uploading image";
    public static final String ERROR_DELETING_IMAGE = "Error deleting image";
    public static final String ERROR_RETRIEVING_IMAGES = "Error retrieving images";
    
    // NUTRITION MESSAGES
    public static final String NUTRITION_INFO_RETRIEVED_SUCCESSFULLY = "Nutrition information retrieved successfully";
    public static final String NUTRITION_INFO_CREATED_SUCCESSFULLY = "Nutrition information created successfully";
    public static final String NUTRITION_INFO_UPDATED_SUCCESSFULLY = "Nutrition information updated successfully";
    public static final String NUTRITION_INFO_DELETED_SUCCESSFULLY = "Nutrition information deleted successfully";
    
    // NUTRITION ERROR MESSAGES
    public static final String ERROR_RETRIEVING_NUTRITION = "Error retrieving nutrition information";
    public static final String ERROR_CREATING_NUTRITION = "Error creating nutrition information";
    public static final String ERROR_UPDATING_NUTRITION = "Error updating nutrition information";
    public static final String ERROR_DELETING_NUTRITION = "Error deleting nutrition information";
    
    // SEARCH MESSAGES
    public static final String SEARCH_COMPLETED_SUCCESSFULLY = "Search completed successfully";
    public static final String ERROR_IN_SEARCH = "Error in search";
    
    public static final String PASSWORD_RESET_CODE_SENT = "Password reset code sent to your email";
    public static final String PASSWORD_RESET_CODE_VALIDATED = "Password reset code validated";
    public static final String PASSWORD_RESET_SUCCESS = "Password has been reset successfully";
    public static final String PASSWORD_RESET_CODE_NOT_VALID = "Password reset code is not valid";
    public static final String NO_CHANGES_FOUND = "No changes found";
    
}
