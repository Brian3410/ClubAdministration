module club {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens club.controller to javafx.fxml;
    exports club;
    opens club to javafx.fxml;
    exports club.database;
    opens club.database to javafx.fxml;
    exports club.model;
    opens club.model to javafx.fxml;
}
