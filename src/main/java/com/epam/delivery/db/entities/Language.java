package com.epam.delivery.db.entities;

import java.util.Objects;

/**
 * Language entity.
 */
public class Language extends Entity {
    private static final long serialVersionUID = -7304907511028603803L;
    private String shortName;
    private String fullName;

    private Language(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
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
                "shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(shortName, language.shortName) && Objects.equals(fullName, language.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortName, fullName);
    }
}
