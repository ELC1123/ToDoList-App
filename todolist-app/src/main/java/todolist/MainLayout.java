package todolist;

import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.fxml.*;

import java.io.IOException;

public class MainLayout {
    @FXML private Button homeButton;
    @FXML private Button addTaskButton;
    @FXML private Button taskListButton;
    @FXML private StackPane contentArea;

    private Node homeView;
    private Node addTaskView;
    private Node taskListView;

    @FXML public void initialize() {
        try {
            homeView = FXMLLoader.load(getClass().getResource("Home.fxml"));
            addTaskView = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
            //taskListView = FXMLLoader.load(getClass().getResource("TaskList.fxml"));

            contentArea.getChildren().setAll(homeView);
            System.out.println("Home Page Loaded: " + (homeView != null));

        } catch (IOException e) {
            e.printStackTrace();
        }

        homeButton.setOnAction(e -> contentArea.getChildren().setAll(homeView));
        addTaskButton.setOnAction(e -> contentArea.getChildren().setAll(addTaskView));
        taskListButton.setOnAction(e -> contentArea.getChildren().setAll(taskListView));
    }
}
