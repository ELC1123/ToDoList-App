package todolist;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TaskList {
    
    @FXML private VBox taskContainer;
    private StackPane contentArea;
    private MainLayout mainLayoutController;
    
    @FXML void initialize() {
        refreshTaskList();
    }

    public void setContentArea(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    public void setMainLayoutController(MainLayout mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    public void refreshTaskList() {
        taskContainer.getChildren().clear();

        for(Task task : TaskData.getTaskList()) {
            // Layout
            HBox taskRow = new HBox(10);
            taskRow.setPadding(new Insets(10));
            taskRow.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 8;");
            taskRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            
            // Completion Checkbox
            CheckBox completeCheck = new CheckBox();
            completeCheck.setSelected(task.getCompletionStatus());

            // Title + Due Date display
            Text titleText = new Text(task.getTitle() + " (Due: " + task.getDueDate() + ")");
            titleText.setStyle("-fx-font-size: 12px;");
            titleText.setStrikethrough(task.getCompletionStatus());
            titleText.setFill(task.getCompletionStatus() ? Color.GREY : Color.BLACK);

            completeCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
                task.setCompletionStatus(newVal);
                TaskStorage.saveTasks(TaskData.getTaskList());

                titleText.setStrikethrough(newVal);
                titleText.setFill(newVal ? Color.GREY : Color.BLACK);
                taskContainer.requestFocus();
            });

            // Priority label
            Label priorityLabel = new Label(task.getPriority());
            priorityLabel.setStyle("-fx-font-weight: bold;");
            switch(task.getPriority()) {
                case "High" -> priorityLabel.setTextFill(Color.RED);
                case "Medium" -> priorityLabel.setTextFill(Color.ORANGE);
                case "Low" -> priorityLabel.setTextFill(Color.GREEN);
                default -> priorityLabel.setTextFill(Color.BLACK);
            }

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #64b5f6; -fx-text-fill: white;");
            editButton.setOnAction(e -> {
                if(mainLayoutController != null) {
                    mainLayoutController.showAddTaskForEdit(task);
                }
            });

            // Delete button
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #e57373; -fx-text-fill: white;");
            deleteButton.setOnAction(e -> {
                TaskData.removeTask(task);
                taskContainer.requestFocus();
                refreshTaskList();
            });

            // Spacer
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            taskRow.getChildren().addAll(completeCheck, titleText, spacer, priorityLabel, editButton, deleteButton);
            taskContainer.getChildren().add(taskRow);
        }

        if(taskContainer.getChildren().isEmpty()) {
            Label emptyLabel = new Label("You have no tasks. Yay!");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            taskContainer.setAlignment(Pos.TOP_CENTER);
            taskContainer.getChildren().add(emptyLabel);
        }
    }

}
