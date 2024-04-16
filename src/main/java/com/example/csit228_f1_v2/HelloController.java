package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class HelloController {
    public GridPane pnLogin;
    public AnchorPane pnMain;
    public VBox pnHome;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void insertData(String name, String password) throws IOException{
        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO users(name,password) VALUES(?,?)")){
            statement.setString(1,name);
            statement.setString(2,password);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    protected boolean readData(String name, String password){
        try(Connection conn = MySQLConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE name = ?")){
            statement.setString(1,name);
            statement.executeQuery();
            ResultSet results = statement.getResultSet();
            if(results.next()){
                String pw = results.getString("password");
                if(password.equals(pw)){
                    System.out.println("Correct password");
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
        Parent homeview = FXMLLoader.load(HelloApplication.class
                .getResource("home-view.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(homeview);
    }
}