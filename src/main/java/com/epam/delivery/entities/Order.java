package com.epam.delivery.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
    private static final long serialVersionUID = -2667467651139601650L;
    private int id;
    private Locality shippingAddress;
    private Locality deliveryAddress;
    private Timestamp creationTime;
    private Client client;
    private String consignee;
    private String description;
    private double distance;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double volume;
    private double fare;
    private ShippingStatusDescription status;
    private Timestamp deliveryDate;

    private Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Locality getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Locality shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Locality getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Locality deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
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

    public ShippingStatusDescription getStatus() {
        return status;
    }

    public void setStatus(ShippingStatusDescription status) {
        this.status = status;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public static Order createOrder() {
        return new Order();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", shippingAddress=" + shippingAddress +
                ", deliveryAddress=" + deliveryAddress +
                ", creationTime=" + creationTime +
                ", client=" + client +
                ", consignee='" + consignee + '\'' +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                ", weight=" + weight +
                ", volume=" + volume +
                ", fare=" + fare +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    public class Builder {

        private Order order;

        public Builder(Order order) {
            this.order = order;
        }

        public Builder withID(Integer id) {
            order.id = id;
            return this;
        }

        public Builder withShippingAddress(Locality val) {
            order.shippingAddress = val;
            return this;
        }

        public Builder withDeliveryAddress(Locality val) {
            order.deliveryAddress = val;
            return this;
        }

        public Builder withCreationTimestamp(Timestamp val) {
            order.creationTime = val;
            return this;
        }

        public Builder withDescription(String val) {
            order.description = val;
            return this;
        }

        public Builder withClient(Client val) {
            order.client = val;
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

        public Builder withLength(double val) {
            order.length = val;
            return this;
        }

        public Builder withHeight(double val) {
            order.height = val;
            return this;
        }

        public Builder withWidth(double val) {
            order.width = val;
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

        public Builder withFare(double val) {
            order.fare = val;
            return this;
        }

        public Builder withShippingStatus(ShippingStatusDescription val) {
            order.status = val;
            return this;
        }

        public Builder withDeliveryDate(Timestamp val) {
            order.deliveryDate = val;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
