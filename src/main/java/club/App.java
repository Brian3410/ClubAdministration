package club;

import club.database.DataManager;
import club.model.Admin;
import club.model.Club;
import club.model.Event;
import club.model.Member;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the club management system.
 * Handles application lifecycle, scene switching, and global state management.
 */
public class App extends Application {

    private static Stage primaryStage;
    private static Club club;
    private static String loggedInUserEmail;
    private static Member selectedMember;
    private static Event selectedEvent;
    private static final DataManager dataManager = new DataManager();

    /**
     * Entry point for the JavaFX application.
     * Initializes the application and loads the login screen.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an error occurs during initialization.
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load the saved state of the Club object
        initializeClubData();

        // Load the login screen
        loadScene("/club/Login.fxml", "Login");
    }

    /**
     * Saves the current state of the Club object when the application closes.
     */
    @Override
    public void stop() {
        try {
            dataManager.saveClubData(club);
            System.out.println("Club data saved successfully.");
        } catch (Exception e) {
            System.err.println("Failed to save club data: " + e.getMessage());
        }
    }

    /**
     * Switches the current scene to the specified FXML file.
     *
     * @param fxmlFile The path to the FXML file.
     * @throws Exception If an error occurs while loading the scene.
     */
    public static void switchScene(String fxmlFile) throws Exception {
        loadScene(fxmlFile, null);
    }

    /**
     * Loads a scene from the specified FXML file and sets it on the primary stage.
     *
     * @param fxmlFile The path to the FXML file.
     * @param title    The title of the stage (optional).
     * @throws Exception If an error occurs while loading the scene.
     */
    private static void loadScene(String fxmlFile, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        if (title != null) {
            primaryStage.setTitle(title);
        }
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Initializes the Club object by loading saved data or creating a default instance.
     */
    private void initializeClubData() {
        try {
            club = dataManager.loadClubData();
            System.out.println("Club data loaded successfully.");
        } catch (Exception e) {
            System.out.println("No saved data found. Starting with a new Club instance.");
            club = new Club(getClubName()); // Default Club instance
        }

        // Add a default admin if no admins exist
        if (club.getAdmins().isEmpty()) {
            Admin defaultAdmin = new Admin("Default Admin", "admin", "admin123", "123");
            club.getAdmins().add(defaultAdmin);
            System.out.println("Default admin added: " + defaultAdmin.getName());
        }
    }

    // --- Getters and Setters ---

    /**
     * Gets the current Club object.
     *
     * @return The current Club object.
     */
    public static Club getClub() {
        return club;
    }

    /**
     * Sets the current Club object.
     *
     * @param updatedClub The updated Club object.
     */
    public static void setClub(Club updatedClub) {
        club = updatedClub;
    }

    /**
     * Gets the name of the current club.
     *
     * @return The name of the current club.
     */
    public static String getClubName() {
        return club != null ? club.getClubName() : "Monash Deep Neuron";
    }

    /**
     * Gets the DataManager instance.
     *
     * @return The DataManager instance.
     */
    public static DataManager getDataManager() {
        return dataManager;
    }

    /**
     * Gets the email of the currently logged-in user.
     *
     * @return The email of the logged-in user.
     */
    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    /**
     * Sets the email of the currently logged-in user.
     *
     * @param email The email of the logged-in user.
     */
    public static void setLoggedInUserEmail(String email) {
        loggedInUserEmail = email;
    }

    /**
     * Gets the currently selected member.
     *
     * @return The selected member.
     */
    public static Member getSelectedMember() {
        return selectedMember;
    }

    /**
     * Sets the currently selected member.
     *
     * @param member The selected member.
     */
    public static void setSelectedMember(Member member) {
        selectedMember = member;
    }

    /**
     * Checks if the currently logged-in user is an admin.
     *
     * @return true if the logged-in user is an admin, false otherwise.
     */
    public static boolean isAdmin() {
        return club.getAdmins().stream()
                .anyMatch(admin -> admin.getEmail().equals(loggedInUserEmail));
    }

    /**
     * Gets the currently selected event.
     *
     * @return The selected event.
     */
    public static Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * Sets the currently selected event.
     *
     * @param event The selected event.
     */
    public static void setSelectedEvent(Event event) {
        selectedEvent = event;
    }
}