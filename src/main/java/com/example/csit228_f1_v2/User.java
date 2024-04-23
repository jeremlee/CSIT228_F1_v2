package com.example.csit228_f1_v2;

public class User {
    protected String username;
    protected String password;
    protected int uid;

    User(){
        username = "";
        password = "";
        uid = -1;
    }
    public String toString(){
        return uid + " " + username + " " + password;
    }
}
