package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    public GridPane pnLogin;
    public AnchorPane pnMain;
    public VBox pnHome;
    public Label lblUsername;
    public TextField tfUsername;
    public Label lblPassword;
    public TextField tfPassword;
    public Button btnLogin;
    public Button btnRegister;
    @FXML
    private Label welcomeText;
    private Stage stage;
    protected User user;

    protected void setStage(Stage stage){
        this.stage = stage;
    }
    protected void setUser(User user){
        this.user = user;
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void insertData(String name, String password) {
        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO tbluser(username,password) VALUES(?,?)");
            PreparedStatement statement2 = conn.prepareStatement("SELECT uid FROM tbluser WHERE username = ?")){
            conn.setAutoCommit(false);
            statement.setString(1,name);
            statement.setString(2,password);
            statement.executeUpdate();
            statement2.setString(1,name);
            statement2.executeQuery();
            ResultSet rs = statement2.getResultSet();
            if(rs.next()){
                user.uid = rs.getInt("uid");
                user.username = name;
                user.password = password;
            } else{
                throw new SQLException();
            }
            System.out.println(user);
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected boolean readData(String name, String password){
        try(Connection conn = MySQLConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM tbluser WHERE username = ?")){
            statement.setString(1,name);
            statement.executeQuery();
            ResultSet results = statement.getResultSet();
            if(results.next()){
                String pw = results.getString("password");
                if(password.equals(pw)){
                    System.out.println("Correct password");
                    user.uid = results.getInt("uid");
                    user.username = results.getString("username");
                    user.password = results.getString("password");
                    System.out.println(user);
                    return true;
                } else{
                    System.out.println("Wrong password");
                    return false;
                }
            } else{
                System.out.println("No such user found");
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    @FXML
    protected void onSigninClick() throws IOException {
        Parent homeview = FXMLLoader.load(HelloApplication.class.getResource("home-view.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(homeview);
    }
    @FXML
    protected void onLogin(){
        String name = tfUsername.getText();
        String password = tfPassword.getText();
        boolean loggedIn = readData(name,password);
        if(loggedIn){
            System.out.println(user);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                Parent p = loader.load();
                HomeController controller = loader.getController();
                Scene s = new Scene(p);
                controller.setStage(stage);
                controller.setUser(user);
                stage.setScene(s);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    protected void onRegister() {
        String name = tfUsername.getText();
        String password = tfPassword.getText();
        insertData(name,password);
        tfUsername.setText("");
        tfPassword.setText("");
    }
}