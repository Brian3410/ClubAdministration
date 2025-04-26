package club.controller;

import javafx.scene.control.Alert;

/**
 * Base controller class for the application.
 * Provides common functionality for all controllers.
 */
public abstract class BaseController {

    /**
     * Displays an alert dialog with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message of the alert.
     */
    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
