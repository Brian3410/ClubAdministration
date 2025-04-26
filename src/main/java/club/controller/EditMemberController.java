package club.controller;

import club.App;
import club.model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for editing an existing member's details.
 */
public class EditMemberController extends BaseController{

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label membershipIdField; // Displays the membership ID

    @FXML
    private CheckBox activeCheckBox; // Checkbox for active status

    private Member selectedMember; // The member being edited

    /**
     * Initializes the controller.
     * Loads the selected member and populates the fields with their data.
     */
    public void initialize() {
        // Load the selected member from the App class
        selectedMember = App.getSelectedMember();

        if (selectedMember != null) {
            populateFields();
        } else {
            showAlert("Error", "No member selected for editing.");
            navigateToMemberList();
        }
    }

    /**
     * Populates the input fields with the selected member's data.
     */
    private void populateFields() {
        nameField.setText(selectedMember.getName());
        emailField.setText(selectedMember.getEmail());
        phoneField.setText(selectedMember.getPhone());
        membershipIdField.setText(selectedMember.getMembershipId());
        activeCheckBox.setSelected(selectedMember.isActive());
    }

    /**
     * Handles saving the updated member details.
     * Validates the input fields, updates the member, and saves the changes.
     */
    @FXML
    private void saveMember() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        boolean isActive = activeCheckBox.isSelected();

        if (!validateInputs(name, email, phone)) {
            return;
        }

        // Update the selected member's details
        updateMemberDetails(name, email, phone, isActive);

        // Save the updated club data
        App.getDataManager().saveClubData(App.getClub());

        showAlert("Success", "Member details updated successfully!");
        navigateToMemberList();
    }

    /**
     * Cancels the editing process and navigates back to the member list.
     */
    @FXML
    private void cancel() {
        navigateToMemberList();
    }

    /**
     * Validates the input fields for the member.
     *
     * @param name  The name of the member.
     * @param email The email of the member.
     * @param phone The phone number of the member.
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInputs(String name, String email, String phone) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return false;
        }
        return true;
    }

    /**
     * Updates the selected member's details with the provided values.
     *
     * @param name     The updated name of the member.
     * @param email    The updated email of the member.
     * @param phone    The updated phone number of the member.
     * @param isActive The updated active status of the member.
     */
    private void updateMemberDetails(String name, String email, String phone, boolean isActive) {
        selectedMember.setName(name);
        selectedMember.setEmail(email);
        selectedMember.setPhone(phone);
        selectedMember.setActive(isActive);
    }

    /**
     * Navigates back to the member list screen.
     */
    private void navigateToMemberList() {
        try {
            App.switchScene("/club/MemberList.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to the member list.");
        }
    }
}