package todolist;

import java.time.LocalDate;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AddTask {
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> priorityBox;
    @FXML private DatePicker dueDatePicker; 
    @FXML private StackPane addTaskRoot;
    @FXML private Button addButton;

    private Task editingTask = null;

    // Initialize some portions of the program
    @FXML public void initialize() {
        // Initialize options for priorities
        priorityBox.getItems().addAll("Low", "Medium", "High");
        priorityBox.getItems().add(0, "Set Priority");
        priorityBox.setValue("Set Priority");

        // Initialize priorities to where "Set Priority" is not selectable
        priorityBox.setCellFactory(lv -> new ListCell<String>(){
            @Override public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null || empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setDisable(item.equals("Set Priority"));
                    setStyle(item.equals("Set Priority") ? "-fx-text-fill: gray;" : "-fx-text-fill: black;");
                }
            }
        });

        // Initialize date picker to where dates before today are not selectable
        dueDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if(date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: gray;");
                }
            }
        });
    }

    // This function adds a task
    @FXML private void addTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String priority = priorityBox.getValue();
        LocalDate dueDate = dueDatePicker.getValue();

        // If task is valid
        if(TaskManager.isValidTask(title, dueDate, priority)) {
            if(editingTask != null) {
                editingTask.setTitle(title);
                editingTask.setDescription(description);
                editingTask.setDueDate(dueDate);
                editingTask.setPriority(priority);

                TaskStorage.saveTasks(TaskData.getTaskList());
                showToast("✓ Task updated successfully!", "#17a2b8");

                editingTask = null;
                addButton.setText("Add Task");
            }
            else {
                Task newTask = TaskManager.createTask(title, description, dueDate, priority);
                TaskData.addTask(newTask);  // add new task to the data

                // Display successful task add
                showToast("✓ Task added successfully!", "#28a745");
            }
            clearForm();                // clear inputs
        }
        else {
            // Display error
            showToast("⚠ Please fill in all required fields.", "#dc3545");
        }
        
    }

    // This function makes it to where the pop-up for un/successful task addition is a toast-style popup
    private void showToast(String message, String color) {
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-padding: 10 20 10 20; -fx-background-radius: 10; -fx-font-size: 14px;");
        toastLabel.setOpacity(0);

        addTaskRoot.getChildren().add(toastLabel);

        StackPane.setAlignment(toastLabel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(toastLabel, new Insets(0, 0, 150, 0));

        // Fade in portion
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Pause portion
        PauseTransition stay = new PauseTransition(Duration.seconds(2));

        // Fade out portion
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Execute toast-style popup, remove popup after execution
        SequentialTransition seq = new SequentialTransition(fadeIn, stay, fadeOut);
        seq.setOnFinished(e -> addTaskRoot.getChildren().remove(toastLabel));
        seq.play();
    }

    public void setTaskToEdit(Task task) {
        editingTask = task;
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        dueDatePicker.setValue(task.getDueDate());
        priorityBox.setValue(task.getPriority());
        addButton.setText("Update Task");
    }

    private void clearForm() {
        titleField.clear();
        descriptionField.clear();
        dueDatePicker.setValue(null);
        priorityBox.setValue("Set Priority");
    }
}
