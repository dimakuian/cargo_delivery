package com.epam.delivery.entities;

import java.io.Serializable;

public class ShippingStatus implements Serializable {
    private static final long serialVersionUID = 8181521910693065637L;
    private int id;
    private String nameEN;
    private String nameUK;

    private ShippingStatus(String nameEN, String nameUK) {
        this.nameEN = nameEN;
        this.nameUK = nameUK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameUK() {
        return nameUK;
    }

    public void setNameUK(String nameUK) {
        this.nameUK = nameUK;
    }

    public static ShippingStatus createShippingStatus(String nameEN, String nameUK) {
        return new ShippingStatus(nameEN, nameUK);
    }



    @Override
    public String toString() {
        return "ShippingStatus{" +
                "id=" + id +
                ", nameEN='" + nameEN + '\'' +
                ", nameUK='" + nameUK + '\'' +
                '}';
    }
}
