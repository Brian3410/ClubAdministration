package club.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import club.App;
import club.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller for editing an existing event.
 */
public class EditEventController extends BaseController{

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private TextField locationField;

    @FXML
    private TextArea descriptionField;

    private Event selectedEvent;

    /**
     * Initializes the controller.
     * Loads the selected event and populates the fields with its data.
     */
    public void initialize() {
        // Load the selected event from the App class
        selectedEvent = App.getSelectedEvent();

        if (selectedEvent != null) {
            populateFields();
        } else {
            showAlert("Error", "No event selected for editing.");
            navigateToEventList();
        }
    }

    /**
     * Populates the input fields with the selected event's data.
     */
    private void populateFields() {
        nameField.setText(selectedEvent.getName());
        datePicker.setValue(selectedEvent.getDate());
        timeField.setText(selectedEvent.getTime().toString());
        locationField.setText(selectedEvent.getLocation());
        descriptionField.setText(selectedEvent.getDescription());
    }

    /**
     * Handles saving the updated event.
     * Validates the input fields, updates the event, and saves the changes.
     */
    @FXML
    private void saveEvent() {
        String name = nameField.getText();
        LocalDate date = datePicker.getValue();
        String timeInput = timeField.getText().trim();
        String location = locationField.getText();
        String description = descriptionField.getText();

        if (!validateInputs(name, date, timeInput, location)) {
            return;
        }

        // Parse the time input
        LocalTime time = parseTime(timeInput);
        if (time == null) {
            return;
        }

        // Update the selected event's details
        updateEventDetails(name, date, time, location, description);

        // Save the updated club data
        App.getDataManager().saveClubData(App.getClub());

        showAlert("Success", "Event details updated successfully!");
        navigateToEventList();
    }

    /**
     * Cancels the editing process and navigates back to the event list.
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
     * Updates the selected event's details with the provided values.
     *
     * @param name        The updated name of the event.
     * @param date        The updated date of the event.
     * @param time        The updated time of the event.
     * @param location    The updated location of the event.
     * @param description The updated description of the event.
     */
    private void updateEventDetails(String name, LocalDate date, LocalTime time, String location, String description) {
        // Update the event object
        selectedEvent.setName(name);
        selectedEvent.setDate(date);
        selectedEvent.setTime(time);
        selectedEvent.setLocation(location);
        selectedEvent.setDescription(description);
        
        // Use the Club's update method to properly update the event in the system
        try {
            App.getClub().updateEvent(selectedEvent);
        } catch (IllegalArgumentException e) {
            showAlert("Error", "Failed to update event: " + e.getMessage());
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