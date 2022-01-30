package com.epam.delivery.entities;

import java.io.Serializable;

public class Admin implements Serializable {
    private int id;
    private User user;
    private String name;
    private String surname;

    private Admin(User user, String name, String surname) {
        this.user = user;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static Admin createManager(User user, String name, String surname) {
        return new Admin(user, name, surname);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
