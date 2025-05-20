package todolist;

import java.time.LocalDate;

public class TaskManager {

    public static boolean isValidTask(String title, LocalDate dueDate, String priority) {
        return title != null && !title.trim().isEmpty() && dueDate != null && !dueDate.isBefore(LocalDate.now()) && priority != null;
    }

    public static Task createTask(String title, String description, LocalDate dueDate, String priority) {
        return new Task(title, description, dueDate, priority, false);
    }

}
