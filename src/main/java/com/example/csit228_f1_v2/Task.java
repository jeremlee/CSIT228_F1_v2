package com.example.csit228_f1_v2;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final IntegerProperty taskId;
    private final StringProperty content;

    public Task(int taskId, String content) {
        this.taskId = new SimpleIntegerProperty(taskId);
        this.content = new SimpleStringProperty(content);
    }

    public int getTaskId() {
        return taskId.get();
    }

    public IntegerProperty taskIdProperty() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}