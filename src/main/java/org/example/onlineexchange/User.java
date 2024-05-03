package org.example.onlineexchange;

public class User {
    public static User[] user = new User[100];
    private String username;
    private long password;


    public String getUsername() {
        return username;
    }

    public long getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(long password) {
        this.password = password;
    }
}
