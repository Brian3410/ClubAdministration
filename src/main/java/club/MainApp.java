package club;

/**
 * The MainApp class serves as the entry point for launching the application.
 * It contains the main method that initializes the application and launches the GUI.
 */
public class MainApp {

    /**
     * The main method is the entry point of the application.
     * It launches the JavaFX application by delegating to the {@link App} class.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        App.launch(App.class, args);
    }
}