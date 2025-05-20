package todolist;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.*;

public class TaskData {
    private static final List<Task> taskList = new ArrayList<>();

    // make the list read-only
    public static List<Task> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }

    public static void addTask(Task task) {
        taskList.add(task);
    }

    public static void removeTask(Task task) {
        taskList.remove(task);
    }

    public static void clearTasks() {
        taskList.clear();
    }
}
