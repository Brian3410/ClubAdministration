package club.controller;

import club.App;
import club.model.Announcement;
import club.model.Event;
import club.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller for managing the dashboard view.
 * Handles statistics, event table, announcements, and navigation.
 */
public class DashboardController extends BaseController {

    @FXML
    private Label totalMembersLabel;

    @FXML
    private VBox totalMembersBox;

    @FXML
    private Label upcomingEventsLabel;

    @FXML
    private ListView<Announcement> announcementsListView;

    @FXML
    private TextField announcementInput;

    @FXML
    private HBox addAnnouncementBox;

    @FXML
    private Button navigateToEventsButton;

    @FXML
    private Button navigateToMembersButton;

    @FXML
    private TableView<Event> eventsTableView;

    @FXML
    private TableColumn<Event, String> eventNameColumn;

    @FXML
    private TableColumn<Event, String> eventDateColumn;

    @FXML
    private TableColumn<Event, String> eventTimeColumn;

    @FXML
    private TableColumn<Event, String> eventLocationColumn;

    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;

    private ObservableList<Announcement> announcements;

    /**
     * Initializes the dashboard.
     * Sets up the UI components and loads data.
     */
    @FXML
    private void initialize() {
        boolean isAdmin = App.isAdmin();

        // Configure admin-only sections
        configureAdminSections(isAdmin);

        // Update statistics and load data
        updateStats();
        setupEventTable();
        displayEvents();
        loadAnnouncements();
    }

    /**
     * Configures visibility and management of admin-only sections.
     *
     * @param isAdmin True if the user is an admin, false otherwise.
     */
    private void configureAdminSections(boolean isAdmin) {
        totalMembersBox.setVisible(isAdmin);
        totalMembersBox.setManaged(isAdmin);
        addAnnouncementBox.setVisible(isAdmin);
        addAnnouncementBox.setManaged(isAdmin);
        navigateToEventsButton.setVisible(isAdmin);
        navigateToEventsButton.setManaged(isAdmin);
        navigateToMembersButton.setVisible(isAdmin);
        navigateToMembersButton.setManaged(isAdmin);
    }

    /**
     * Updates the dashboard statistics, including total active members and upcoming events.
     */
    private void updateStats() {
        if (App.isAdmin()) {
            // Count only active members
            int totalActiveMembers = (int) App.getClub().getMembers().stream()
                    .filter(Member::isActive)
                    .count();
            totalMembersLabel.setText(String.valueOf(totalActiveMembers));
        }

        // Count upcoming events
        long upcomingEvents = App.getClub().getEvents().stream()
                .filter(event -> event.getDate().isAfter(LocalDate.now()))
                .count();
        upcomingEventsLabel.setText(String.valueOf(upcomingEvents));
    }

    /**
     * Sets up the columns for the event table.
     */
    private void setupEventTable() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description")); // Bind description column
    }

    /**
     * Displays the list of events in the event table.
     */
    private void displayEvents() {
        eventsTableView.getItems().setAll(App.getClub().getEvents());
    }

    /**
     * Loads announcements into the announcements list view.
     */
    private void loadAnnouncements() {
        announcements = FXCollections.observableArrayList(App.getClub().getAnnouncements());
        announcementsListView.setItems(announcements);

        announcementsListView.setCellFactory(listView -> new ListCell<>() {
            private final HBox container = new HBox();
            private final Text announcementText = new Text();
            private final Button deleteButton = new Button("X");

            {
                // Enable text wrapping for the announcement text
                announcementText.wrappingWidthProperty().bind(announcementsListView.widthProperty().subtract(52)); // Adjust width as needed

                // Always add the announcement text first
                container.getChildren().add(announcementText);

                // Check if the user is an admin before adding the delete button
                if (App.isAdmin()) {
                    deleteButton.setOnAction(event -> {
                        Announcement announcement = getItem();
                        if (announcement != null) {
                            App.getClub().getAnnouncements().remove(announcement);
                            announcements.remove(announcement);
                            App.getDataManager().saveClubData(App.getClub());
                        }
                    });
                    // Add delete button after the text (at the back) only for admins
                    container.getChildren().add(deleteButton);
                }

                container.setSpacing(10); // Add spacing between text and button
            }

            @Override
            protected void updateItem(Announcement item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Format the announcement text with a short date and time
                    String formattedDateTime = item.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    announcementText.setText(formattedDateTime + " - " + item.getMessage());
                    setGraphic(container);
                }
            }
        });
    }

    /**
     * Adds a new announcement to the club.
     */
    @FXML
    private void addAnnouncement() {
        if (!App.isAdmin()) {
            showAlert("Access Denied", "You do not have permission to add announcements.");
            return;
        }

        String newAnnouncement = announcementInput.getText().trim();
        if (!newAnnouncement.isEmpty()) {
            LocalDateTime dateTime = LocalDateTime.now();

            // Generate a unique ID for the new announcement
            int newAnnouncementId = App.getDataManager().generateNextAnnouncementId();

            // Create a new announcement with the generated ID
            Announcement announcement = new Announcement(newAnnouncementId, newAnnouncement, dateTime);

            // Add the announcement to the club and save it
            App.getClub().addAnnouncement(announcement);
            announcements.add(announcement);
            App.getDataManager().saveClubData(App.getClub());

            // Clear the input field
            announcementInput.clear();

            showAlert("Success", "Announcement added successfully!");
        } else {
            showAlert("Error", "Announcement text cannot be empty.");
        }
    }

    /**
     * Navigates to the members list screen.
     */
    @FXML
    private void navigateToMembers() {
        navigateTo("/club/MemberList.fxml");
    }

    /**
     * Navigates to the events list screen.
     */
    @FXML
    private void navigateToEvents() {
        navigateTo("/club/EventList.fxml");
    }

    /**
     * Navigates to the profile screen.
     */
    @FXML
    private void navigateToProfile() {
        navigateTo("/club/Profile.fxml");
    }

    /**
     * Signs out the current user and navigates to the login screen.
     */
    @FXML
    private void signOut() {
        App.setLoggedInUserEmail(null);
        navigateTo("/club/Login.fxml");
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