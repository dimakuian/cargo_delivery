package com.epam.delivery.entities;

import java.io.Serializable;

/**
 * LocalityDescription entity.
 */
public class LocalityDescription extends Entity {
    private static final long serialVersionUID = -650387365460106049L;

    private int localityID;
    private int languageID;
    private String city;
    private String street;
    private String streetNumber;

    private LocalityDescription(int localityID, int languageID, String city, String street, String streetNumber) {
        this.localityID = localityID;
        this.languageID = languageID;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public int getLocalityID() {
        return localityID;
    }

    public void setLocalityID(int localityID) {
        this.localityID = localityID;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public static LocalityDescription create(int locID, int langID, String city, String street, String strNumb) {
        return new LocalityDescription(locID, langID, city, street, strNumb);
    }

    @Override
    public String toString() {
        return "LocalityDescription{" +
                "localityID=" + localityID +
                ", languageID=" + languageID +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                '}';
    }
}
