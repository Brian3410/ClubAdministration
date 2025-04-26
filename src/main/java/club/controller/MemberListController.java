package club.controller;

import club.App;
import club.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for managing the member list view.
 * Handles displaying, editing, and navigating between screens.
 */
public class MemberListController extends BaseController{

    @FXML
    private TableView<Member> memberTable;

    @FXML
    private TableColumn<Member, String> nameColumn;

    @FXML
    private TableColumn<Member, String> emailColumn;

    @FXML
    private TableColumn<Member, String> phoneColumn;

    @FXML
    private TableColumn<Member, String> membershipIdColumn;

    @FXML
    private TableColumn<Member, Boolean> activeColumn;

    private ObservableList<Member> members;

    /**
     * Initializes the controller.
     * Sets up the member table and loads the members.
     */
    @FXML
    public void initialize() {
        setupMemberTable();
        loadMembers();
    }

    /**
     * Sets up the columns for the member table.
     */
    private void setupMemberTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        membershipIdColumn.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    /**
     * Loads the members into the member table.
     */
    private void loadMembers() {
        members = FXCollections.observableArrayList(App.getClub().getMembers());
        memberTable.setItems(members);
    }

    /**
     * Handles editing the selected member.
     * Navigates to the edit member screen.
     */
    @FXML
    private void editSelectedMember() {
        Member selected = memberTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a member to edit.");
            return;
        }

        // Store the selected member in the App class
        App.setSelectedMember(selected);

        // Navigate to the edit member screen
        navigateTo("/club/EditMember.fxml");
    }

    /**
     * Navigates back to the dashboard.
     */
    @FXML
    private void goToDashboard() {
        navigateTo("/club/Dashboard.fxml");
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