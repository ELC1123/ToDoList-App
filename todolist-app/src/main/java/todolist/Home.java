package todolist;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

public class Home {

    @FXML private VBox taskPreviewBox;

    @FXML public void initialize() {
        List<Task> tasks = TaskData.getTaskList().stream().filter(t -> !t.getCompletionStatus())
                                                          .sorted(Comparator.comparing(Task::getDueDate))
                                                          .limit(3)
                                                          .collect(Collectors.toList());
        taskPreviewBox.getChildren().clear();
        
        if(tasks.isEmpty()) {
            Label emptyLabel = new Label("Wow! Nothing to do!");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray");
            taskPreviewBox.getChildren().add(emptyLabel);
        }
        else {
            for(Task task : tasks) {
                Label taskLabel = new Label("• " + task.getTitle() + " - due " + task.getDueDate());
                taskLabel.setStyle("-fx-font-size: 14px;");
                taskPreviewBox.getChildren().add(taskLabel);
            } 
        }
    }

}
