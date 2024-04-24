package com.example.csit228_f1_v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class TaskController {
    public TextField tfNewTask;
    public Button btnAddNewTask;
    public TableColumn<Task,String> colEdit;
    protected User user;
    protected Stage stage;
    protected ResultSet rs;
    @FXML
    public TableView<Task> tblvTasks;
    public TableColumn<Task, Integer> colTaskId;
    public TableColumn<Task, String> colContent;
    public TableColumn<Task, Void> colBtnEdit;

    public TableColumn<Task, Void> colBtnDelete;

    protected void setUser(User user) {
        this.user = user;
    }

    protected void setResultSet(ResultSet rs) {
        this.rs = rs;
        populateTableFromResultSet();
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        tblvTasks.setEditable(true);
        colTaskId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colContent.setCellFactory(TextFieldTableCell.forTableColumn());
        colEdit.setOnEditCommit(event -> {
            String newContent = event.getNewValue();
            Task task = event.getRowValue();
            task.setContent(newContent);
            updateTaskContent(task);
        });
        colEdit.setCellFactory(TextFieldTableCell.forTableColumn());
        colBtnEdit.setCellFactory(getButtonCellFactory("Edit"));
        colBtnDelete.setCellFactory(getButtonCellFactory("Delete"));
    }

    private Callback<TableColumn<Task, Void>, TableCell<Task, Void>> getButtonCellFactory(String buttonText) {
        return new Callback<>() {
            @Override
            public TableCell<Task, Void> call(final TableColumn<Task, Void> param) {
                final TableCell<Task, Void> cell = new TableCell<>() {
                    private final Button btn = new Button(buttonText);

                    {
                        btn.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            Integer task_id = task.getTaskId();
                            if (buttonText.equals("Edit")) {
                                String newContent = task.getContent();
                                try (Connection conn = MySQLConnection.getConnection()) {
                                    conn.setAutoCommit(false);
                                    Statement statement = conn.createStatement();
                                    statement.executeUpdate("UPDATE tbltask SET " +
                                            "content = '" + newContent + "' WHERE task_id = " + task.getTaskId());
                                    Statement statement1 = conn.createStatement();
                                    TaskController.this.rs = statement1.executeQuery("SELECT * from tbltask WHERE uid = " + user.uid);
                                    populateTableFromResultSet();
                                    conn.commit();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            } else if (buttonText.equals("Delete")) {
                                try(Connection conn = MySQLConnection.getConnection()) {
                                    conn.setAutoCommit(false);
                                    Statement statement = conn.createStatement();
                                    statement.executeUpdate("DELETE FROM tbltask WHERE task_id = " + task_id);
                                    Statement statement1 = conn.createStatement();
                                    TaskController.this.rs = statement1.executeQuery("SELECT * from tbltask WHERE uid = " + user.uid);
                                    populateTableFromResultSet();
                                    conn.commit();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @FXML
    protected void onBtnAddNewTaskClick(){
        String content = tfNewTask.getText();
        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO tbltask(uid,content) VALUES(?,?)")) {
            statement.setInt(1,user.uid);
            statement.setString(2,content);
            statement.executeUpdate();
            Statement statement1 = conn.createStatement();
            this.rs = statement1.executeQuery("SELECT * from tbltask WHERE uid = " + user.uid);
            populateTableFromResultSet();
            tfNewTask.setText("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateTableFromResultSet() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String content = rs.getString("content");
                Task task = new Task(taskId, content);
                tasks.add(task);
            }
            tblvTasks.setItems(tasks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateTaskContent(Task task) {
        try (Connection conn = MySQLConnection.getConnection()) {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE tbltask SET " +
                    "content = '" + task.getContent() + "' WHERE task_id = " + task.getTaskId());
            Statement statement1 = conn.createStatement();
            this.rs = statement1.executeQuery("SELECT * from tbltask WHERE uid = " + user.uid);
            populateTableFromResultSet();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}