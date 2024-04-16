package com.example.csit228_f1_v2;
import java.sql.*;
public class MySQLConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/dblee";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static Connection getConnection() {
        Connection c = null;
        try{
            c = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("SUCCESS");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return c;
    }
    public static void main(String[] args){
        Connection c = getConnection();
        try{
            c.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
