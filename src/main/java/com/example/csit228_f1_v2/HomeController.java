package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HomeController {


    public TextField tfChangeUsername;
    public Button btnChangeUsername;
    public Text txtChangePassword;
    public TextField tfChangePassword;
    public Button btnChangePassword;
    public Button btnDelete;

    private Stage stage;
    protected User user;
    protected void setStage(Stage stage) {
        this.stage = stage;
    }
    protected void setUser(User user){
        this.user = user;
    }
    @FXML
    protected void onChangeUsernameClick() {
        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE tbluser SET username = ? WHERE uid = ?")){
            String newName = tfChangeUsername.getText();
            user.username = newName;
            statement.setString(1,newName);
            statement.setInt(2,user.uid);
            statement.executeUpdate();
            System.out.println(user);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void onChangePasswordClick(){
        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE tbluser SET password = ? WHERE uid = ?")){
            String newPassword = tfChangePassword.getText();
            user.password = newPassword;
            statement.setString(1,newPassword);
            statement.setInt(2,user.uid);
            statement.executeUpdate();
            System.out.println(user);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void onDeleteClick(){
        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE from tbluser WHERE uid = ?")){
            statement.setInt(1,user.uid);
            statement.executeUpdate();
            user.username = "";
            user.password = "";
            user.uid = -1;
            System.out.println("Deleted");
            System.out.println(user);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                Parent p = loader.load();
                HelloController controller = loader.getController();
                Scene s = new Scene(p);
                controller.setStage(stage);
                controller.setUser(user);
                stage.setScene(s);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
