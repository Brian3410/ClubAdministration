package club.controller;

import club.App;
import club.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controller for adding a new event.
 */
public class AddEventController extends BaseController{

    @FXML
    private TextField eventNameField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField eventTimeField;

    @FXML
    private TextField eventLocationField;

    @FXML
    private TextArea eventDescriptionField;

    /**
     * Handles saving a new event.
     * Validates the input fields, creates a new event, and adds it to the club.
     */
    @FXML
    private void saveEvent() {
        String name = eventNameField.getText();
        LocalDate date = eventDatePicker.getValue();
        String timeInput = eventTimeField.getText().trim();
        String location = eventLocationField.getText();
        String description = eventDescriptionField.getText();

        if (!validateInputs(name, date, timeInput, location)) {
            return;
        }

        // Parse the time input
        LocalTime time = parseTime(timeInput);
        if (time == null) {
            return;
        }

        // Generate a unique ID for the new event
        int newEventId = App.getDataManager().generateNextEventId();

        // Create a new event and add it to the club
        Event newEvent = new Event(newEventId, name, date, time, location, description);
        App.getClub().addEvent(newEvent);

        // Save the updated club data
        App.getDataManager().saveClubData(App.getClub());

        showAlert("Success", "Event saved successfully!");

        // Navigate back to the event list
        navigateToEventList();
    }

    /**
     * Cancels the event creation and navigates back to the event list.
     */
    @FXML
    private void cancel() {
        navigateToEventList();
    }

    /**
     * Validates the input fields for the event.
     *
     * @param name     The name of the event.
     * @param date     The date of the event.
     * @param time     The time of the event as a string.
     * @param location The location of the event.
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInputs(String name, LocalDate date, String time, String location) {
        if (name.isEmpty() || date == null || time.isEmpty() || location.isEmpty()) {
            showAlert("Error", "Please fill in all required fields.");
            return false;
        }
        return true;
    }

    /**
     * Parses the time input and validates its format.
     *
     * @param timeInput The time input as a string.
     * @return The parsed LocalTime object, or null if the format is invalid.
     */
    private LocalTime parseTime(String timeInput) {
        try {
            return LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            showAlert("Error", "Invalid time format. Please use HH:mm.");
            return null;
        }
    }

    /**
     * Navigates back to the event list screen.
     */
    private void navigateToEventList() {
        try {
            App.switchScene("/club/EventList.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to the event list.");
        }
    }
}