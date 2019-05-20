package com.example.vignesh.checke;

/**
 * Created by Hariharan Sivakumar on 3/27/2018.
 */

public class User {


    private int id;
    private String username, email;

    public User(int id, String username) {
        this.id =id;
        this.username = username;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
