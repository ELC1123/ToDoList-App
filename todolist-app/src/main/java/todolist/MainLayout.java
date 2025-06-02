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

    private Home homeController;

    private TaskList taskListController;

    @FXML public void initialize() {
        try {
            FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
            homeView = homeLoader.load();
            homeController = homeLoader.getController();
            addTaskView = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
            // taskListView = FXMLLoader.load(getClass().getResource("TaskList.fxml"));
            FXMLLoader taskListLoader = new FXMLLoader(getClass().getResource("TaskList.fxml")); 
            taskListView = taskListLoader.load();
            taskListController = taskListLoader.getController();

            contentArea.getChildren().setAll(homeView);
            System.out.println("Home Page Loaded: " + (homeView != null));

        } catch (IOException e) {
            e.printStackTrace();
        }

        homeButton.setOnAction(e -> {
            homeController.refresh();
            contentArea.getChildren().setAll(homeView);
        });
        addTaskButton.setOnAction(e -> contentArea.getChildren().setAll(addTaskView));
        // taskListButton.setOnAction(e -> contentArea.getChildren().setAll(taskListView));
        taskListButton.setOnAction(e -> {
            taskListController.refreshTaskList();
            contentArea.getChildren().setAll(taskListView);
        });
    }
}
