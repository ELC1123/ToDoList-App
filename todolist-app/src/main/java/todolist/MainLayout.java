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
            // Load Homepage
            FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
            homeView = homeLoader.load();
            homeController = homeLoader.getController();

            // Load Add Task page
            FXMLLoader addTaskLoader = new FXMLLoader(getClass().getResource("AddTask.fxml"));
            addTaskView = addTaskLoader.load();

            // Load Task List page
            FXMLLoader taskListLoader = new FXMLLoader(getClass().getResource("TaskList.fxml")); 
            taskListView = taskListLoader.load();
            taskListController = taskListLoader.getController();
            taskListController.setMainLayoutController(this);
            taskListController.setContentArea(contentArea);

            contentArea.getChildren().setAll(homeView);

            // FOR TESTING
            // System.out.println("Home Page Loaded: " + (homeView != null)); 

        } catch (IOException e) {
            e.printStackTrace();
        }

        homeButton.setOnAction(e -> {
            homeController.refresh();
            contentArea.getChildren().setAll(homeView);
        });

        addTaskButton.setOnAction(e -> contentArea.getChildren().setAll(addTaskView));

        taskListButton.setOnAction(e -> {
            taskListController.refreshTaskList();
            contentArea.getChildren().setAll(taskListView);
        });
    }

    public void showAddTaskForEdit(Task taskToEdit) {
        FXMLLoader addTaskLoader = new FXMLLoader(getClass().getResource("AddTask.fxml"));
        try {
            Node editView = addTaskLoader.load();
            AddTask addTaskController = addTaskLoader.getController();
            addTaskController.setTaskToEdit(taskToEdit);

            contentArea.getChildren().setAll(editView);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
