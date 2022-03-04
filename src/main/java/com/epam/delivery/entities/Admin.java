package com.epam.delivery.entities;

/**
 * Admin entity.
 */
public class Admin  extends Entity{
    private static final long serialVersionUID = -5882593516555785328L;
    private long userID;
    private String name;
    private String surname;

    private Admin(long userID, String name, String surname) {
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

    public static Admin createManager(long userID, String name, String surname) {
        return new Admin(userID, name, surname);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
