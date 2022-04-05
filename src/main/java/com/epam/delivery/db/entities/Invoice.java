package com.epam.delivery.db.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Invoice extends Entity {
    private static final long serialVersionUID = 7954175266891150537L;

    private long clientID;
    private Timestamp creationDatetime;
    private long orderID;
    private double sum;
    private int invoiceStatusID;

    public Invoice() {
    }

    public Invoice(long clientID, Timestamp creationDatetime, long orderID, double sum, int invoiceStatusID) {
        this.clientID = clientID;
        this.creationDatetime = creationDatetime;
        this.orderID = orderID;
        this.sum = sum;
        this.invoiceStatusID = invoiceStatusID;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public long getOrderID() {
        return orderID;
    }

    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Timestamp creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getInvoiceStatusID() {
        return invoiceStatusID;
    }

    public void setInvoiceStatusID(int invoiceStatusID) {
        this.invoiceStatusID = invoiceStatusID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return clientID == invoice.clientID && orderID == invoice.orderID && Double.compare(invoice.sum, sum) == 0
                && invoiceStatusID == invoice.invoiceStatusID && Objects.equals(creationDatetime, invoice.creationDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientID, creationDatetime, orderID, sum, invoiceStatusID);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "clientID=" + clientID +
                ", creationDatetime=" + creationDatetime +
                ", orderID=" + orderID +
                ", sum=" + sum +
                ", invoiceStatusID=" + invoiceStatusID +
                "} ";
    }
}
