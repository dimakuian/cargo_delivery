package com.epam.delivery.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
    private int id;
    private String shippingAddress;
    private String deliveryAddress;
    private Timestamp creationTime;
    private Person person;
    private String consignee;
    private String description;
    private double distance;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double volume;
    private double fare;
    private ShippingStatus shippingStatus;
    private PaymentStatus paymentStatus;
    private Timestamp deliveryDate;

    private Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Order createOrder() {
        return new Order();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", creationTime=" + creationTime +
                ", person=" + person +
                ", consignee='" + consignee + '\'' +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                ", weight=" + weight +
                ", volume=" + volume +
                ", fare=" + fare +
                ", shippingStatus=" + shippingStatus +
                ", paymentStatus=" + paymentStatus +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    public class Builder {
        private Order order;

        public Builder(Order order) {
            this.order = order;
        }

        public Builder withCreationTimestamp(Timestamp val) {
            order.creationTime = val;
            return this;
        }

        public Builder withPerson(Person val) {
            order.person = val;
            return this;
        }

        public Builder withConsignee(String val) {
            order.consignee = val;
            return this;
        }

        public Builder withDistance(double val) {
            order.distance = val;
            return this;
        }

        public Builder withWeight(double val) {
            order.weight = val;
            return this;
        }

        public Builder withVolume(double val) {
            order.volume = val;
            return this;
        }

        public Builder withShippingStatus(ShippingStatus val) {
            order.shippingStatus = val;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
