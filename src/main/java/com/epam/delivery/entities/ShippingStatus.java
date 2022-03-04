package com.epam.delivery.entities;

import java.util.Objects;

/**
 * ShippingStatus entity.
 */
public class ShippingStatus extends Entity {
    private static final long serialVersionUID = 6121382337924563974L;
    private String name;

    private ShippingStatus(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingStatus that = (ShippingStatus) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
