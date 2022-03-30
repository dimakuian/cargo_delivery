package com.epam.delivery.db.entities;

import java.util.Objects;

/**
 * Locality entity.
 */
public class Locality extends Entity {
    private static final long serialVersionUID = 5912926141331183042L;
    private String name;
    private double latitude;
    private double longitude;

    private Locality(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static Locality createLocality(String name, double latitude, double longitude) {
        return new Locality(name, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Locality{" +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locality locality = (Locality) o;
        return Double.compare(locality.latitude, latitude) == 0 && Double.compare(locality.longitude, longitude) == 0 && Objects.equals(name, locality.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }
}
