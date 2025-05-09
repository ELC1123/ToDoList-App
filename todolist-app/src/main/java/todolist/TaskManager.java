package todolist;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> taskList = new ArrayList<>();

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> priorityBox;
    @FXML private DatePicker dueDatePicker;
    @FXML private ListView<Task> taskListView;
    @FXML private Button addButton;
    
    // Initialize some stuff in the program
    @FXML public void initialize() {
        // Initialize options in priority
        priorityBox.getItems().addAll("Low", "Medium", "High");
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // This function adds a task to the task manager
    @FXML private void addTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String priority = priorityBox.getValue();
        LocalDate dueDate = dueDatePicker.getValue();

        if(title.isEmpty() || dueDate == null) {
            showError("Title and Deadline must be filled out!");
            return;
        }

        if(priority == null) {
            showError("Please set a priority");
            return;
        }

        Task task = new Task(title, description, dueDate, priority, false);
        taskList.add(task);
        taskListView.getItems().add(task);

        titleField.clear();
        descriptionField.clear();
        priorityBox.setValue(null);
        dueDatePicker.setValue(null);
    }

    // This function marks a task completed
    @FXML private void markComplete() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if(selectedTask != null) {
            selectedTask.setCompletionStatus(true);
            taskListView.refresh();
        }
        else {
            showError("Select a task to mark complete");
        }
    }

    // This function deletes a task
    @FXML private void deleteTask() {
        ObservableList<Task> selectedTasks = taskListView.getSelectionModel().getSelectedItems();
        ArrayList<Task> toRemove = new ArrayList<>(selectedTasks);

        if(!selectedTasks.isEmpty()) {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete the selected task/s?");
            confirmAlert.setContentText("This action cannot be undone.");

            confirmAlert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    taskList.removeAll(toRemove);
                    taskListView.getItems().removeAll(toRemove);
                }
            });
        }
        else {
            showError("Select at least 1 task to delete");
        }
    }

    // This functions shows an error message
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
