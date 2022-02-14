package com.epam.delivery.entities;

import java.io.Serializable;

public class PaymentStatus implements Serializable {
    private static final long serialVersionUID = 5920354141897471791L;
    private int id;
    private String nameEN;
    private String nameUK;

    private PaymentStatus(String nameEN, String nameUK) {
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

    public static PaymentStatus createPaymentStatus(String nameEN, String nameUK) {
        return new PaymentStatus(nameEN, nameUK);
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "id=" + id +
                ", nameEN='" + nameEN + '\'' +
                ", nameUK='" + nameUK + '\'' +
                '}';
    }
}
