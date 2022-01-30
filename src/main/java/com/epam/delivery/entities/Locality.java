package com.epam.delivery.entities;

import java.io.Serializable;

public class Locality implements Serializable {
    private int id;
    private String name;

    private Locality(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Locality createLocality(String name) {
        return new Locality(name);
    }

    @Override
    public String toString() {
        return "Locality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
