package club.controller;

import club.App;
import club.model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for managing the sign-up process.
 * Handles user registration and navigation to the login screen.
 */
public class SignUpController extends BaseController{

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    /**
     * Handles the sign-up process.
     * Validates the input fields, creates a new member, and saves the data.
     */
    @FXML
    private void handleSignUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText().trim();

        if (!validateInputs(name, email, phone, password)) {
            return;
        }

        if (isEmailInUse(email)) {
            showAlert("Error", "A member with this email already exists.");
            return;
        }

        // Create and add the new member
        Member newMember = new Member(name, email, phone, true, password);

        // Let the membership manager generate and assign the ID
        String membershipId = App.getClub().registerNewMember(newMember);

        // Save the updated club data
        App.getDataManager().saveClubData(App.getClub());

        showAlert("Success", "Sign-up successful! Your Membership ID is: " + membershipId);

        // Navigate to the login screen
        navigateToLogin();
    }

    /**
     * Validates the input fields for the sign-up process.
     *
     * @param name     The name of the user.
     * @param email    The email of the user.
     * @param phone    The phone number of the user.
     * @param password The password of the user.
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInputs(String name, String email, String phone, String password) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given email is already in use by another member.
     *
     * @param email The email to check.
     * @return true if the email is in use, false otherwise.
     */
    private boolean isEmailInUse(String email) {
        return App.getClub().getMembers().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    /**
     * Navigates to the login screen.
     */
    @FXML
    private void goToLogin() {
        navigateToLogin();
    }

    /**
     * Navigates to the login screen.
     */
    private void navigateToLogin() {
        try {
            App.switchScene("/club/Login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to the login screen.");
        }
    }
}