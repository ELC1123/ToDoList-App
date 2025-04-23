package todolist;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> taskList = new ArrayList<>();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("taskstorage");
    }
}
