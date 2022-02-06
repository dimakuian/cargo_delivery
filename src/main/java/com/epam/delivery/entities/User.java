package com.epam.delivery.entities;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private final String login;
    private String password;
    private int roleID;

    private User(String login) {
        this.login = login;
    }

    private User(String login, String password, int roleID) {
        this.login = login;
        this.password = password;
        this.roleID = roleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public static User createUser(String login, String password, int roleID) {
        return new User(login,password, roleID);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", roleID=" + roleID +
                '}';
    }
}
