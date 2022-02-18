package com.epam.delivery.entities;

import java.io.Serializable;

public class ShippingStatus implements Serializable {
    private static final long serialVersionUID = 8181521910693065637L;
    private int id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ShippingStatus createShippingStatus(String name) {
        return new ShippingStatus(name);
    }


    @Override
    public String toString() {
        return "ShippingStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
