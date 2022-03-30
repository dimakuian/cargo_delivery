package com.epam.delivery.db.entities;

import java.util.Objects;

/**
 * Client entity.
 */
public class Client extends Entity{
    private static final long serialVersionUID = 2099303903362589994L;
    private long userID;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String phone;
    private double balance;

    private Client(long userID, String name, String surname) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static Client createClient(long userID, String name, String surname) {
        return new Client(userID, name, surname);
    }

    @Override
    public String toString() {
        return "Client{" +
                ", user=" + userID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return userID == client.userID && Double.compare(client.balance, balance) == 0
                && Objects.equals(name, client.name) && Objects.equals(surname, client.surname)
                && Objects.equals(patronymic, client.patronymic) && Objects.equals(email, client.email)
                && Objects.equals(phone, client.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, name, surname, patronymic, email, phone, balance);
    }
}


