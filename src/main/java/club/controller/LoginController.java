package club.controller;

import club.App;
import club.database.DatabaseManager;
import club.model.Admin;
import club.model.Member;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Properties;

/**
 * Controller for managing the login functionality.
 * Handles user authentication, "Remember Me" functionality, and navigation.
 */
public class LoginController extends BaseController {

    @FXML
    private Text welcomeText;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    private static final String REMEMBER_ME_FILE = DatabaseManager.SAVES_FOLDER + File.separator + "remember_me.properties";
    private static final String ENCRYPTION_KEY_FILE = DatabaseManager.SAVES_FOLDER + File.separator + "encryption.key";

    /**
     * Initializes the controller.
     * Loads saved credentials if "Remember Me" was previously selected.
     */
    @FXML
    private void initialize() {
        String clubName = App.getClub().getClubName();
        welcomeText.setText(clubName + "'s Management System");
        ensureSavesFolderExists();
        loadSavedCredentials();
    }

    /**
     * Handles the login process for both admins and members.
     * Validates credentials and navigates to the appropriate screen.
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate admin login
        if (validateAdminLogin(email, password)) {
            return;
        }

        // Validate member login
        if (validateMemberLogin(email, password)) {
            return;
        }

        // If no valid login, show an appropriate error message
        showLoginError(email);
    }

    /**
     * Validates admin login credentials.
     *
     * @param email    The admin's email.
     * @param password The admin's password.
     * @return true if the credentials are valid, false otherwise.
     */
    private boolean validateAdminLogin(String email, String password) {
        Admin admin = App.getClub().validateAdminLogin(email, password);
        if (admin != null) {
            handleRememberMe(email, password);
            App.setLoggedInUserEmail(admin.getEmail());
            navigateTo("/club/Dashboard.fxml");
            return true;
        }
        return false;
    }

    /**
     * Validates member login credentials.
     *
     * @param email    The member's email.
     * @param password The member's password.
     * @return true if the credentials are valid, false otherwise.
     */
    private boolean validateMemberLogin(String email, String password) {
        Member member = App.getClub().validateMemberLogin(email, password);
        if (member != null) {
            if (!member.isActive()) {
                showAlert("Login Failed", "Your account is inactive. Please contact the administrator.");
                return true; // Stop further processing
            }
            handleRememberMe(email, password);
            App.setLoggedInUserEmail(member.getEmail());
            navigateTo("/club/Dashboard.fxml");
            return true;
        }
        return false;
    }

    /**
     * Displays an error message if login fails.
     *
     * @param email The email entered by the user.
     */
    private void showLoginError(String email) {
        boolean isInactive = App.getClub().getMembers().stream()
                .anyMatch(m -> m.getEmail().equals(email) && !m.isActive());
        if (isInactive) {
            showAlert("Login Failed", "Your account is inactive. Please contact the administrator.");
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    /**
     * Ensures the saves folder exists for storing credentials and encryption keys.
     */
    private void ensureSavesFolderExists() {
        File savesFolder = new File(DatabaseManager.SAVES_FOLDER);
        if (!savesFolder.exists() && !savesFolder.mkdirs()) {
            System.err.println("Failed to create saves folder: " + DatabaseManager.SAVES_FOLDER);
        }
    }

    /**
     * Loads saved credentials if "Remember Me" was previously selected.
     */
    private void loadSavedCredentials() {
        try (InputStream input = new FileInputStream(REMEMBER_ME_FILE)) {
            Properties props = new Properties();
            props.load(input);
            String encryptedEmail = props.getProperty("email");
            String encryptedPassword = props.getProperty("password");

            if (encryptedEmail != null && encryptedPassword != null) {
                emailField.setText(decrypt(encryptedEmail));
                passwordField.setText(decrypt(encryptedPassword));
                rememberMeCheckBox.setSelected(true);
            }
        } catch (IOException e) {
            System.out.println("No saved credentials found.");
        }
    }

    /**
     * Handles the "Remember Me" functionality.
     * Saves or clears credentials based on the checkbox state.
     *
     * @param email    The email to save.
     * @param password The password to save.
     */
    private void handleRememberMe(String email, String password) {
        if (rememberMeCheckBox.isSelected()) {
            try (OutputStream output = new FileOutputStream(REMEMBER_ME_FILE)) {
                Properties props = new Properties();
                props.setProperty("email", encrypt(email));
                props.setProperty("password", encrypt(password));
                props.store(output, null);
            } catch (IOException e) {
                System.err.println("Failed to save credentials: " + e.getMessage());
            }
        } else {
            File file = new File(REMEMBER_ME_FILE);
            if (file.exists() && !file.delete()) {
                System.err.println("Failed to delete saved credentials.");
            }
        }
    }

    /**
     * Encrypts the given data using AES encryption.
     *
     * @param data The data to encrypt.
     * @return The encrypted data as a Base64-encoded string.
     * @throws IOException If encryption fails.
     */
    private String encrypt(String data) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getEncryptionKey());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IOException("Failed to encrypt data", e);
        }
    }

    /**
     * Decrypts the given encrypted data using AES encryption.
     *
     * @param encryptedData The encrypted data as a Base64-encoded string.
     * @return The decrypted data.
     * @throws IOException If decryption fails.
     */
    private String decrypt(String encryptedData) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getEncryptionKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IOException("Failed to decrypt data", e);
        }
    }

    /**
     * Retrieves the encryption key from the file or generates a new one if it doesn't exist.
     *
     * @return The encryption key.
     * @throws IOException If key generation or retrieval fails.
     */
    private Key getEncryptionKey() throws IOException {
        File keyFile = new File(ENCRYPTION_KEY_FILE);
        if (!keyFile.exists()) {
            try {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                SecretKey secretKey = keyGen.generateKey();
                try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                    fos.write(secretKey.getEncoded());
                }
                return secretKey;
            } catch (Exception e) {
                throw new IOException("Failed to generate encryption key", e);
            }
        } else {
            try (FileInputStream fis = new FileInputStream(keyFile)) {
                byte[] keyBytes = fis.readAllBytes();
                return new SecretKeySpec(keyBytes, "AES");
            }
        }
    }

    /**
     * Handles the "Forgot Password" action.
     * Displays a message to contact the administrator.
     */
    @FXML
    private void handleForgotPassword() {
        showAlert("Forgot Password", "Please contact the administrator.");
    }

    /**
     * Navigates to the sign-up screen.
     */
    @FXML
    private void goToSignUp() {
        navigateTo("/club/SignUp.fxml");
    }

    /**
     * Navigates to the specified FXML file.
     *
     * @param fxmlPath The path to the FXML file.
     */
    private void navigateTo(String fxmlPath) {
        try {
            App.switchScene(fxmlPath);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to the requested screen.");
        }
    }
}