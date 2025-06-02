module todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;

    opens todolist to javafx.fxml, com.google.gson;
    exports todolist;
}
