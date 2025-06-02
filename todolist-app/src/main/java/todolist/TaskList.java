package todolist;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class TaskList {
    
    @FXML private VBox taskContainer;
    
    @FXML void initialize() {
        refreshTaskList();
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

            completeCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
                task.setCompletionStatus(newVal);
                taskContainer.requestFocus();
                refreshTaskList();
            });

            // Title + Due Date display
            Label titleLabel = new Label(task.getTitle() + " (Due: " + task.getDueDate() + ")");
            titleLabel.setStyle("-fx-font-size: 12px;");
            if(task.getCompletionStatus()) {
                titleLabel.setStyle(titleLabel.getStyle() + "-fx-text-fill: gray; -fx-strikethrough: true;");
            }

            // Priority label
            Label priorityLabel = new Label(task.getPriority());
            priorityLabel.setStyle("-fx-font-weight: bold;");
            switch(task.getPriority()) {
                case "High" -> priorityLabel.setTextFill(Color.RED);
                case "Medium" -> priorityLabel.setTextFill(Color.ORANGE);
                case "Low" -> priorityLabel.setTextFill(Color.GREEN);
                default -> priorityLabel.setTextFill(Color.BLACK);
            }

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

            taskRow.getChildren().addAll(completeCheck, titleLabel, spacer, priorityLabel, deleteButton);
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
