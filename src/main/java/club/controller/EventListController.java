package club.controller;

import club.App;
import club.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for managing the event list view.
 * Handles displaying, editing, and adding events.
 */
public class EventListController extends BaseController{

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> nameColumn;

    @FXML
    private TableColumn<Event, String> dateColumn;

    @FXML
    private TableColumn<Event, String> timeColumn;

    @FXML
    private TableColumn<Event, String> locationColumn;

    @FXML
    private TableColumn<Event, String> descriptionColumn;

    private ObservableList<Event> events;

    /**
     * Initializes the controller.
     * Sets up the event table and loads the events.
     */
    @FXML
    public void initialize() {
        setupEventTable();
        loadEvents();
    }

    /**
     * Sets up the columns for the event table.
     */
    private void setupEventTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    /**
     * Loads the events into the event table.
     */
    private void loadEvents() {
        events = FXCollections.observableArrayList(App.getClub().getEvents());
        eventTable.setItems(events);
    }

    /**
     * Handles editing the selected event.
     * Navigates to the edit event screen.
     */
    @FXML
    private void editSelectedEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Error", "Please select an event to edit.");
            return;
        }

        // Store the selected event in the App class
        App.setSelectedEvent(selectedEvent);

        // Navigate to the edit event screen
        navigateTo("/club/EditEvent.fxml");
    }

    /**
     * Handles adding a new event.
     * Navigates to the add event screen.
     */
    @FXML
    private void addNewEvent() {
        navigateTo("/club/AddEvent.fxml");
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