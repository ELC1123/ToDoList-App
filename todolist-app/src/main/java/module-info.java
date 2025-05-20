module todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens todolist to javafx.fxml;
    exports todolist;
}
