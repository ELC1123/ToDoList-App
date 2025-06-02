package todolist;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.*;

public class TaskData {
    private static final List<Task> taskList = new ArrayList<>();

    static {
        List<Task> loadedTasks = TaskStorage.loadTasks();
        if(loadedTasks != null) {
            taskList.clear();
            taskList.addAll(loadedTasks);
        }
    }

    // make the list read-only
    public static List<Task> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }

    public static void addTask(Task task) {
        taskList.add(task);
        TaskStorage.saveTasks(taskList);
    }

    public static void removeTask(Task task) {
        taskList.remove(task);
        TaskStorage.saveTasks(taskList);
    }

    public static void clearTasks() {
        taskList.clear();
    }
}
