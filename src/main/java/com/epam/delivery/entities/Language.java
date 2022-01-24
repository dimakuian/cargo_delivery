package com.epam.delivery.entities;

import java.io.Serializable;

public class Language implements Serializable {
    private int id;
    private String shortName;
    private String fullName;

    private Language(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static Language createLanguage(String shortName, String fullName) {
        return new Language(shortName, fullName);
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
