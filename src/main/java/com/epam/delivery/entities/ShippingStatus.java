package com.epam.delivery.entities;

import java.io.Serializable;

public class ShippingStatus implements Serializable {
    private int id;
    private Language language;
    private String name;

    private ShippingStatus(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShippingStatus createShippingStatus(String name) {
        return new ShippingStatus(name);
    }

    @Override
    public String toString() {
        return "ShippingStatus{" +
                "id=" + id +
                ", language=" + language +
                ", name='" + name + '\'' +
                '}';
    }
}
