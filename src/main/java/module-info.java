module com.oniverse.maze_runner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.oniverse.neon_dystopia to javafx.fxml;
    exports com.oniverse.neon_dystopia;
}