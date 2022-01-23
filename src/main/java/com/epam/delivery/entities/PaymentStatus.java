package com.epam.delivery.entities;

import java.io.Serializable;

public class PaymentStatus implements Serializable {
    private int id;
    private Language language;
    private String name;

    private PaymentStatus(String name) {
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

    public PaymentStatus createPaymentStatus(String name) {
        return new PaymentStatus(name);
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "id=" + id +
                ", language=" + language +
                ", name='" + name + '\'' +
                '}';
    }
}
