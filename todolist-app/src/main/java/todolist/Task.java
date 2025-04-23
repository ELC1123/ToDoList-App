package todolist;

import java.time.*;

public class Task {
    // Initialize variables
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private boolean completionStatus;

    // Constructor
    public Task(String title, String description, LocalDate dueDate, String priority, boolean completionStatus) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completionStatus = completionStatus;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public boolean getCompletionStatus() {
        return completionStatus;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    @Override public String toString() {
        return "[" + (completionStatus ? "X" : " ") + "]" + title + " (Due: " + dueDate + ", Priority: " + priority + ")";
    }
}
