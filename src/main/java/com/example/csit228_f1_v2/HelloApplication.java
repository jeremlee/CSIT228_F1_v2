package com.example.csit228_f1_v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    protected User user;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        user = new User();
        String createStatement = "CREATE TABLE IF NOT EXISTS tbluser ("
                + "uid INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(255) NOT NULL,"
                + "password VARCHAR(255) NOT NULL"
                + ")";
        try(Connection conn = MySQLConnection.getConnection();
            Statement statement = conn.createStatement()){
            statement.executeUpdate(createStatement);
        }catch(SQLException e){
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            // Load the FXML file
            Parent p = loader.load();
            HelloController controller = loader.getController();
            controller.setStage(stage);
            controller.setUser(user);
            Scene s = new Scene(p);
            stage.setScene(s);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}