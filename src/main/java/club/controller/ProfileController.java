package club.controller;

import club.App;
import club.model.Admin;
import club.model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for managing the profile view.
 * Handles displaying and updating the logged-in user's profile details.
 */
public class ProfileController extends BaseController{

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneLabel;

    @FXML
    private PasswordField passwordField;

    private Object loggedInUser; // Can be either Admin or Member

    /**
     * Initializes the controller.
     * Loads the logged-in user's details and populates the fields.
     */
    @FXML
    private void initialize() {
        String loggedInEmail = App.getLoggedInUserEmail();
        if (loggedInEmail == null) {
            showAlert("Error", "No user is logged in.");
            return;
        }

        loadUserDetails(loggedInEmail);
    }

    /**
     * Loads the logged-in user's details and populates the fields.
     *
     * @param loggedInEmail The email of the logged-in user.
     */
    private void loadUserDetails(String loggedInEmail) {
        Admin admin = App.getClub().getAdmins().stream()
                .filter(a -> a.getEmail().equals(loggedInEmail))
                .findFirst()
                .orElse(null);

        if (admin != null) {
            setupAdminProfile(admin);
        } else {
            Member member = App.getClub().getMembers().stream()
                    .filter(m -> m.getEmail().equals(loggedInEmail))
                    .findFirst()
                    .orElse(null);

            if (member != null) {
                setupMemberProfile(member);
            } else {
                showAlert("Error", "User not found.");
            }
        }
    }

    /**
     * Sets up the profile fields for an admin user.
     *
     * @param admin The logged-in admin.
     */
    private void setupAdminProfile(Admin admin) {
        loggedInUser = admin;
        nameField.setText(admin.getName());
        emailField.setText(admin.getEmail());
        passwordField.setText(admin.getPassword());
        hidePhoneFields();
    }

    /**
     * Sets up the profile fields for a member user.
     *
     * @param member The logged-in member.
     */
    private void setupMemberProfile(Member member) {
        loggedInUser = member;
        nameField.setText(member.getName());
        emailField.setText(member.getEmail());
        phoneField.setText(member.getPhone());
        passwordField.setText(member.getPassword());
        showPhoneFields();
    }

    /**
     * Hides the phone fields for admin users.
     */
    private void hidePhoneFields() {
        phoneLabel.setVisible(false);
        phoneLabel.setManaged(false);
        phoneField.setVisible(false);
        phoneField.setManaged(false);
    }

    /**
     * Shows the phone fields for member users.
     */
    private void showPhoneFields() {
        phoneLabel.setVisible(true);
        phoneLabel.setManaged(true);
        phoneField.setVisible(true);
        phoneField.setManaged(true);
    }

    /**
     * Handles saving the updated profile details.
     * Validates the input fields and updates the user's profile.
     */
    @FXML
    private void saveChanges() {
        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newPassword = passwordField.getText().trim();

        if (!validateInputs(newName, newEmail, newPassword)) {
            return;
        }

        if (loggedInUser instanceof Admin) {
            updateAdminProfile((Admin) loggedInUser, newName, newEmail, newPassword);
        } else if (loggedInUser instanceof Member) {
            updateMemberProfile((Member) loggedInUser, newName, newEmail, newPhone, newPassword);
        }

        App.getDataManager().saveClubData(App.getClub());
        showAlert("Success", "Profile updated successfully!");
    }

    /**
     * Validates the input fields for the profile.
     *
     * @param name     The name of the user.
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return true if all required fields are valid, false otherwise.
     */
    private boolean validateInputs(String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Name, Email, and Password cannot be empty.");
            return false;
        }
        return true;
    }

    /**
     * Updates the profile details for an admin user.
     *
     * @param admin    The admin user.
     * @param name     The updated name.
     * @param email    The updated email.
     * @param password The updated password.
     */
    private void updateAdminProfile(Admin admin, String name, String email, String password) {
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(password);
    }

    /**
     * Updates the profile details for a member user.
     *
     * @param member   The member user.
     * @param name     The updated name.
     * @param email    The updated email.
     * @param phone    The updated phone number.
     * @param password The updated password.
     */
    private void updateMemberProfile(Member member, String name, String email, String phone, String password) {
        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);
        member.setPassword(password);
    }

    /**
     * Navigates back to the dashboard.
     */
    @FXML
    private void goToDashboard() {
        try {
            App.switchScene("/club/Dashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to the dashboard.");
        }
    }
}