package com.epam.delivery.db.entities;

import java.util.Objects;

/**
 * Admin entity.
 */
public class Admin  extends Entity{
    private static final long serialVersionUID = -5882593516555785328L;
    private long userID;
    private String name;
    private String surname;

    public Admin() {
    }

    public Admin(long userID, String name, String surname) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
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

    @Override
    public String toString() {
        return "Admin{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return userID == admin.userID && Objects.equals(name, admin.name) && Objects.equals(surname, admin.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, name, surname);
    }
}
