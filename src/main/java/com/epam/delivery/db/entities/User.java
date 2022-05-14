package com.epam.delivery.db.entities;

import java.util.Objects;

/**
 * User entity.
 */
public class User extends Entity{
    private static final long serialVersionUID = 1647335406566429410L;
    private String login;
    private String password;
    private int roleID;

    public User() {
    }

    public User(String login, String password, int roleID) {
        this.login = login;
        this.password = password;
        this.roleID = roleID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", roleID=" + roleID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return roleID == user.roleID && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, roleID);
    }
}
